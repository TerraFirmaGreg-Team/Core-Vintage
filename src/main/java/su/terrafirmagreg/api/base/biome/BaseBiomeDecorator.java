package su.terrafirmagreg.api.base.biome;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.category.PlantCategories;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorPlant;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorSand;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorWildCrops;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import net.dries007.tfc.util.climate.Climate;

import java.util.Random;

import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.CACTUS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.CREEPING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.DESERT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.DESERT_TALL_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.DRY;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.DRY_TALL_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.EMERGENT_TALL_WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.EMERGENT_TALL_WATER_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.EPIPHYTE;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.FLOATING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.FLOATING_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.HANGING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.MUSHROOM;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.REED;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.REED_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.SHORT_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.STANDARD;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_REED;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_REED_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER_SEA;

public class BaseBiomeDecorator extends BiomeDecorator {

  private final int lilyPadPerChunk;
  private final int waterPlantsPerChunk;
  private final GeneratorWildCrops wildCropsGen;

  private final GeneratorPlant plantGen;
  private int standardCount = 1;
  private int tallCount = 1;
  private int creepingCount = 1;
  private int hangingCount = 1;
  private int floatingCount = 1;
  private int floatingSeaCount = 1;
  private int desertCount = 1;
  private int dryCount = 1;
  private int cactusCount = 1;
  private int grassCount = 1;
  private int tallGrassCount = 1;
  private int epiphyteCount = 1;
  private int reedCount = 1;
  private int reedSeaCount = 1;
  private int waterCount = 1;
  private int waterSeaCount = 1;
  private int mushroomCount = 1;

  public BaseBiomeDecorator(int lilyPadPerChunk, int waterPlantsPerChunk) {
    this.lilyPadPerChunk = lilyPadPerChunk;
    this.waterPlantsPerChunk = waterPlantsPerChunk;

    this.clayGen = null;
    this.sandGen = null;
    this.gravelGen = null;
    this.flowerGen = null;
    this.mushroomBrownGen = null;
    this.mushroomRedGen = null;
    this.bigMushroomGen = null;

    this.plantGen = new GeneratorPlant();

    this.sandGen = new GeneratorSand(7);
    this.wildCropsGen = new GeneratorWildCrops();

    for (PlantType plant : PlantType.getTypes()) {
      if (plant.getCategory().equals(TALL_PLANT)) {
        tallCount++;
      } else if (plant.getCategory().equals(CREEPING)) {
        creepingCount++;
      } else if (plant.getCategory().equals(HANGING)) {
        hangingCount++;
      } else if (plant.getCategory().equals(FLOATING)) {
        floatingCount++;
      } else if (plant.getCategory().equals(FLOATING_SEA)) {
        floatingSeaCount++;
      } else if (plant.getCategory().equals(DESERT) || plant.getCategory().equals(DESERT_TALL_PLANT)) {
        desertCount++;
      } else if (plant.getCategory().equals(DRY) || plant.getCategory().equals(DRY_TALL_PLANT)) {
        dryCount++;
      } else if (plant.getCategory().equals(CACTUS)) {
        cactusCount++;
      } else if (plant.getCategory().equals(SHORT_GRASS)) {
        grassCount++;
      } else if (plant.getCategory().equals(TALL_GRASS)) {
        tallGrassCount++;
      } else if (plant.getCategory().equals(EPIPHYTE)) {
        epiphyteCount++;
      } else if (plant.getCategory().equals(REED) || plant.getCategory().equals(TALL_REED)) {
        reedCount++;
      } else if (plant.getCategory().equals(REED_SEA) || plant.getCategory().equals(TALL_REED_SEA)) {
        reedSeaCount++;
      } else if (plant.getCategory().equals(WATER) || plant.getCategory().equals(TALL_WATER) || plant.getCategory().equals(EMERGENT_TALL_WATER)) {
        waterCount++;
      } else if (plant.getCategory().equals(WATER_SEA) || plant.getCategory().equals(TALL_WATER_SEA) || plant.getCategory().equals(EMERGENT_TALL_WATER_SEA)) {
        waterSeaCount++;
      } else if (plant.getCategory().equals(MUSHROOM)) {
        mushroomCount++;
      } else {
        standardCount++;
      }
    }
  }

  @Override
  public void decorate(final World world, final Random rng, final Biome biome,
                       final BlockPos chunkPos) {
    ChunkPos forgeChunkPos = new ChunkPos(
      chunkPos); // actual ChunkPos instead of BlockPos, used for events
    MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(world, rng, forgeChunkPos));

    var data = CapabilityChunkData.get(world, chunkPos);
    if (!data.isInitialized()) {
      return;
    }

    final float avgTemperature = Climate.getAvgTemp(world, chunkPos);
    final float rainfall = ProviderChunkData.getRainfall(world, chunkPos);
    final float floraDensity = data.getFloraDensity(); // Use for various plant based decoration (tall grass, those vanilla jungle shrub things, etc.)
    final float floraDiversity = data.getFloraDiversity();

    this.chunkPos = chunkPos;
    // todo: settings for all the rarities?

    if (TerrainGen.decorate(world, rng, forgeChunkPos,
                            DecorateBiomeEvent.Decorate.EventType.SHROOM)) {
      for (PlantType plant : PlantType.getTypes()) {
        if (plant.getCategory() == PlantCategories.MUSHROOM && plant.isValidTempForWorldGen(
          avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);

          for (float i = rng.nextInt(Math.round(mushroomCount / floraDiversity));
               i < (1 + floraDensity) * 5; i++) {
            BlockPos blockPos = world.getHeight(
              chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
            plantGen.generate(world, rng, blockPos);
          }
        }
      }
    }

    if (TerrainGen.decorate(world, rng, forgeChunkPos,
                            DecorateBiomeEvent.Decorate.EventType.CACTUS)) {
      for (PlantType plant : PlantType.getTypes()) {
        if (plant.getCategory() == PlantCategories.CACTUS && plant.isValidTempForWorldGen(
          avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);

          for (int i = rng.nextInt(Math.round((cactusCount + 32) / floraDiversity));
               i < (1 + floraDensity) * 3; i++) {
            BlockPos blockPos = world.getHeight(
              chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
            plantGen.generate(world, rng, blockPos);
          }

        }
      }
    }

    if (TerrainGen.decorate(world, rng, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.LILYPAD)) {
      for (PlantType plant : PlantType.getTypes()) {
        if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);
          if (plant.getCategory().equals(FLOATING)) {
            for (int i = rng.nextInt(Math.round(floatingCount / floraDiversity));
                 i < floraDensity * lilyPadPerChunk; i++) {
              BlockPos blockPos = world.getPrecipitationHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(FLOATING_SEA)) {
            for (int i = rng.nextInt(Math.round((floatingSeaCount + 64) / floraDiversity));
                 i < floraDensity * lilyPadPerChunk; i++) {
              BlockPos blockPos = world.getPrecipitationHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          }
        }
      }
    }

    if (TerrainGen.decorate(world, rng, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.REED)) {
      for (PlantType plant : PlantType.getTypes()) {
        if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);
          if (plant.getCategory().equals(REED) || plant.getCategory().equals(TALL_REED)) {
            for (int i = rng.nextInt(Math.round(reedCount / floraDiversity));
                 i < (1 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(REED_SEA) || plant.getCategory().equals(TALL_REED_SEA)) {
            for (int i = rng.nextInt(Math.round(reedSeaCount / floraDiversity));
                 i < (1 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          }
        }
      }
    }

    if (TerrainGen.decorate(world, rng, forgeChunkPos,
                            DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
      for (PlantType plant : PlantType.getTypes()) {
        if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);
          if (plant.getCategory().equals(WATER) || plant.getCategory().equals(TALL_WATER) || plant.getCategory().equals(EMERGENT_TALL_WATER)) {
            for (int i = rng.nextInt(Math.round(waterCount / floraDiversity)); i < floraDensity * waterPlantsPerChunk; i++) {
              BlockPos blockPos = world.getPrecipitationHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(WATER_SEA) || plant.getCategory().equals(TALL_WATER_SEA) || plant.getCategory()
                                                                                                                 .equals(EMERGENT_TALL_WATER_SEA)) {
            for (int i = rng.nextInt(Math.round(waterSeaCount / floraDiversity)); i < floraDensity * waterPlantsPerChunk; i++) {
              BlockPos blockPos = world.getPrecipitationHeight(chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(EPIPHYTE)) {
            for (float i = rng.nextInt(Math.round(epiphyteCount / floraDiversity));
                 i < (1 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(CREEPING)) {
            for (float i = rng.nextInt(Math.round((creepingCount + 32) / floraDiversity));
                 i < (1 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(HANGING)) {
            for (float i = rng.nextInt(Math.round(hangingCount / floraDiversity));
                 i < (1 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(TALL_PLANT)) {
            for (float i = rng.nextInt(Math.round((tallCount + 8) / floraDiversity));
                 i < (1 + floraDensity) * 3; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(STANDARD)) {
            for (float i = rng.nextInt(Math.round((standardCount + 32) / floraDiversity));
                 i < (1 + floraDensity) * 3; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          }
        }
      }
    }

    if (TerrainGen.decorate(world, rng, forgeChunkPos,
                            DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH)) {
      for (PlantType plant : PlantType.getTypes()) {
        if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
          plantGen.setGeneratedPlant(plant);
          if (plant.getCategory().equals(DESERT) || plant.getCategory().equals(DESERT_TALL_PLANT)) {
            for (float i = rng.nextInt(Math.round((desertCount + 16) / floraDiversity));
                 i < (1 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(DRY) || plant.getCategory().equals(DRY_TALL_PLANT)) {
            for (float i = rng.nextInt(Math.round((dryCount + 16) / floraDiversity));
                 i < (1 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
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
          if (plant.getCategory().equals(SHORT_GRASS)) {
            for (int i = rng.nextInt(Math.round(grassCount / floraDiversity));
                 i < (3 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          } else if (plant.getCategory().equals(TALL_GRASS)) {
            for (int i = rng.nextInt(Math.round((tallGrassCount + 8) / floraDiversity));
                 i < (1 + floraDensity) * 5; i++) {
              BlockPos blockPos = world.getHeight(
                chunkPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8));
              plantGen.generate(world, rng, blockPos);
            }
          }
        }
      }
    }

    MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rng, forgeChunkPos));
  }
}
