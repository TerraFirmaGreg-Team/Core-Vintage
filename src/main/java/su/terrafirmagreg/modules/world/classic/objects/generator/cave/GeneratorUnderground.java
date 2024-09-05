package su.terrafirmagreg.modules.world.classic.objects.generator.cave;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.PlantsTFCF;
import net.dries007.tfc.util.climate.Climate;
import tfcflorae.ConfigTFCF;

import java.util.Random;

import static su.terrafirmagreg.data.MathConstants.RNG;

public class GeneratorUnderground implements IWorldGenerator {

  private final GeneratorCaveMushrooms undergroundMushrooms;
  private final GeneratorCaveMoss undergroundMoss;
  private final GeneratorCaveVines undergroundVines;
  private final GeneratorCaveCreepingVines undergroundCreepingVines;
  //private final WorldGenUnderground undergroundPlant = new WorldGenUnderground();

  private final float fungiUndergroundCount = ConfigTFCF.General.WORLD.fungiUndergroundCount;
  private final float hangingVinesUndergroundCount = ConfigTFCF.General.WORLD.hangingVinesUndergroundCount;
  private final float creepingVinesUndergroundCount = ConfigTFCF.General.WORLD.creepingVinesUndergroundCount;
  private final float creepingUndergroundCount = ConfigTFCF.General.WORLD.creepingUndergroundCount;

  public GeneratorUnderground() {
    undergroundMushrooms = new GeneratorCaveMushrooms();
    undergroundCreepingVines = new GeneratorCaveCreepingVines();
    undergroundVines = new GeneratorCaveVines();
    undergroundMoss = new GeneratorCaveMoss();
  }

  // Somewhat works
  @Override
  public void generate(Random rng, int chunkX, int chunkZ, World world,
      IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (!(chunkGenerator instanceof ChunkGenClassic)) {
      return;
    }

    int y = rng.nextInt(WorldTypeClassic.SEALEVEL);
    BlockPos chunkPos = new BlockPos(chunkX << 4, y, chunkZ << 4);

    var data = CapabilityChunkData.get(world, chunkPos);
    Biome b = world.getBiome(chunkPos);
    final float avgTemperature = Climate.getAvgTemp(world, chunkPos);
    final float rainfall = ProviderChunkData.getRainfall(world, chunkPos);
    final float floraDensity = data.getFloraDensity();
    final float floraDiversity = data.getFloraDiversity();

    for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
      if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
        undergroundVines.setGeneratedPlant(plant);
        undergroundCreepingVines.setGeneratedPlant(plant);
        undergroundMoss.setGeneratedPlant(plant);

        switch (plant.getPlantType()) {
          case MUSHROOM: {
            if (avgTemperature >= -13f && avgTemperature <= 50f && rainfall >= 250f
                && rainfall <= 500) {
              int plantCount = (RNG.nextInt(3) + 1);
              for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
                  i < (floraDensity + floraDiversity) * fungiUndergroundCount; i++) {
                BlockPos blockPos = chunkPos.add(rng.nextInt(16) + 8, rng.nextInt(16) + 8,
                    rng.nextInt(16) + 8);
                //TFCFlorae.getLog().warn("TFCFlorae: Mushroom " + plant + " attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 15 && blockPos.getY() > 20) {
                  undergroundMushrooms.generate(world, rng, blockPos);
                }
              }
              break;
            }
          }
          case HANGING: {
            switch (RNG.nextInt(2)) {
              case 0: {
                if ((b != BiomesWorld.OCEAN) && (
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) ||
                        plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) ||
                        plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) ||
                        plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE) ||
                        plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA))) {
                  int y1 =
                      rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER2)
                          + WorldTypeClassic.ROCKLAYER2;
                  BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

                  int plantCount = (RNG.nextInt(3) + 1);
                  for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
                      i < (floraDensity + floraDiversity) * hangingVinesUndergroundCount; i++) {
                    BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0,
                        rng.nextInt(16) + 8);
                    //TFCFlorae.getLog().warn("TFCFlorae: Vines " + plant + " attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                    if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 5
                        && blockPos.getY() > WorldTypeClassic.ROCKLAYER2) {
                      undergroundVines.generate(world, rng, blockPos);
                    }
                  }
                  break;
                }
              }
              case 1: {
                if ((b != BiomesWorld.OCEAN) && (
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) ||
                        plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) ||
                        plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) ||
                        plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE))) {
                  int y1 =
                      rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER2)
                          + WorldTypeClassic.ROCKLAYER2;
                  BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

                  int plantCount = (RNG.nextInt(3) + 1);
                  for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
                      i < (floraDensity + floraDiversity) * creepingVinesUndergroundCount; i++) {
                    BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0,
                        rng.nextInt(16) + 8);
                    //TFCFlorae.getLog().warn("TFCFlorae: CreepingVines " + plant + " attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                    if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 5
                        && blockPos.getY() > WorldTypeClassic.ROCKLAYER2) {
                      undergroundCreepingVines.generate(world, rng, blockPos);
                    }
                  }
                  break;
                }
              }
              default:
                break;
            }
          }
          case CREEPING: {
            if ((b != BiomesWorld.OCEAN) && (
                plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.TACKWEED) ||
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.TAKAKIA) ||
                    plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.IVY) ||
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MORNING_GLORY) ||
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MOSS) ||
                    plant == TFCRegistries.PLANTS.getValue(DefaultPlants.REINDEER_LICHEN))) {
              int y1 = rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER3)
                  + WorldTypeClassic.ROCKLAYER3;
              BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

              int plantCount = (RNG.nextInt(3) + 1);
              for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
                  i < (floraDensity + floraDiversity) * creepingUndergroundCount; i++) {
                BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
                //TFCFlorae.getLog().warn("TFCFlorae: Moss " + plant + " attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 5
                    && blockPos.getY() > WorldTypeClassic.ROCKLAYER2) {
                  undergroundMoss.generate(world, rng, blockPos);
                }
              }
              break;
            }
          }
          default:
            break;
        }
      }
    }
  }
}
