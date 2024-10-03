package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.type.PlantTypes;
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

import net.dries007.tfc.util.climate.Climate;
import tfcflorae.ConfigTFCF;

import java.util.Random;

import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.EPIPHYTE;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.HANGING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.SHORT_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.STANDARD;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER_SEA;

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

    for (PlantType plant : PlantType.getTypes()) {
      if (plant.getCategory().equals(WATER) || plant.getCategory().equals(TALL_WATER)) {
        waterCount++;
      } else if (plant.getCategory().equals(WATER_SEA) || plant.getCategory().equals(TALL_WATER_SEA)) {
        waterSeaCount++;
      } else if (plant.getCategory().equals(HANGING)) {
        hangingCount++;
      } else if (plant.getCategory().equals(SHORT_GRASS)) {
        grassCount++;
      } else if (plant.getCategory().equals(TALL_GRASS)) {
        tallGrassCount++;
      } else if (plant.getCategory().equals(TALL_PLANT)) {
        tallCount++;
      } else if (plant.getCategory().equals(EPIPHYTE)) {
        epiphyteCount++;
      } else {
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
      for (PlantType plant : PlantType.getTypes()) {
        if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);
          if (plant.getCategory().equals(WATER)) {
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
          } else if (plant.getCategory().equals(TALL_WATER)) {
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
          } else if (plant.getCategory().equals(WATER_SEA)) {
            if (floraDensity >= 0.2f && floraDensity <= 0.6f && (
              plant == PlantTypes.RED_ALGAE ||
              plant == PlantTypes.RED_SEA_WHIP ||
              plant == PlantTypes.SEA_ANEMONE)) {
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
            } else if (plant != PlantTypes.RED_SEA_WHIP || plant != PlantTypes.SEA_ANEMONE) {
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
          } else if (plant.getCategory().equals(TALL_WATER_SEA)) {
            if (floraDensity >= 0.2f && plant != PlantTypes.SEAGRASS) {
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
            } else if (plant == PlantTypes.SEAGRASS) {
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
          } else if (plant.getCategory().equals(EPIPHYTE)) {
            if (plant == PlantTypes.MONSTERA_EPIPHYTE) {
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
          } else if (plant.getCategory().equals(HANGING)) {
            if (plant == PlantTypes.GLOW_VINE ||
                plant == PlantTypes.HANGING_VINE ||
                plant == PlantTypes.JUNGLE_VINE ||
                plant == PlantTypes.LIANA) {
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
            } else if (plant == PlantTypes.BEARDED_MOSS &&
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
          } else if (plant.getCategory().equals(TALL_PLANT)) {
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
            if (plant == PlantTypes.FOXGLOVE ||
                plant == PlantTypes.ROSE ||
                plant == PlantTypes.SAPPHIRE_TOWER ||
                plant == PlantTypes.HYDRANGEA ||
                plant == PlantTypes.LILAC ||
                plant == PlantTypes.PEONY ||
                plant == PlantTypes.SUNFLOWER ||
                plant == PlantTypes.HIBISCUS ||
                plant == PlantTypes.MARIGOLD) {
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
          } else if (plant.getCategory().equals(STANDARD)) {
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
            if (plant == PlantTypes.ALLIUM ||
                plant == PlantTypes.BLACK_ORCHID ||
                plant == PlantTypes.BLOOD_LILY ||
                plant == PlantTypes.BLUE_ORCHID ||
                plant == PlantTypes.BUTTERFLY_MILKWEED ||
                plant == PlantTypes.CALENDULA ||
                plant == PlantTypes.CANNA ||
                plant == PlantTypes.DANDELION ||
                plant == PlantTypes.GOLDENROD ||
                plant == PlantTypes.GRAPE_HYACINTH ||
                plant == PlantTypes.HOUSTONIA ||
                plant == PlantTypes.LABRADOR_TEA ||
                plant == PlantTypes.MEADS_MILKWEED ||
                plant == PlantTypes.NASTURTIUM ||
                plant == PlantTypes.OXEYE_DAISY ||
                plant == PlantTypes.POPPY ||
                plant == PlantTypes.PRIMROSE ||
                plant == PlantTypes.PULSATILLA ||
                plant == PlantTypes.SACRED_DATURA ||
                plant == PlantTypes.SNAPDRAGON_PINK ||
                plant == PlantTypes.SNAPDRAGON_RED ||
                plant == PlantTypes.SNAPDRAGON_WHITE ||
                plant == PlantTypes.SNAPDRAGON_YELLOW ||
                plant == PlantTypes.STRELITZIA ||
                plant == PlantTypes.TRILLIUM ||
                plant == PlantTypes.TROPICAL_MILKWEED ||
                plant == PlantTypes.TULIP_ORANGE ||
                plant == PlantTypes.TULIP_PINK ||
                plant == PlantTypes.TULIP_RED ||
                plant == PlantTypes.TULIP_WHITE ||
                plant == PlantTypes.CHAMOMILE ||
                plant == PlantTypes.LAVANDULA ||
                plant == PlantTypes.LILY_OF_THE_VALLEY ||
                plant == PlantTypes.ANTHURIUM ||
                plant == PlantTypes.BLUE_GINGER ||
                plant == PlantTypes.DESERT_FLAME ||
                plant == PlantTypes.HELICONIA ||
                plant == PlantTypes.KANGAROO_PAW ||
                plant == PlantTypes.SILVER_SPURFLOWER) {
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

    if (TerrainGen.decorate(world, rng, forgeChunkPos,
                            DecorateBiomeEvent.Decorate.EventType.GRASS)) {
      for (PlantType plant : PlantType.getTypes()) {
        if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);
          if (plant.getCategory().equals(SHORT_GRASS)) {/*if (plant != PlantTypes.WILD_BARLEY ||
                                plant != PlantTypes.WILD_RICE ||
                                plant != PlantTypes.WILD_WHEAT)
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
                            if (plant == PlantTypes.WILD_BARLEY ||
                                plant == PlantTypes.WILD_RICE ||
                                plant == PlantTypes.WILD_WHEAT)
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
          } else if (plant.getCategory().equals(TALL_GRASS)) {
            if (plant != PlantTypes.SAWGRASS) {
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
            if (plant == PlantTypes.SAWGRASS
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
            if (plant == PlantTypes.SAWGRASS &&
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
            if (plant == PlantTypes.PAMPAS_GRASS &&
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
    MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rng, forgeChunkPos));
  }
}
