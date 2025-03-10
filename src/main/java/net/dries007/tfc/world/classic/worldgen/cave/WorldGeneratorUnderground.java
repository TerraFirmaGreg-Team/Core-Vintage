package net.dries007.tfc.world.classic.worldgen.cave;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.Constants;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.PlantsTFCF;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfcflorae.ConfigTFCF;

import java.util.Random;

public class WorldGeneratorUnderground implements IWorldGenerator {

  private final WorldGenCaveMushrooms undergroundMushrooms;
  private final WorldGenCaveMoss undergroundMoss;
  private final WorldGenCaveVines undergroundVines;
  private final WorldGenCaveCreepingVines undergroundCreepingVines;
  //private final WorldGenUnderground undergroundPlant = new WorldGenUnderground();

  private final float fungiUndergroundCount = ConfigTFCF.General.WORLD.fungiUndergroundCount;
  private final float hangingVinesUndergroundCount = ConfigTFCF.General.WORLD.hangingVinesUndergroundCount;
  private final float creepingVinesUndergroundCount = ConfigTFCF.General.WORLD.creepingVinesUndergroundCount;
  private final float creepingUndergroundCount = ConfigTFCF.General.WORLD.creepingUndergroundCount;

  public WorldGeneratorUnderground() {
    undergroundMushrooms = new WorldGenCaveMushrooms();
    undergroundCreepingVines = new WorldGenCaveCreepingVines();
    undergroundVines = new WorldGenCaveVines();
    undergroundMoss = new WorldGenCaveMoss();
  }

  // Somewhat works
  @Override
  public void generate(Random rng, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (!(chunkGenerator instanceof ChunkGenTFC)) {return;}

    int y = rng.nextInt(WorldTypeTFC.SEALEVEL);
    BlockPos chunkPos = new BlockPos(chunkX << 4, y, chunkZ << 4);

    ChunkDataTFC data = ChunkDataTFC.get(world, chunkPos);
    Biome b = world.getBiome(chunkPos);
    final float avgTemperature = Climate.getAvgTemp(world, chunkPos);
    final float rainfall = ChunkDataTFC.getRainfall(world, chunkPos);
    final float floraDensity = data.getFloraDensity();
    final float floraDiversity = data.getFloraDiversity();

    for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
      if (plant.isValidTempForWorldGen(avgTemperature) && plant.isValidRain(rainfall)) {
        undergroundVines.setGeneratedPlant(plant);
        undergroundCreepingVines.setGeneratedPlant(plant);
        undergroundMoss.setGeneratedPlant(plant);

        switch (plant.getPlantType()) {
          case MUSHROOM: {
            if (avgTemperature >= -13f && avgTemperature <= 50f && rainfall >= 250f && rainfall <= 500) {
              int plantCount = (Constants.RNG.nextInt(3) + 1);
              for (int i = rng.nextInt(Math.round(plantCount / floraDiversity)); i < (floraDensity + floraDiversity) * fungiUndergroundCount; i++) {
                BlockPos blockPos = chunkPos.add(rng.nextInt(16) + 8, rng.nextInt(16) + 8, rng.nextInt(16) + 8);
                //TFCFlorae.getLog().warn("TFCFlorae: Mushroom " + plant + " attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                if (blockPos.getY() < WorldTypeTFC.SEALEVEL - 15 && blockPos.getY() > 20) {
                  undergroundMushrooms.generate(world, rng, blockPos);
                }
              }
              break;
            }
          }
          case HANGING: {
            switch (Constants.RNG.nextInt(2)) {
              case 0: {
                if ((b != BiomesTFC.OCEAN || b != BiomesTFC.OCEAN) && (
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) ||
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) ||
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) ||
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE) ||
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.LIANA))) {
                  int y1 = rng.nextInt((WorldTypeTFC.SEALEVEL - 5) - WorldTypeTFC.ROCKLAYER2) + WorldTypeTFC.ROCKLAYER2;
                  BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

                  int plantCount = (Constants.RNG.nextInt(3) + 1);
                  for (int i = rng.nextInt(Math.round(plantCount / floraDiversity)); i < (floraDensity + floraDiversity) * hangingVinesUndergroundCount; i++) {
                    BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
                    //TFCFlorae.getLog().warn("TFCFlorae: Vines " + plant + " attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                    if (blockPos.getY() < WorldTypeTFC.SEALEVEL - 5 && blockPos.getY() > WorldTypeTFC.ROCKLAYER2) {
                      undergroundVines.generate(world, rng, blockPos);
                    }
                  }
                  break;
                }
              }
              case 1: {
                if ((b != BiomesTFC.OCEAN || b != BiomesTFC.OCEAN) && (
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.BEARDED_MOSS) ||
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.GLOW_VINE) ||
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.HANGING_VINE) ||
                  plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.JUNGLE_VINE))) {
                  int y1 = rng.nextInt((WorldTypeTFC.SEALEVEL - 5) - WorldTypeTFC.ROCKLAYER2) + WorldTypeTFC.ROCKLAYER2;
                  BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

                  int plantCount = (Constants.RNG.nextInt(3) + 1);
                  for (int i = rng.nextInt(Math.round(plantCount / floraDiversity)); i < (floraDensity + floraDiversity) * creepingVinesUndergroundCount; i++) {
                    BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
                    //TFCFlorae.getLog().warn("TFCFlorae: CreepingVines " + plant + " attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                    if (blockPos.getY() < WorldTypeTFC.SEALEVEL - 5 && blockPos.getY() > WorldTypeTFC.ROCKLAYER2) {
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
            if ((b != BiomesTFC.OCEAN || b != BiomesTFC.OCEAN) && (
              plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.TACKWEED) ||
              plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.TAKAKIA) ||
              plant == TFCRegistries.PLANTS.getValue(PlantsTFCF.IVY) ||
              plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MORNING_GLORY) ||
              plant == TFCRegistries.PLANTS.getValue(DefaultPlants.MOSS) ||
              plant == TFCRegistries.PLANTS.getValue(DefaultPlants.REINDEER_LICHEN))) {
              int y1 = rng.nextInt((WorldTypeTFC.SEALEVEL - 5) - WorldTypeTFC.ROCKLAYER3) + WorldTypeTFC.ROCKLAYER3;
              BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

              int plantCount = (Constants.RNG.nextInt(3) + 1);
              for (int i = rng.nextInt(Math.round(plantCount / floraDiversity)); i < (floraDensity + floraDiversity) * creepingUndergroundCount; i++) {
                BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
                //TFCFlorae.getLog().warn("TFCFlorae: Moss " + plant + " attempted to generate at " + "X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ());
                if (blockPos.getY() < WorldTypeTFC.SEALEVEL - 5 && blockPos.getY() > WorldTypeTFC.ROCKLAYER2) {
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
