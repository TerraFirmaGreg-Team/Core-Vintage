package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ICapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.world.ConfigWorld;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;
import su.terrafirmagreg.modules.world.classic.objects.generator.structures.StructureGeneratorCorals;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockJoshuaTreeFlower;
import net.dries007.tfc.objects.te.TEPlacedItemFlat;
import net.dries007.tfc.types.TreesTFCF;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static su.terrafirmagreg.modules.rock.init.BlocksRock.SAND;

public class GeneratorTrees implements IWorldGenerator {

  private final GeneratorCinnamon cinnamon_trees = new GeneratorCinnamon();
  private final GeneratorBamboo bamboo_trees = new GeneratorBamboo();

  public static void generateLooseSticks(Random rand, int chunkX, int chunkZ, World world,
                                         int amount) {
    if (ConfigWorld.MISC.enableLooseSticks) {
      for (int i = 0; i < amount; i++) {
        final int x = chunkX * 16 + rand.nextInt(16) + 8;
        final int z = chunkZ * 16 + rand.nextInt(16) + 8;
        final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

        // Use air, so it doesn't replace other replaceable world gen
        // This matches the check in BlockPlacedItemFlat for if the block can stay
        // Also, only add on soil, since this is called by the world regen handler later
        IBlockState stateDown = world.getBlockState(pos.down());
        if (world.isAirBlock(pos) && stateDown.isSideSolid(world, pos.down(), EnumFacing.UP)
            && BlockHelper.isGround(stateDown)) {
          world.setBlockState(pos, BlocksTFC.PLACED_ITEM_FLAT.getDefaultState());
          var tile = TileUtils.getTile(world, pos, TEPlacedItemFlat.class);
          tile.ifPresent(tilePlacedItemFlat -> tilePlacedItemFlat.setStack(new ItemStack(Items.STICK)));
        }
      }
    }
  }

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
                       IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (!(chunkGenerator instanceof ChunkGenClassic)) {
      return;
    }

    final BlockPos chunkPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
    final BlockPos center = new BlockPos(chunkX * 16 + 8,
                                         world.getHeight(chunkX * 16 + 8, chunkZ * 16 + 8), chunkZ * 16 + 8);
    var chunkData = CapabilityChunkData.get(world, chunkPos);
    if (!chunkData.isInitialized()) {
      return;
    }

    final Biome biome = world.getBiome(chunkPos);
    if (!(biome instanceof BaseBiome)) {
      return;
    }

    final TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();

    final float diversity = chunkData.getFloraDiversity();
    final float density = chunkData.getFloraDensity();
    final float avgTemperature = Climate.getAvgTemp(world, chunkPos);
    final float rainfall = ProviderChunkData.getRainfall(world, chunkPos);

    float gauss = 2f * (float) random.nextGaussian();

    List<Tree> trees = chunkData.getValidTrees();
    Collections.rotate(trees, -(int) (diversity * (trees.size() - 1f)));

    cinnamon_trees.generate(world, random, center);
    bamboo_trees.generate(world, random, center);

    int stickDensity = 3 + (int) (4f * density + 1.5f * trees.size());
    if (trees.isEmpty()) {
      stickDensity = 1 + (int) (1.5f * density);
    }

    if (!(biome == BiomesWorld.OCEAN || biome == BiomesWorld.DEEP_OCEAN)) {
      generateLooseSticks(random, chunkX, chunkZ, world,
                          (int) (Math.ceil(stickDensity * ConfigWorld.MISC.sticksDensityModifier)));
    }

    // This is to avoid giant regions of no trees whatsoever.
    // It will create sparse trees ( < 1 per chunk) by averaging the climate data to make it more temperate
    // The thought is in very harsh conditions, a few trees might survive outside their typical temperature zone
    if (trees.isEmpty()) {
      if (random.nextFloat() > 0.2f) {
        return;
      }

      Tree extra = chunkData.getSparseGenTree();
      if (extra != null) {
        final int x = chunkX * 16 + random.nextInt(16) + 8;
        final int z = chunkZ * 16 + random.nextInt(16) + 8;
        final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
        extra.makeTree(manager, world, pos, random, true);
      }
      return;
    }

    final int treesPerChunk = (int) (density * 16 - 2);
    final int maxTrees = Math.min(trees.size(),
                                  Math.min(5, (int) (1 + (density + diversity) * 2.5f)));
    trees = trees.subList(0, maxTrees);

    int treesPlaced = 0;
    Set<BlockPos> checkedPositions = new HashSet<>();
    for (int i = 0; treesPlaced < treesPerChunk && i < treesPerChunk * 3; i++) {
      BlockPos column = new BlockPos(chunkX * 16 + random.nextInt(16) + 8, 0,
                                     chunkZ * 16 + random.nextInt(16) + 8);
      if (!checkedPositions.contains(column)) {
        final BlockPos pos = world.getTopSolidOrLiquidBlock(column);
        final Tree tree = getTree(trees, density, random);

        checkedPositions.add(column);
        if (tree.makeTree(manager, world, pos, random, true)) {
          treesPlaced++;
        }
      }
    }

    trees.removeIf(t -> !t.hasBushes());

    // Dense foliage chaparral/shrubland forests in dry & sparsely populated mountain regions
    // Similarly to Mediterranean and Californian areas
    if ((biome == BiomesWorld.MOUNTAINS || biome == BiomesWorld.MOUNTAINS_EDGE
         || biome == BiomesWorld.HIGH_HILLS ||
         biome == BiomesWorld.HIGH_HILLS_EDGE) &&
        (avgTemperature >= 4 + gauss)) {
      generateBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.3f, 60f + gauss, 200f + gauss,
                   4 + random.nextInt(10), trees);
    }

    // Mid-dense foliage chaparral/shrubland forests in dry & sparsely populated hilly landscapes
    // Similarly to South African areas
    if ((biome == BiomesWorld.ROLLING_HILLS || biome == BiomesWorld.HIGH_PLAINS) && (avgTemperature
                                                                                     >= 1 + gauss)) {
      generateBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.3f, 70f + gauss, 230f + gauss,
                   4 + random.nextInt(9), trees);
    }

    // Mid-dense foliage chaparral/shrubland forests in temperate regions
    // Similarly to steppes across Eurasian regions
    if ((biome == BiomesWorld.ROLLING_HILLS || biome == BiomesWorld.FIELDS
         || biome == BiomesWorld.FLATLANDS || biome == BiomesWorld.PLAINS ||
         biome == BiomesWorld.HIGH_PLAINS) && (avgTemperature <= 10 + gauss)) {
      generateBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.3f, 150f + gauss, 380f + gauss,
                   1 + random.nextInt(7), trees);
    }

    // More foliage bushes to woodlands
    if (!(biome == BiomesWorld.OCEAN || biome == BiomesWorld.DEEP_OCEAN)) {
      generateBush(random, chunkX, chunkZ, world, chunkData, 0.3f, 1f, 150f + gauss, 500f - gauss,
                   1 + random.nextInt(5), trees);
    }

    // Jungle Foliage
    if (!(biome == BiomesWorld.OCEAN || biome == BiomesWorld.DEEP_OCEAN) && (avgTemperature
                                                                             >= 10 + gauss)) {
      generateBush(random, chunkX, chunkZ, world, chunkData, 0.3f, 1f, 150f + gauss, 500f - gauss,
                   5 + random.nextInt(10), trees);
    }

    // Sparse foliage were it's otherwise just completely barren and boring...
    if (!(biome == BiomesWorld.OCEAN || biome == BiomesWorld.DEEP_OCEAN)) {
      generateBush(random, chunkX, chunkZ, world, chunkData, 0.0f, 0.2f, 260f + gauss, 500f - gauss,
                   random.nextInt(5), trees);
    }

    int treesPerChunk1 = (int) (density * 12 - 2);
    for (int i = random.nextInt(Math.round(1 / diversity)); i < (1 + density) * treesPerChunk1;
         i++) {
      final int x = (chunkX << 4) + random.nextInt(16) + 8;
      final int z = (chunkZ << 4) + random.nextInt(16) + 8;
      BlockPos blockPos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
      IBlockState down = world.getBlockState(blockPos.down());
      Biome b1 = world.getBiome(blockPos);
      //BlockPos blockPos = world.getHeight(chunkPos.add(random.nextInt(16) + 8, (random.nextInt(7) - random.nextInt(7)) * -1, random.nextInt(16) + 8));

      if ((BlockHelper.isGround(down) || world.getBlockState(blockPos)
                                              .getBlock() == ChunkGenClassic.FRESH_WATER.getBlock()) && b1 == BiomesWorld.BAYOU) {
        //if (TFCRegistries.TREES.getValue(TreesTFCF.BALD_CYPRESS).isValidLocation(avgTemperature, rainfall, density))
        if (10f <= avgTemperature && 38f >= avgTemperature && 180f <= rainfall && 500f >= rainfall
            &&
            blockPos.getY() >= WorldTypeClassic.SEALEVEL - 8) {
          int randomTree = random.nextInt(13) + 1;
          StructureGeneratorCorals gen = new StructureGeneratorCorals("bald_cypress/" + randomTree);
          generateStructure(gen, world, random, blockPos);

          //TFCRegistries.TREES.getValue(TreesTFCF.BALD_CYPRESS).makeTree(manager, world, blockPos, random, true);
          //TFCFlorae.getLog().warn("TFCFlorae: Bald Cypress attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
        }
      }
    }

    int treesPerChunk2 = (int) (density * 12 - 2);
    for (int i = random.nextInt(Math.round(1 / diversity)); i < (1 + density) * treesPerChunk2;
         i++) {
      final int x = (chunkX << 4) + random.nextInt(16) + 8;
      final int z = (chunkZ << 4) + random.nextInt(16) + 8;
      BlockPos blockPos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
      IBlockState down = world.getBlockState(blockPos.down());
      final Biome b1 = world.getBiome(blockPos);
      //BlockPos blockPos = world.getHeight(chunkPos.add(random.nextInt(16) + 8, (random.nextInt(7) - random.nextInt(7)) * -1, random.nextInt(16) + 8));

      if ((BlockHelper.isGround(down) || world.getBlockState(blockPos)
                                              .getBlock() == ChunkGenClassic.SALT_WATER.getBlock()) && b1 == BiomesWorld.MANGROVE) {
        //if (TFCRegistries.TREES.getValue(TreesTFCF.MANGROVE).isValidLocation(avgTemperature, rainfall, density))
        if (15f <= avgTemperature && 40f >= avgTemperature && 200f <= rainfall && 500f >= rainfall
            &&
            blockPos.getY() >= WorldTypeClassic.SEALEVEL - 8) {
          int randomTree = random.nextInt(13) + 1;
          StructureGeneratorCorals gen = new StructureGeneratorCorals("mangrove/" + randomTree);
          generateStructure(gen, world, random, blockPos);

          //TFCRegistries.TREES.getValue(TreesTFCF.MANGROVE).makeTree(manager, world, blockPos, random, true);
        }
      }
    }

    if (((rainfall >= 100 && density <= 0.3f) || rainfall <= 100)) {
      int treesPerChunk3 = (int) (density * 10 - 2);
      for (int i = random.nextInt(Math.round(1 / diversity)); i < (density) * treesPerChunk3; i++) {
        final int x = (chunkX << 4) + random.nextInt(16) + 8;
        final int z = (chunkZ << 4) + random.nextInt(16) + 8;
        BlockPos blockPos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
        IBlockState down = world.getBlockState(blockPos.down());
        final Biome b1 = world.getBiome(blockPos);

        if (b1 != BiomesWorld.BAYOU && b1 != BiomesWorld.MARSH && !BiomeHelper.isOceanicBiome(b1)
            && !BiomeHelper.isLakeBiome(b1) &&
            !BiomeHelper.isBeachBiome(b1) &&
            !BiomeHelper.isMesaBiome(b1)) {
          if ((Variant.isVariant(down, SAND) || BlockHelper.isSoilOrGravel(down)) &&
              (down != Blocks.HARDENED_CLAY && down != Blocks.STAINED_HARDENED_CLAY)) {
            if (15f <= avgTemperature && 40f >= avgTemperature && 65f <= rainfall
                && 150f >= rainfall &&
                blockPos.getY() >= WorldTypeClassic.SEALEVEL) {
              BlockJoshuaTreeFlower.get(TFCRegistries.TREES.getValue(TreesTFCF.JOSHUA_TREE))
                                   .generatePlant(world, blockPos, random, 8);
            }
          }
        }
      }
    }

    // Small bushes in high density areas
    // Density requirement is the same for jungles (kapok trees) to generate
    if (density > 0.6f && !trees.isEmpty()) {
      for (int i = 0; i < trees.size() * 4f * density; i++) {
        final int x = chunkX * 16 + random.nextInt(16) + 8;
        final int z = chunkZ * 16 + random.nextInt(16) + 8;
        final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
        final Tree tree = getTree(trees, density, random);
        ITreeGenerator bushGen = tree.getBushGen();
        if (bushGen != null && tree.hasBushes() && bushGen.canGenerateTree(world, pos, tree)) {
          bushGen.generateTree(manager, world, pos, tree, random, true);
        }
      }
    }
  }

  private Tree getTree(List<Tree> trees, float density, Random random) {
    if (trees.size() == 1 || random.nextFloat() < 0.8f - density * 0.4f) {
      return trees.get(0);
    }
    return trees.get(1 + random.nextInt(trees.size() - 1));
  }

  private void generateBush(Random random, int chunkX, int chunkZ, World world,
                            ICapabilityChunkData chunkData, float minFlora, float maxFlora,
                            float minRainfall, float maxRainfall,
                            int numBushes, List<Tree> trees) {
    final TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();
    final float density = chunkData.getFloraDensity();
    final float rainfall = chunkData.getRainfall();

    if (density > minFlora && density < maxFlora && rainfall > minRainfall && rainfall < maxRainfall
        && !trees.isEmpty()) {
      for (int i = 0; i < numBushes; i++) {
        final int x = chunkX * 16 + random.nextInt(16) + 8;
        final int z = chunkZ * 16 + random.nextInt(16) + 8;
        final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
        final Tree tree = getTree(trees, density, random);
        ITreeGenerator bushGen = tree.getBushGen();
        if (bushGen != null && tree.hasBushes() && bushGen.canGenerateTree(world, pos, tree)) {
          bushGen.generateTree(manager, world, pos, tree, random, true);
        }
      }
    }
  }

  private void generateStructure(WorldGenerator generator, World world, Random random,
                                 BlockPos pos) {
    generator.generate(world, random, pos);
  }
}
