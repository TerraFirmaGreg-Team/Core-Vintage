package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;

import net.minecraft.block.BlockHardenedClay;
import net.minecraft.block.BlockStainedHardenedClay;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.IWorldGenerator;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.PlantsTFCF;
import net.dries007.tfc.util.climate.Climate;
import tfcflorae.ConfigTFCF;

import java.util.Random;

public class GeneratorPlants implements IWorldGenerator {

  private final WorldGenPlants plantGen;
  private final float waterCountConfig = ConfigTFCF.General.WORLD.waterCount;
  private final float waterTallCountConfig = ConfigTFCF.General.WORLD.waterTallCount;
  private final float waterSeaCountConfig = ConfigTFCF.General.WORLD.waterSeaCount;
  private final float waterTallSeaCountConfig = ConfigTFCF.General.WORLD.waterTallSeaCount;
  private final float waterSeaAlgaeCountConfig = ConfigTFCF.General.WORLD.waterSeaAlgaeCount;
  private final float hangingCountConfig = ConfigTFCF.General.WORLD.hangingCount;
  private final float beardedMossConfig = ConfigTFCF.General.WORLD.beardedMossCount;
  private final float grassCountConfig = ConfigTFCF.General.WORLD.grassCount;
  private final float tallGrassCountConfig = ConfigTFCF.General.WORLD.tallGrassCount;
  private final float tallCountConfig = ConfigTFCF.General.WORLD.tallPlantCount;
  private final float epiphyteCountConfig = ConfigTFCF.General.WORLD.epiphyteCount;
  private final float standardCountConfig = ConfigTFCF.General.WORLD.standardCount;
  private int waterCount = 1;
  private int waterSeaCount = 1;
  private int hangingCount = 1;
  private int grassCount = 1;
  private int tallGrassCount = 1;
  private int tallCount = 1;
  private int epiphyteCount = 1;
  private int standardCount = 1;

  public GeneratorPlants() {
    plantGen = new WorldGenPlants();

    for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
      switch (plant.getPlantType()) {
        case WATER:
        case TALL_WATER:
          waterCount++;
          break;
        case WATER_SEA:
        case TALL_WATER_SEA:
          waterSeaCount++;
          break;
        case HANGING:
          hangingCount++;
          break;
        case SHORT_GRASS:
          grassCount++;
          break;
        case TALL_GRASS:
          tallGrassCount++;
          break;
        case TALL_PLANT:
          tallCount++;
          break;
        case EPIPHYTE:
          epiphyteCount++;
          break;
        default:
          standardCount++;
      }
    }
  }

  @Override
  public void generate(Random rng, int chunkX, int chunkZ, World world,
          IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    BlockPos chunkPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
    ChunkPos forgeChunkPos = new ChunkPos(
            chunkPos); // actual ChunkPos instead of BlockPos, used for events
    MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rng, forgeChunkPos));

    var data = CapabilityChunkData.get(world, chunkPos);
    if (!data.isInitialized()) {
      return;
    }

    Biome b = world.getBiome(chunkPos);
    final float avgTemperature = Climate.getAvgTemp(world, chunkPos);
    final float rainfall = ProviderChunkData.getRainfall(world, chunkPos);
    final float floraDensity = data.getFloraDensity(); // Use for various plant based decoration (tall grass, those vanilla jungle shrub things, etc.)
    final float floraDiversity = data.getFloraDiversity();

    // this.chunkPos = chunkPos;
    // todo: settings for all the rarities?

    if (TerrainGen.decorate(world, rng, forgeChunkPos,
            DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
      for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
        if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);
          switch (plant.getPlantType()) {
            case WATER: {
              for (int i = rng.nextInt(Math.round(waterCount / floraDiversity));
                      i < (5 + floraDensity) * waterCountConfig; i++) {
                BlockPos blockPos = world.getHeight(
                        chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                IBlockState blockPosState = world.getBlockState(blockPos.down());
                if (!(blockPosState instanceof BlockHardenedClay
                        || blockPosState instanceof BlockStainedHardenedClay)) {
                  plantGen.generate(world, rng, blockPos);
                }
              }
              break;
            }
            case TALL_WATER: {
              for (int i = rng.nextInt(Math.round(waterCount / floraDiversity));
                      i < (5 + floraDensity) * waterTallCountConfig; i++) {
                BlockPos blockPos = world.getHeight(
                        chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                IBlockState blockPosState = world.getBlockState(blockPos.down());
                if (!(blockPosState instanceof BlockHardenedClay
                        || blockPosState instanceof BlockStainedHardenedClay)) {
                  plantGen.generate(world, rng, blockPos);
                }
              }
              break;
            }
            case WATER_SEA: {
              if (floraDensity >= 0.2f && floraDensity <= 0.6f && (
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_ALGAE) ||
                              plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_SEA_WHIP) ||
                              plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SEA_ANEMONE))) {
                for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity));
                        i < floraDensity * waterSeaAlgaeCountConfig; i++) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              } else if (plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_ALGAE) ||
                      plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.RED_SEA_WHIP) ||
                      plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.SEA_ANEMONE)) {
                for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity));
                        i < (5 + floraDensity) * waterSeaCountConfig; i++) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
              break;
            }
            case TALL_WATER_SEA: {
              if (floraDensity >= 0.2f && plant != TFCRegistries.PLANTS.getValue(
                      PlantsTFCF.SEAGRASS)) {
                for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity));
                        i < (5 + floraDensity) * waterTallSeaCountConfig; i++) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              } else if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SEAGRASS)) {
                for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity));
                        i < (5 + floraDensity) * waterSeaCountConfig; i++) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
              break;
            }
            case EPIPHYTE: {
              if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.MONSTERA_EPIPHYTE)) {
                for (float i = rng.nextInt(Math.round(epiphyteCount / floraDiversity));
                        i < (5 + floraDensity + floraDiversity) * epiphyteCountConfig; i++) {
                  if (rainfall >= (260f + 4f * rng.nextGaussian()) &&
                          Climate.getAvgTemp(world, chunkPos) >= (20f + 2f * rng.nextGaussian())) {
                    BlockPos blockPos = world.getHeight(
                            chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                    IBlockState blockPosState = world.getBlockState(blockPos.down());
                    if (!(blockPosState instanceof BlockHardenedClay
                            || blockPosState instanceof BlockStainedHardenedClay)) {
                      plantGen.generate(world, rng, blockPos);
                    }
                  }
                }
              }
              break;
            }
            case HANGING: {
              if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA)) {
                for (float i = rng.nextInt(
                        Math.round((hangingCount + floraDensity) / floraDiversity));
                        i < (3 + floraDensity + floraDiversity) * hangingCountConfig; i++) {
                  if (floraDensity >= 0.1f && rainfall >= (260f + 4f * rng.nextGaussian()) &&
                          Climate.getAvgTemp(world, chunkPos) >= (20f + 2f * rng.nextGaussian())) {
                    BlockPos blockPos = world.getHeight(
                            chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                    IBlockState blockPosState = world.getBlockState(blockPos.down());
                    if (!(blockPosState instanceof BlockHardenedClay
                            || blockPosState instanceof BlockStainedHardenedClay)) {
                      plantGen.generate(world, rng, blockPos);
                    }
                  }
                }
              } else if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) &&
                      (b == BiomesWorld.SWAMPLAND || b == BiomesWorld.LAKE || b == BiomesWorld.BAYOU
                              || b == BiomesWorld.MANGROVE ||
                              b == BiomesWorld.MARSH)) {
                for (float i = rng.nextInt(Math.round(hangingCount / floraDiversity));
                        i < (2 + floraDensity) * beardedMossConfig; i++) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
              break;
            }
            case TALL_PLANT: {
              for (float i = rng.nextInt(Math.round((tallCount + 8) / floraDiversity));
                      i < (1 + floraDensity) * tallCountConfig; i++) {
                if (rainfall >= (260f + 4f * rng.nextGaussian()) &&
                        Climate.getAvgTemp(world, chunkPos) >= (20f + 2f * rng.nextGaussian())) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
              if (plant == TFCRegistries.PLANTS.getValue(DefaultPlants.FOXGLOVE) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.ROSE) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.SAPPHIRE_TOWER) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HYDRANGEA) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LILAC) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.PEONY) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SUNFLOWER) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HIBISCUS) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.MARIGOLD)) {
                for (float i = rng.nextInt(Math.round((tallCount + 2) / floraDiversity));
                        i < (2 + floraDensity + floraDiversity) * tallCountConfig; i++) {
                  if (floraDensity <= Math.abs(0.2f - (rng.nextGaussian() / 20))
                          && b == BiomesWorld.MEADOWS) {
                    BlockPos blockPos = world.getHeight(
                            chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                    IBlockState blockPosState = world.getBlockState(blockPos.down());
                    if (!(blockPosState instanceof BlockHardenedClay
                            || blockPosState instanceof BlockStainedHardenedClay)) {
                      plantGen.generate(world, rng, blockPos);
                    }
                  }
                }
              }
              break;
            }
            case STANDARD: {
              for (float i = rng.nextInt(Math.round((standardCount + 8) / floraDiversity));
                      i < (1 + floraDensity) * standardCountConfig; i++) {
                if (rainfall >= (260f + 4f * rng.nextGaussian()) &&
                        Climate.getAvgTemp(world, chunkPos) >= (20f + 2f * rng.nextGaussian())) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
              if (plant == TFCRegistries.PLANTS.getValue(DefaultPlants.ALLIUM) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.BLACK_ORCHID) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.BLOOD_LILY) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.BLUE_ORCHID) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.BUTTERFLY_MILKWEED) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.CALENDULA) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.CANNA) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.DANDELION) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.GOLDENROD) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.GRAPE_HYACINTH) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.HOUSTONIA) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.LABRADOR_TEA) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MEADS_MILKWEED) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.NASTURTIUM) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.OXEYE_DAISY) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.POPPY) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.PRIMROSE) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.PULSATILLA) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.SACRED_DATURA) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.SNAPDRAGON_PINK) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.SNAPDRAGON_RED) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.SNAPDRAGON_WHITE) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.SNAPDRAGON_YELLOW) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.STRELITZIA) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.TRILLIUM) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.TROPICAL_MILKWEED) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.TULIP_ORANGE) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.TULIP_PINK) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.TULIP_RED) ||
                      plant == TFCRegistries.PLANTS.getValue(DefaultPlants.TULIP_WHITE) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.CHAMOMILE) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LAVANDULA) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LILY_OF_THE_VALLEY) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.ANTHURIUM) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BLUE_GINGER) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.DESERT_FLAME) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HELICONIA) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.KANGAROO_PAW) ||
                      plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SILVER_SPURFLOWER)) {
                for (float i = rng.nextInt(Math.round(standardCount / floraDiversity));
                        i < (3 + floraDensity + floraDiversity) * standardCountConfig; i++) {
                  if (floraDensity <= Math.abs(0.2f - (rng.nextGaussian() / 20))
                          && b == BiomesWorld.MEADOWS) {
                    BlockPos blockPos = world.getHeight(
                            chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                    IBlockState blockPosState = world.getBlockState(blockPos.down());
                    if (!(blockPosState instanceof BlockHardenedClay
                            || blockPosState instanceof BlockStainedHardenedClay)) {
                      plantGen.generate(world, rng, blockPos);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    if (TerrainGen.decorate(world, rng, forgeChunkPos,
            DecorateBiomeEvent.Decorate.EventType.GRASS)) {
      for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
        if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);
          switch (plant.getPlantType()) {
            case SHORT_GRASS: {
                            /*if (plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_BARLEY) || 
                                plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_RICE) || 
                                plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_WHEAT))
                            {*/
              for (int i = rng.nextInt(Math.round(grassCount / floraDiversity));
                      i < (5 + floraDensity) * grassCountConfig; i++) {
                if (rainfall >= (260f + 4f * rng.nextGaussian()) &&
                        Climate.getAvgTemp(world, chunkPos) >= (20f + 2f * rng.nextGaussian())) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                } else if (rainfall >= (90f + 4f * rng.nextGaussian()) && rainfall <= 255f) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
              for (int j = rng.nextInt(Math.round(grassCount / floraDiversity));
                      j < (2 + floraDensity) * grassCountConfig; j++) {
                if (rainfall >= (90f + 4f * rng.nextGaussian()) && rainfall <= 255f) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
                            /*}
                            if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_BARLEY) || 
                                plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_RICE) || 
                                plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_WHEAT))
                            {
                                if (floraDensity <= Math.abs(0.2f - (rng.nextGaussian() / 20)) && b == BiomesWorld.FIELDS)
                                {
                                    for (int i = rng.nextInt(Math.round((tallGrassCount + 4) / floraDiversity)); i < (1 + floraDensity) * ConfigTFC.General.FOOD.cropRarity; i++)
                                    {
                                        BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                        IBlockState blockPosState = world.getBlockState(blockPos.down());
                                        if (!(blockPosState instanceof BlockHardenedClay || blockPosState instanceof BlockStainedHardenedClay))
                                        {
                                            plantGen.generate(world, rng, blockPos);
                                        }
                                    }
                                }
                                else if (floraDensity <= Math.abs(0.2f - (rng.nextGaussian() / 20)) && (b != BiomesWorld.FIELDS || b != BiomesWorld.MEADOWS || b != BiomesWorld.FLATLANDS))
                                {
                                    for (int i = rng.nextInt(Math.round((tallGrassCount + 12) / floraDiversity)); i < (1 + floraDensity) * (ConfigTFC.General.FOOD.cropRarity / 2D); i++)
                                    {
                                        BlockPos blockPos = world.getHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                                        IBlockState blockPosState = world.getBlockState(blockPos.down());
                                        if (!(blockPosState instanceof BlockHardenedClay || blockPosState instanceof BlockStainedHardenedClay))
                                        {
                                            plantGen.generate(world, rng, blockPos);
                                        }
                                    }
                                }
                            }*/
              break;
            }
            case TALL_GRASS: {
              if (plant != TFCRegistries.PLANTS.getValue(PlantsTFCF.SAWGRASS)) {
                for (int i = rng.nextInt(Math.round((tallGrassCount + 8) / floraDiversity));
                        i < (3 + floraDensity) * tallGrassCountConfig; i++) {
                  if (rainfall >= (260f + 4f * rng.nextGaussian()) &&
                          Climate.getAvgTemp(world, chunkPos) >= (20f + 2f * rng.nextGaussian())) {
                    BlockPos blockPos = world.getHeight(
                            chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                    IBlockState blockPosState = world.getBlockState(blockPos.down());
                    if (!(blockPosState instanceof BlockHardenedClay
                            || blockPosState instanceof BlockStainedHardenedClay)) {
                      plantGen.generate(world, rng, blockPos);
                    }
                  }
                }
                for (int j = rng.nextInt(Math.round(tallGrassCount / floraDiversity));
                        j < (1 + floraDensity) * tallGrassCountConfig; j++) {
                  if (rainfall >= (90f + 4f * rng.nextGaussian()) && rainfall <= 255f) {
                    BlockPos blockPos = world.getHeight(
                            chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                    IBlockState blockPosState = world.getBlockState(blockPos.down());
                    if (!(blockPosState instanceof BlockHardenedClay
                            || blockPosState instanceof BlockStainedHardenedClay)) {
                      plantGen.generate(world, rng, blockPos);
                    }
                  }
                }
              }
              if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SAWGRASS)
                      && b == BiomesWorld.MARSH) {
                for (int k = rng.nextInt(Math.round(grassCount / floraDiversity));
                        k < (5 + floraDensity) * grassCountConfig; k++) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
              if (plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.SAWGRASS) &&
                      (b == BiomesWorld.BAYOU || b == BiomesWorld.MANGROVE)) {
                for (int k = rng.nextInt(Math.round(grassCount / floraDiversity));
                        k < (3 + floraDensity) * tallGrassCountConfig; k++) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
              if (plant == TFCRegistries.PLANTS.getValue(DefaultPlants.PAMPAS_GRASS) &&
                      (b == BiomesWorld.BAYOU || b == BiomesWorld.MANGROVE || b == BiomesWorld.MARSH)) {
                for (int k = rng.nextInt(Math.round(grassCount / floraDiversity));
                        k < (2 + floraDensity) * tallGrassCountConfig; k++) {
                  BlockPos blockPos = world.getHeight(
                          chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
                  IBlockState blockPosState = world.getBlockState(blockPos.down());
                  if (!(blockPosState instanceof BlockHardenedClay
                          || blockPosState instanceof BlockStainedHardenedClay)) {
                    plantGen.generate(world, rng, blockPos);
                  }
                }
              }
            }
          }
        }
      }
    }
    MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rng, forgeChunkPos));
  }
}
