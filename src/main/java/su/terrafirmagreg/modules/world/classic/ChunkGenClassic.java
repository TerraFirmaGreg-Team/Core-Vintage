package su.terrafirmagreg.modules.world.classic;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.world.ConfigWorld;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorBerryBushes;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorFalls;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorFissure;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorFruitTrees;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorLargeRocks;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorRarityBased;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorSnowIce;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorSoilPits;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorSpeleothem;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorTrees;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorWildCrops;
import su.terrafirmagreg.modules.world.classic.objects.generator.groundcover.GeneratorSurfaceRocks;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerBase;
import su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.drainage.GenLayerDrainage;
import su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.ph.GenLayerPH;
import su.terrafirmagreg.modules.world.classic.objects.mapgen.MapGenCaves;
import su.terrafirmagreg.modules.world.classic.objects.mapgen.MapGenRavine;
import su.terrafirmagreg.modules.world.classic.objects.mapgen.MapGenRiverRavine;
import su.terrafirmagreg.modules.world.classic.objects.spawner.EntitySpawnerWorldData;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.IWorldGenerator;


import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateHelper;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS;
import static su.terrafirmagreg.modules.world.classic.WorldTypeClassic.ROCKLAYER2;
import static su.terrafirmagreg.modules.world.classic.WorldTypeClassic.ROCKLAYER3;

@SuppressWarnings("WeakerAccess")
public class ChunkGenClassic implements IChunkGenerator {

  public static final IBlockState STONE = Blocks.STONE.getDefaultState();
  public static final IBlockState AIR = Blocks.AIR.getDefaultState();
  public static final IBlockState SALT_WATER = FluidsTFC.SALT_WATER.get().getBlock()
          .getDefaultState();
  public static final IBlockState FRESH_WATER = FluidsTFC.FRESH_WATER.get().getBlock()
          .getDefaultState();
  public static final IBlockState HOT_WATER = FluidsTFC.HOT_WATER.get().getBlock()
          .getDefaultState();
  public static final IBlockState LAVA = Blocks.LAVA.getDefaultState(); // todo: replace
  public static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
  /* Layers must be one here - otherwise snow becomes non-replaceable and wrecks the rest of world gen */
  public static final IBlockState SNOW = Blocks.SNOW_LAYER.getDefaultState()
          .withProperty(BlockSnow.LAYERS, 1);
  public static final IBlockState SALT_WATER_ICE = BlocksTFC.SEA_ICE.getDefaultState();
  public static final IBlockState FRESH_WATER_ICE = Blocks.ICE.getDefaultState();
  private static final float[] parabolicField = new float[25];

  /* This is done here rather than GameRegistry.registerWorldGenerator since we need to control the ordering of them better */
  private static final IWorldGenerator LAVA_FISSURE_GEN = new GeneratorRarityBased(
          x -> x.lavaFissureRarity, new GeneratorFissure(true));
  private static final IWorldGenerator WATER_FISSURE_GEN = new GeneratorRarityBased(
          x -> x.waterFissureRarity, new GeneratorFissure(false));
  private static final IWorldGenerator SOIL_PITS_GEN = new GeneratorSoilPits();
  private static final IWorldGenerator LARGE_ROCKS_GEN = new GeneratorRarityBased(
          x -> x.largeRockRarity, new GeneratorLargeRocks());
  private static final IWorldGenerator TREE_GEN = new GeneratorTrees();
  private static final IWorldGenerator BERRY_BUSH_GEN = new GeneratorBerryBushes();
  private static final IWorldGenerator FRUIT_TREE_GEN = new GeneratorFruitTrees();
  private static final IWorldGenerator WILD_CROPS_GEN = new GeneratorWildCrops();
  private static final IWorldGenerator LOOSE_ROCKS_GEN = new GeneratorSurfaceRocks();
  private static final IWorldGenerator SPELEOTHEM_GEN = new GeneratorSpeleothem();
  private static final IWorldGenerator WATERFALL_GEN = new GeneratorFalls(FRESH_WATER, 15);
  private static final IWorldGenerator LAVAFALL_GEN = new GeneratorFalls(
          Blocks.FLOWING_LAVA.getDefaultState(), 5);
  private static final IWorldGenerator SNOW_ICE_GEN = new GeneratorSnowIce();

  static {
    for (int x = -2; x <= 2; ++x) {
      for (int y = -2; y <= 2; ++y) {
        parabolicField[x + 2 + (y + 2) * 5] = 10.0F / MathHelper.sqrt(x * x + y * y + 0.2F);
        // Results in the following plot: http://i.imgur.com/rxrui67.png
      }
    }
  }

  public final WorldGenSettings settings;
  private final World world;
  private final long seed;
  private final Random rand;

  private final NoiseGeneratorOctaves noiseGen1;
  private final NoiseGeneratorOctaves noiseGen2;
  private final NoiseGeneratorOctaves noiseGen3;
  private final NoiseGeneratorOctaves noiseGen4;
  private final NoiseGeneratorOctaves noiseGen5;
  private final NoiseGeneratorOctaves noiseGen6;
  private final NoiseGeneratorOctaves mobSpawnerNoise;
  private final NoiseGeneratorPerlin noiseGen7; // Rainfall
  private final NoiseGeneratorPerlin noiseGen8; // Flora Density
  private final NoiseGeneratorPerlin noiseGen9; // Flora Diversity
  private final NoiseGeneratorPerlin noiseGen10; // Temperature

  private final GenLayerBase soilsGenLayer1;
  private final GenLayerBase rocksGenLayer1;
  private final GenLayerBase rocksGenLayer2;
  private final GenLayerBase rocksGenLayer3;

  private final GenLayerBase stabilityGenLayer;
  private final GenLayerBase phGenLayer;
  private final GenLayerBase drainageGenLayer;

  private final double[] noise1 = new double[425];
  private final double[] noise2 = new double[425];
  private final double[] noise3 = new double[425];
  private final double[] noise4 = new double[256];
  private final double[] noise5 = new double[425];
  private final double[] noise6 = new double[425];
  private final double[] heightMap = new double[425];

  private final Biome[] biomes = new Biome[324];
  private final DataLayerClassic[] stabilityLayer = new DataLayerClassic[256];
  private final DataLayerClassic[] drainageLayer = new DataLayerClassic[256];

  private final int[] seaLevelOffsetMap = new int[256];
  private final int[] chunkHeightMap = new int[256];

  private final MapGenBase caveGen;
  private final MapGenBase surfaceRavineGen;
  private final MapGenBase ravineGen;
  private final MapGenBase riverRavineGen;

  private final int seaLevel = 32;
  private final int yOffset = 112;

  private final float rainfallSpread;
  private final float floraDensitySpread;
  private final float floraDiversitySpread;

  private int[] soilLayer1 = new int[256];
  private int[] rockLayer1 = new int[256];
  private int[] rockLayer2 = new int[256];
  private int[] rockLayer3 = new int[256];

  private float rainfall;
  private float averageTemp;

  public ChunkGenClassic(World world, String settingsString) {
    this.world = world;
    this.seed = world.getSeed();
    this.rand = new Random(seed);
    this.settings = WorldGenSettings.fromString(settingsString).build();

    noiseGen1 = new NoiseGeneratorOctaves(rand, 4);
    noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
    noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
    noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
    noiseGen5 = new NoiseGeneratorOctaves(rand, 2);
    noiseGen6 = new NoiseGeneratorOctaves(rand, 1);
    mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);

    soilsGenLayer1 = GenLayerBase.initializeSoil(seed + 1, settings.rockLayerSize);
    rocksGenLayer1 = GenLayerBase.initializeRock(seed + 1, RockCategory.Layer.TOP, settings.rockLayerSize);
    rocksGenLayer2 = GenLayerBase.initializeRock(seed + 2, RockCategory.Layer.MIDDLE, settings.rockLayerSize);
    rocksGenLayer3 = GenLayerBase.initializeRock(seed + 3, RockCategory.Layer.BOTTOM, settings.rockLayerSize);

    noiseGen7 = new NoiseGeneratorPerlin(new Random(seed + 4), 4);
    noiseGen8 = new NoiseGeneratorPerlin(new Random(seed + 5), 4);
    noiseGen9 = new NoiseGeneratorPerlin(new Random(seed + 6), 4);
    noiseGen10 = new NoiseGeneratorPerlin(new Random(seed + 7), 4);

    stabilityGenLayer = GenLayerBase.initializeStability(seed + 9);
    phGenLayer = GenLayerPH.initializePH(seed + 10);
    drainageGenLayer = GenLayerDrainage.initialize(seed + 11);

    caveGen = TerrainGen.getModdedMapGen(new MapGenCaves(stabilityLayer),
            InitMapGenEvent.EventType.CAVE);
    surfaceRavineGen = new MapGenRavine(settings.surfaceRavineRarity, settings.surfaceRavineHeight,
            settings.surfaceRavineVariability);
    ravineGen = new MapGenRavine(settings.ravineRarity, settings.ravineHeight,
            settings.ravineVariability);
    riverRavineGen = new MapGenRiverRavine(settings.riverRavineRarity);

    // Load these now, because if config changes, shit will break
    rainfallSpread = (float) ConfigWorld.MISC.rainfallSpreadFactor;
    floraDiversitySpread = (float) ConfigWorld.MISC.floraDiversitySpreadFactor;
    floraDensitySpread = (float) ConfigWorld.MISC.floraDensitySpreadFactor;
    world.setSeaLevel(WorldTypeClassic.SEALEVEL); // Set sea level so squids can spawn
    EntitySpawnerWorldData.init(); // Called here so only TFC Worlds are affected
  }

  @Override
  public Chunk generateChunk(int chunkX, int chunkZ) {
    Arrays.fill(noise1, 0);
    Arrays.fill(noise2, 0);
    Arrays.fill(noise3, 0);
    Arrays.fill(noise4, 0);
    Arrays.fill(noise5, 0);
    Arrays.fill(noise6, 0);
    Arrays.fill(seaLevelOffsetMap, 0);
    Arrays.fill(chunkHeightMap, 0);
    Arrays.fill(heightMap, 0);

    rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
    ChunkPrimer chunkPrimerIn = new ChunkPrimer();
    generateRoughTerrain(chunkX, chunkZ, chunkPrimerIn);

    world.getBiomeProvider().getBiomes(biomes, chunkX * 16 - 1, chunkZ * 16 - 1, 18, 18);

    loadLayerGeneratorData(stabilityGenLayer, stabilityLayer, chunkX * 16, chunkZ * 16, 16, 16);
    loadLayerGeneratorData(drainageGenLayer, drainageLayer, chunkX * 16, chunkZ * 16, 16, 16);

    rainfall = MathHelper.clamp(
            250f + 250f * rainfallSpread * (float) noiseGen7.getValue(chunkX * 0.005, chunkZ * 0.005),
            0, 500);
    float floraDiversity = MathHelper.clamp(
            0.5f + 0.5f * floraDiversitySpread * (float) noiseGen9.getValue(chunkX * 0.005,
                    chunkZ * 0.005), 0,
            1);
    float floraDensity = MathHelper.clamp(
            (0.3f + 0.2f * rainfall / 500f) + 0.4f * floraDensitySpread * (float) noiseGen8.getValue(
                    chunkX * 0.05, chunkZ * 0.05), 0, 1);

    soilLayer1 = soilsGenLayer1.getInts(chunkX * 16, chunkZ * 16, 16, 16).clone();
    rockLayer1 = rocksGenLayer1.getInts(chunkX * 16, chunkZ * 16, 16, 16).clone();
    rockLayer2 = rocksGenLayer2.getInts(chunkX * 16, chunkZ * 16, 16, 16).clone();
    rockLayer3 = rocksGenLayer3.getInts(chunkX * 16, chunkZ * 16, 16, 16).clone();

    final float regionalFactor =
            5f * 0.09f * (float) noiseGen10.getValue(chunkX * 0.05, chunkZ * 0.05); // Range -5 <> 5
    averageTemp = ClimateHelper.monthFactor(regionalFactor, Month.AVERAGE_TEMPERATURE_MODIFIER,
            chunkZ << 4);

    ChunkPrimerClassic chunkPrimerOut = new ChunkPrimerClassic();
    replaceBlocksForBiomeHigh(chunkX, chunkZ, chunkPrimerIn, chunkPrimerOut);

    if (caveGen instanceof MapGenCaves mapGenCaves) {
      // Since this may be replaced by other mods (we give them the option, since 1.12 caves are bad)
      mapGenCaves.setGenerationData(rainfall, rockLayer1.clone());
    }
    caveGen.generate(world, chunkX, chunkZ, chunkPrimerOut);
    surfaceRavineGen.generate(world, chunkX, chunkZ, chunkPrimerOut);
    ravineGen.generate(world, chunkX, chunkZ, chunkPrimerOut);
    riverRavineGen.generate(world, chunkX, chunkZ, chunkPrimerOut);

    if (ConfigCore.MISC.DEBUG.debugWorldGenDanger) {
      for (int x = 0; x < 16; ++x) {
        for (int z = 0; z < 16; ++z) {
          chunkPrimerOut.setBlockState(x, 240, z, Blocks.STAINED_GLASS.getStateFromMeta(
                  Biome.getIdForBiome(getBiomeOffset(x, z)) & 15));

          chunkPrimerOut.setBlockState(x, 230, z,
                  Blocks.STAINED_GLASS.getStateFromMeta(rockLayer1[z << 4 | x] & 15));
          chunkPrimerOut.setBlockState(x, 220, z,
                  Blocks.STAINED_GLASS.getStateFromMeta(rockLayer2[z << 4 | x] & 15));
          chunkPrimerOut.setBlockState(x, 210, z,
                  Blocks.STAINED_GLASS.getStateFromMeta(rockLayer3[z << 4 | x] & 15));

          chunkPrimerOut.setBlockState(x, 252, z,
                  Blocks.STAINED_GLASS.getStateFromMeta(stabilityLayer[x << 4 | z].layerID & 15));
          chunkPrimerOut.setBlockState(x, 250, z,
                  Blocks.STAINED_GLASS.getStateFromMeta(drainageLayer[x << 4 | z].layerID & 15));
        }
      }
    }

    Chunk chunk = new Chunk(world, chunkPrimerOut, chunkX, chunkZ);

    var chunkData = CapabilityChunkData.get(chunk);
    if (chunkData == null) {
      throw new IllegalStateException("ChunkData capability is missing.");
    }
    chunkData.setGenerationData(rockLayer1, rockLayer2, rockLayer3, soilLayer1, stabilityLayer, drainageLayer, seaLevelOffsetMap, rainfall,
            regionalFactor, averageTemp, floraDensity, floraDiversity);

    byte[] biomeIds = chunk.getBiomeArray();
    for (int x = 0; x < 16; ++x) {
      for (int z = 0; z < 16; ++z) {
        biomeIds[z << 4 | x] = (byte) Biome.getIdForBiome(getBiomeOffset(x, z));
      }
    }

    chunk.setHeightMap(chunkHeightMap);
    chunk.generateSkylightMap();
    return chunk;
  }

  @Override
  public void populate(int chunkX, int chunkZ) {
    ForgeEventFactory.onChunkPopulate(true, this, world, rand, chunkX, chunkZ, false);
    BlockFalling.fallInstantly = true;
    final int worldX = chunkX << 4;
    final int worldZ = chunkZ << 4;
    BlockPos blockpos = new BlockPos(worldX, 0, worldZ);
    final Biome biome = world.getBiome(blockpos.add(16, 0, 16));
    rand.setSeed(world.getSeed());
    rand.setSeed((long) chunkX * (rand.nextLong() / 2L * 2L + 1L) + (long) chunkZ * (
            rand.nextLong() / 2L * 2L + 1L) ^ world.getSeed());

    // First, do all terrain related features
    SOIL_PITS_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    LAVA_FISSURE_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    WATER_FISSURE_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    LARGE_ROCKS_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    // todo: cave decorator

    // Next, larger plant type features
    TREE_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    BERRY_BUSH_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    FRUIT_TREE_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());

    // Calls through biome decorator which includes all small plants
    biome.decorate(world, rand, blockpos);

    // Finally
    LOOSE_ROCKS_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    WATERFALL_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    LAVAFALL_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    SPELEOTHEM_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());
    SNOW_ICE_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());

    if (TerrainGen.populate(this, world, rand, chunkX, chunkZ, false, ANIMALS)) {
      EntitySpawnerWorldData.performWorldGenSpawning(world, biome, worldX + 8, worldZ + 8, 16, 16,
              rand);
    }

    // To minimize the effects of this change, i'm putting this here, in the end of chunk generation
    WILD_CROPS_GEN.generate(rand, chunkX, chunkZ, world, this, world.getChunkProvider());

    ForgeEventFactory.onChunkPopulate(false, this, world, rand, chunkX, chunkZ, false);
    BlockFalling.fallInstantly = false;
  }

  @Override
  public boolean generateStructures(Chunk chunkIn, int x, int z) {
    return false; //todo
  }

  @Override
  public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType,
          BlockPos pos) {
    // This is a temporary measure for making 1.12 closer to playable
    return world.getBiome(pos).getSpawnableList(creatureType);
  }

  @Nullable
  @Override
  public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
          boolean findUnexplored) {
    return null; //todo
  }

  @Override
  public void recreateStructures(Chunk chunkIn, int x, int z) {
    //todo
  }

  @Override
  public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
    return false; //todo
  }

  @SuppressWarnings("PointlessArithmeticExpression")
  private void generateRoughTerrain(int chunkX, int chunkZ, ChunkPrimer primer) {
    world.getBiomeProvider().getBiomesForGeneration(biomes, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);
    generateHeightMap(chunkX * 4, chunkZ * 4);

    for (int x = 0; x < 4; ++x) {
      for (int z = 0; z < 4; ++z) {
        for (int y = 0; y < 16; ++y) {
          double noiseDL = heightMap[((x + 0) * 5 + z + 0) * 17 + y];
          double noiseUL = heightMap[((x + 0) * 5 + z + 1) * 17 + y];
          double noiseDR = heightMap[((x + 1) * 5 + z + 0) * 17 + y];
          double noiseUR = heightMap[((x + 1) * 5 + z + 1) * 17 + y];
          final double noiseDLA =
                  (heightMap[((x + 0) * 5 + z + 0) * 17 + y + 1] - noiseDL) * 0.125D;
          final double noiseULA =
                  (heightMap[((x + 0) * 5 + z + 1) * 17 + y + 1] - noiseUL) * 0.125D;
          final double noiseDRA =
                  (heightMap[((x + 1) * 5 + z + 0) * 17 + y + 1] - noiseDR) * 0.125D;
          final double noiseURA =
                  (heightMap[((x + 1) * 5 + z + 1) * 17 + y + 1] - noiseUR) * 0.125D;

          for (int yy = 0; yy < 8; ++yy) {
            double var34 = noiseDL;
            double var36 = noiseUL;
            final double var38 = (noiseDR - noiseDL) * 0.25D;
            final double var40 = (noiseUR - noiseUL) * 0.25D;

            for (int xx = 0; xx < 4; ++xx) {
              final double var49 = (var36 - var34) * 0.25D;
              double var47 = var34 - var49;

              for (int zz = 0; zz < 4; ++zz) {
                if ((var47 += var49) > 0.0D) {
                  primer.setBlockState(x * 4 + xx, y * 8 + yy, z * 4 + zz, STONE);
                } else if (y * 8 + yy < seaLevel) {
                  primer.setBlockState(x * 4 + xx, y * 8 + yy, z * 4 + zz, SALT_WATER);
                } else {
                  primer.setBlockState(x * 4 + xx, y * 8 + yy, z * 4 + zz, AIR);
                }
              }
              var34 += var38;
              var36 += var40;
            }
            noiseDL += noiseDLA;
            noiseUL += noiseULA;
            noiseDR += noiseDRA;
            noiseUR += noiseURA;
          }
        }
      }
    }
  }

  @SuppressWarnings("SameParameterValue")
  private void loadLayerGeneratorData(GenLayerBase gen, DataLayerClassic[] layers, int x, int y,
          int width, int height) {
    IntCache.resetIntCache();
    int[] ints = gen.getInts(x, y, width, height);
    for (int i = 0; i < width * height; ++i) {
      layers[i] = DataLayerClassic.get(ints[i]);
    }
  }

  private void replaceBlocksForBiomeHigh(int chunkX, int chunkZ, ChunkPrimer inp,
          ChunkPrimerClassic outp) {
    double var6 = 0.03125D;
    noiseGen4.generateNoiseOctaves(noise4, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, var6 * 4.0D,
            var6, var6 * 4.0D);
    boolean[] cliffMap = new boolean[256];
    for (int x = 0; x < 16; ++x) {
      for (int z = 0; z < 16; ++z) {
        int colIndex = z << 4 | x;
        Biome biome = getBiomeOffset(x, z);

        var rock1 = RockType.valueOf(rockLayer1[colIndex]);
        var rock2 = RockType.valueOf(rockLayer2[colIndex]);
        var rock3 = RockType.valueOf(rockLayer3[colIndex]);

        var soil1 = SoilType.valueOf(rockLayer1[colIndex]);

        DataLayerClassic drainage = drainageLayer[colIndex];
        DataLayerClassic stability = stabilityLayer[colIndex];
        int noise = (int) (noise4[colIndex] / 3.0D + 6.0D);
        int smooth = -1;

        IBlockState surfaceBlock = (rainfall + 1.3 * rand.nextGaussian() >= 150f ? BlocksSoil.GRASS
                : BlocksSoil.DRY_GRASS).get(soil1)
                .getDefaultState();
        IBlockState subSurfaceBlock = BlocksSoil.DIRT.get(soil1).getDefaultState();

        if (BiomeUtils.isBeachBiome(getBiomeOffset(x - 1, z)) || BiomeUtils.isBeachBiome(
                getBiomeOffset(x + 1, z)) ||
                BiomeUtils.isBeachBiome(getBiomeOffset(x, z + 1)) || BiomeUtils.isBeachBiome(
                getBiomeOffset(x, z - 1))) {
          if (!BiomeUtils.isBeachBiome(getBiomeOffset(x, z))) {
            cliffMap[colIndex] = true;
          }
        }

        // Используется для улучшения рек
        int nonRiverTiles = 0;
        int nonBeachTiles = 0;
        for (int a = x - 1; a <= x + 1; a++) {
          for (int b = z - 1; b <= z + 1; b++) {
            Biome BiomeAtOffset = getBiomeOffset(a, b);
            if (!BiomeUtils.isRiverBiome(BiomeAtOffset)) {
              nonRiverTiles++;
            }
            if (!BiomeUtils.isBeachBiome(BiomeAtOffset) && !BiomeUtils.isOceanicBiome(BiomeAtOffset)
                    &&
                    BiomeAtOffset != BiomesWorld.DEEP_OCEAN && BiomeAtOffset != BiomesWorld.OCEAN) {
              nonBeachTiles++;
            }
          }
        }

        int highestStone = 0;

        // Итерируемся по оси Y сверху вниз
        for (int y = 255 - yOffset; y >= 0; y--) {
          /*
           * ВЕРХНЯЯ ЧАСТЬ (используется yOffset)
           */
          if (outp.isEmpty(x, y + yOffset, z)) {
            outp.setBlockState(x, y + yOffset, z, inp.getBlockState(x, y, z));
            if (y + 1 < yOffset && outp.getBlockState(x, y + yOffset, z) ==
                    AIR/* нет необходимости проверять снова && BlockUtils.isSoilOrGravel(outp.getBlockState(x, y + yOffset + 1, z))*/) {
              for (int upCount = 1;
                      BlockUtils.isSoilOrGravel(outp.getBlockState(x, y + yOffset + upCount, z));
                      upCount++) {
                outp.setBlockState(x, y + yOffset + upCount, z, AIR);
              }
            }
          }

          if (outp.getBlockState(x, y + yOffset, z) == STONE) {
            highestStone = Math.max(highestStone, y);
          }

          int highestBeachTheoretical = (highestStone - seaLevel) / 4 + seaLevel;
          int beachCliffHeight =
                  nonBeachTiles > 0 ? (int) (
                          (highestStone - highestBeachTheoretical) * (nonBeachTiles) / 6.0
                                  + highestBeachTheoretical) :
                          highestBeachTheoretical;

          // Перестраиваем утесы на пляже
          if (BiomeUtils.isBeachBiome(biome) && y > seaLevel
                  && outp.getBlockState(x, y + yOffset, z) != AIR && y >= beachCliffHeight) {
            inp.setBlockState(x, y, z, AIR);
            outp.setBlockState(x, y + yOffset, z, AIR);
          }
          // Гарантируем, что реки не будут заблокированы
          if (BiomeUtils.isRiverBiome(biome) && y >= seaLevel - 2
                  && outp.getBlockState(x, y + yOffset, z) != AIR) {

            if (nonRiverTiles > 0) {
              if (y >= seaLevel - 1) {
                inp.setBlockState(x, y, z, y >= seaLevel ? AIR : SALT_WATER);
                outp.setBlockState(x, y + yOffset, z, y >= seaLevel ? AIR : SALT_WATER);
              }
            } else {
              inp.setBlockState(x, y, z, y >= seaLevel ? AIR : SALT_WATER);
              outp.setBlockState(x, y + yOffset, z, y >= seaLevel ? AIR : SALT_WATER);
            }

            //outp.setBlockState(x, y + yOffset, z, y >= seaLevel ? AIR : SALT_WATER);
          } else if (!BiomeUtils.isRiverBiome(biome) && nonRiverTiles < 9
                  && outp.getBlockState(x, y + yOffset, z) == STONE &&
                  ((y >= ((highestStone - seaLevel) / (10 - nonRiverTiles) + seaLevel)) || (
                          nonRiverTiles <= 5 && y >= seaLevel))) {
            inp.setBlockState(x, y, z, y >= seaLevel ? AIR : SALT_WATER);
            outp.setBlockState(x, y + yOffset, z, y >= seaLevel ? AIR : SALT_WATER);
          }

          if (outp.getBlockState(x, y + yOffset, z) == STONE) {
            if (seaLevelOffsetMap[colIndex] == 0 && y - seaLevel >= 0) {
              seaLevelOffsetMap[colIndex] = y - seaLevel;
            }

            if (chunkHeightMap[colIndex] == 0) {
              chunkHeightMap[colIndex] = y + yOffset;
            }

            if (y + yOffset <= ROCKLAYER3 + seaLevelOffsetMap[colIndex]) {
              outp.setBlockState(x, y + yOffset, z, BlocksRock.RAW.get(rock3).getDefaultState());
            } else if (y + yOffset <= ROCKLAYER2 + seaLevelOffsetMap[colIndex]) {
              outp.setBlockState(x, y + yOffset, z, BlocksRock.RAW.get(rock2).getDefaultState());
            } else {
              outp.setBlockState(x, y + yOffset, z, BlocksRock.RAW.get(rock1).getDefaultState());
            }

            // Пустыни / сухие районы
            if (rainfall < +1.3 * rand.nextGaussian() + 75f) {
              subSurfaceBlock = surfaceBlock = BlocksRock.SAND.get(rock1).getDefaultState();
            }

            if (biome == BiomesWorld.BEACH || biome == BiomesWorld.OCEAN
                    || biome == BiomesWorld.DEEP_OCEAN) {
              subSurfaceBlock = surfaceBlock = BlocksRock.SAND.get(rock1).getDefaultState();
            } else if (biome == BiomesWorld.GRAVEL_BEACH) {
              subSurfaceBlock = surfaceBlock = BlocksRock.GRAVEL.get(rock1).getDefaultState();
            }

            if (smooth == -1) {
              //The following makes dirt behave nicer and more smoothly, instead of forming sharp cliffs.
              int arrayIndexx = x > 0 ? x - 1 + (z * 16) : -1;
              int arrayIndexX = x < 15 ? x + 1 + (z * 16) : -1;
              int arrayIndexz = z > 0 ? x + ((z - 1) * 16) : -1;
              int arrayIndexZ = z < 15 ? x + ((z + 1) * 16) : -1;
              for (int counter = 1; counter < noise / 3; counter++) {
                if (arrayIndexx >= 0
                        && seaLevelOffsetMap[colIndex] - (3 * counter) > seaLevelOffsetMap[arrayIndexx]
                        &&
                        arrayIndexX >= 0
                        && seaLevelOffsetMap[colIndex] - (3 * counter) > seaLevelOffsetMap[arrayIndexX]
                        &&
                        arrayIndexz >= 0
                        && seaLevelOffsetMap[colIndex] - (3 * counter) > seaLevelOffsetMap[arrayIndexz]
                        &&
                        arrayIndexZ >= 0 && seaLevelOffsetMap[colIndex] - (3 * counter)
                        > seaLevelOffsetMap[arrayIndexZ]) {
                  seaLevelOffsetMap[colIndex]--;
                  noise--;
                  y--;
                }
              }
              smooth = (int) (noise * (1d - Math.max(Math.min((y - 16) / 80d, 1), 0)));

              // Set soil below water
              for (int c = 1; c < 3; c++) {
                if (yOffset + y + c > 256) {
                  continue;
                }

                IBlockState current = outp.getBlockState(x, yOffset + y + c, z);
                if (current != surfaceBlock && current != subSurfaceBlock && !BlockUtils.isWater(
                        current)) {
                  outp.setBlockState(x, yOffset + y + c, z, AIR);
                  if (yOffset + y + c + 1 > 256) {
                    continue;
                  }
                  if (outp.getBlockState(x, yOffset + y + c + 1, z) == SALT_WATER) {
                    outp.setBlockState(x, yOffset + y + c, z, subSurfaceBlock);
                  }
                }
              }

              // Determine the soil depth based on world y
              int dirtH = Math.max(8 - ((y + yOffset - 24 - WorldTypeClassic.SEALEVEL) / 16), 0);

              if (smooth > 0) {
                if (y >= seaLevel - 1 && y + 1 < yOffset
                        && inp.getBlockState(x, y + 1, z) != SALT_WATER && dirtH > 0 &&
                        !(BiomeUtils.isBeachBiome(biome) && y > highestBeachTheoretical + 2)) {
                  outp.setBlockState(x, y + yOffset, z, surfaceBlock);

                  boolean mountains =
                          BiomeUtils.isMountainBiome(biome) || biome == BiomesWorld.HIGH_HILLS ||
                                  biome == BiomesWorld.HIGH_HILLS_EDGE ||
                                  biome == BiomesWorld.MOUNTAINS || biome == BiomesWorld.MOUNTAINS_EDGE;
                  for (int c = 1; c < dirtH && !mountains && !cliffMap[colIndex]; c++) {
                    outp.setBlockState(x, y - c + yOffset, z, subSurfaceBlock);
                    if (c > 1 + (5 - drainage.valueInt)) {
                      outp.setBlockState(x, y - c + yOffset, z,
                              BlocksRock.GRAVEL.get(rock1).getDefaultState());
                    }
                  }
                }
              }
            }

            if (y > seaLevel - 2 && y < seaLevel && inp.getBlockState(x, y + 1, z) == SALT_WATER ||
                    y < seaLevel && inp.getBlockState(x, y + 1, z) == SALT_WATER) {
              if (biome == BiomesWorld.SWAMPLAND && biome == BiomesWorld.BAYOU
                      && biome == BiomesWorld.MANGROVE &&
                      biome == BiomesWorld.MARSH) {
                if (outp.getBlockState(x, y + yOffset, z) != BlocksRock.SAND.get(rock1)
                        .getDefaultState()) {
                  outp.setBlockState(x, y + yOffset, z,
                          BlocksSoil.DIRT.get(soil1).getDefaultState());
                }
              } else if (outp.getBlockState(x, y + yOffset, z) != BlocksRock.SAND.get(rock1)
                      .getDefaultState() &&
                      this.rand.nextInt(5) != 0) {
                outp.setBlockState(x, y + yOffset, z,
                        BlocksRock.GRAVEL.get(rock1).getDefaultState());
              }
            }
          }
          //  && biome != BiomesWorld.OCEAN && biome != BiomesWorld.DEEP_OCEAN && biome != BiomesWorld.BEACH && biome != BiomesWorld.GRAVEL_BEACH
          else if (inp.getBlockState(x, y, z) == SALT_WATER && !(BiomeUtils.isOceanicBiome(biome)
                  || BiomeUtils.isBeachBiome(biome))) {
            outp.setBlockState(x, y + yOffset, z, FRESH_WATER);
          }
        }

        for (int y = yOffset - 1;
                y >= 0;
                y--) // This cannot be optimized with the prev for loop, because the sealeveloffset won't be ready yet.
        {
          /*
           * LOW PART (yOffset is NOT used)
           */
          if (y < 1 + (settings.flatBedrock ? 0
                  : rand.nextInt(3))) //  + (seaLevelOffsetMap[colIndex] / 3)
          {
            outp.setBlockState(x, y, z, BEDROCK);
          } else if (outp.isEmpty(x, y, z)) {
            if (y <= ROCKLAYER3 + seaLevelOffsetMap[colIndex]) {
              outp.setBlockState(x, y, z, BlocksRock.RAW.get(rock3).getDefaultState());
            } else if (y <= ROCKLAYER2 + seaLevelOffsetMap[colIndex]) {
              outp.setBlockState(x, y, z, BlocksRock.RAW.get(rock2).getDefaultState());
            } else {
              outp.setBlockState(x, y, z, BlocksRock.RAW.get(rock1).getDefaultState());
            }

            if (BiomeUtils.isBeachBiome(biome) || BiomeUtils.isOceanicBiome(biome)) {
              if (outp.getBlockState(x, y + 1, z) == SALT_WATER) {
                outp.setBlockState(x, y, z, BlocksRock.SAND.get(rock1).getDefaultState());
                outp.setBlockState(x, y - 1, z, BlocksRock.SAND.get(rock1).getDefaultState());
              }
            }
          }
          if (y <= 6 && stability.valueInt == 1 && outp.getBlockState(x, y, z) == AIR) {
            outp.setBlockState(x, y, z, LAVA);
            if (outp.getBlockState(x, y + 1, z) != LAVA && rand.nextBoolean()) {
              outp.setBlockState(x, y + 1, z, LAVA);
            }
          }
        }
      }
    }
  }

  private Biome getBiomeOffset(int x, int z) {
    return biomes[(z + 1) * 18 + (x + 1)]; //todo: check, was (z + 1) + (x + 1) * 18
  }

  private void generateHeightMap(int xPos, int zPos) {
    noiseGen6.generateNoiseOctaves(noise6, xPos, zPos, 5, 5, 200.0D, 200.0D, 0.5D);
    noiseGen3.generateNoiseOctaves(noise3, xPos, 0, zPos, 5, 17, 5, 12.5, 6.25, 12.5);
    noiseGen1.generateNoiseOctaves(noise1, xPos, 0, zPos, 5, 17, 5, 1000D, 1000D, 1000D);
    noiseGen2.generateNoiseOctaves(noise2, xPos, 0, zPos, 5, 17, 5, 1000D, 1000D, 1000D);

    int i = 0;
    int j = 0;

    for (int x = 0; x < 5; ++x) {
      for (int z = 0; z < 5; ++z) {
        float variationBlended = 0.0F;
        float rootBlended = 0.0F;
        float totalBlendedHeight = 0.0F;
        Biome baseBiome = biomes[x + 2 + (z + 2) * 10];

        for (int xR = -2; xR <= 2; ++xR) {
          for (int zR = -2; zR <= 2; ++zR) {
            Biome blendBiome = biomes[x + xR + 2 + (z + zR + 2) * 10];
            float blendedHeight = parabolicField[xR + 2 + (zR + 2) * 5] / 2.0F;
            if (blendBiome.getBaseHeight() > baseBiome.getBaseHeight()) {
              blendedHeight *= 0.5F;
            }

            variationBlended += blendBiome.getHeightVariation() * blendedHeight;
            rootBlended += blendBiome.getBaseHeight() * blendedHeight;
            totalBlendedHeight += blendedHeight;
          }
        }

        variationBlended /= totalBlendedHeight;
        rootBlended /= totalBlendedHeight;
        variationBlended = variationBlended * 0.9F + 0.1F;
        rootBlended = (rootBlended * 4.0F - 1.0F) / 8.0F;

        double scaledNoise6Value = noise6[j++] / 8000.0D;

        if (scaledNoise6Value < 0.0D) {
          scaledNoise6Value =
                  -scaledNoise6Value * 0.3D; //If negative, make positive and shrink by a third?
        }

        scaledNoise6Value = scaledNoise6Value * 3.0D - 2.0D;

        if (scaledNoise6Value
                < 0.0D) // Only true when noise6[index2] is between -17,777 and 0, scaledNoise6Value will be at maximum -2
        {
          scaledNoise6Value /= 2.0D; // Results in values between 0 and -1
          if (scaledNoise6Value < -1.0D) //Error Checking
          {
            scaledNoise6Value = -1.0D;
          }
          scaledNoise6Value /= 1.4D * 2.0D; // Results in values between 0 and -0.357143
        } else {
          if (scaledNoise6Value > 1.0D) {
            scaledNoise6Value = 1.0D;
          }
          scaledNoise6Value /= 8.0D; // Results in values between 0 and 0.125
        }

        for (int y = 0; y < 17; ++y) {
          double rootBlendedCopy = rootBlended;
          rootBlendedCopy += scaledNoise6Value * 0.2D;
          rootBlendedCopy = rootBlendedCopy * 17 / 16.0D;
          double var28 = 17 / 2.0D + rootBlendedCopy * 4.0D;
          double output;
          double var32 = (y - var28) * 12.0D * 256.0D / 256.0D / (2.70 + variationBlended);

          if (var32 < 0.0D) {
            var32 *= 4.0D;
          }

          double var34 = noise1[i] / 512.0D;
          double var36 = noise2[i] / 512.0D;
          double var38 = (noise3[i] / 10.0D + 1.0D) / 2.0D;

          if (var38 < 0.0D) {
            output = var34;
          } else if (var38 > 1.0D) {
            output = var36;
          } else {
            output = var34 + (var36 - var34) * var38;
          }

          output -= var32;
          if (y > 17 - 4) {
            double var40 = (y - (17 - 4)) / 3.0F;
            output = output * (1.0D - var40) + -10.0D * var40;
          }

          heightMap[i++] = output;
        }
      }
    }
  }
}
