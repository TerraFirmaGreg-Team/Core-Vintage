package su.terrafirmagreg.modules.world.classic.objects.generator.cave;

import su.terrafirmagreg.api.library.types.category.Category;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;
import su.terrafirmagreg.modules.world.classic.init.BiomesWorld;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import su.terrafirmagreg.modules.core.feature.climate.Climate;
import net.dries007.tfcflorae.ConfigTFCF;

import java.util.Random;

import static su.terrafirmagreg.api.util.MathUtils.RNG;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.CREEPING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.HANGING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.MUSHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BEARDED_MOSS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GLOW_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HANGING_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.IVY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.JUNGLE_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LIANA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MORNING_GLORY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MOSS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.REINDEER_LICHEN;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TACKWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TAKAKIA;

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
  public void generate(Random rng, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (!(chunkGenerator instanceof ChunkGenClassic)) {
      return;
    }

    int y = rng.nextInt(WorldTypeClassic.SEALEVEL);
    BlockPos chunkPos = new BlockPos(chunkX << 4, y, chunkZ << 4);

    var data = CapabilityChunkData.get(world, chunkPos);
    Biome biome = world.getBiome(chunkPos);
    final float avgTemperature = Climate.getAvgTemp(world, chunkPos);
    final float rainfall = ProviderChunkData.getRainfall(world, chunkPos);
    final float floraDensity = data.getFloraDensity();
    final float floraDiversity = data.getFloraDiversity();

    for (PlantType plant : PlantType.getTypes()) {
      if (!plant.isValidTempForWorldGen(avgTemperature) && !plant.isValidRain(rainfall)) {return;}
      undergroundVines.setGeneratedPlant(plant);
      undergroundCreepingVines.setGeneratedPlant(plant);
      undergroundMoss.setGeneratedPlant(plant);

      if (Category.isCategory(plant.getCategory(), MUSHROOM)) {
        if (avgTemperature >= -13f && avgTemperature <= 50f && rainfall >= 250f && rainfall <= 500) {
          int plantCount = (RNG.nextInt(3) + 1);
          for (int i = rng.nextInt(Math.round(plantCount / floraDiversity)); i < (floraDensity + floraDiversity) * fungiUndergroundCount; i++) {
            BlockPos blockPos = chunkPos.add(rng.nextInt(16) + 8, rng.nextInt(16) + 8, rng.nextInt(16) + 8);
            if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 15 && blockPos.getY() > 20) {
              undergroundMushrooms.generate(world, rng, blockPos);
            }
          }
          continue;
        }
        switch (RNG.nextInt(2)) {
          case 0: {
            if ((biome != BiomesWorld.OCEAN) && (Type.isType(plant, BEARDED_MOSS, GLOW_VINE, HANGING_VINE, JUNGLE_VINE, LIANA))) {
              int y1 = rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER2) + WorldTypeClassic.ROCKLAYER2;
              BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

              int plantCount = (RNG.nextInt(3) + 1);
              for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
                   i < (floraDensity + floraDiversity) * hangingVinesUndergroundCount; i++) {
                BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
                if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 5
                    && blockPos.getY() > WorldTypeClassic.ROCKLAYER2) {
                  undergroundVines.generate(world, rng, blockPos);
                }
              }
              break;
            }
          }
          case 1: {
            if ((biome != BiomesWorld.OCEAN) && (Type.isType(plant, BEARDED_MOSS, GLOW_VINE, HANGING_VINE, JUNGLE_VINE))) {
              int y1 = rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER2) + WorldTypeClassic.ROCKLAYER2;
              BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

              int plantCount = (RNG.nextInt(3) + 1);
              for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
                   i < (floraDensity + floraDiversity) * creepingVinesUndergroundCount; i++) {
                BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0,
                                                      rng.nextInt(16) + 8);
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
        if ((biome != BiomesWorld.OCEAN) && (Type.isType(plant, TACKWEED, TAKAKIA, IVY, MORNING_GLORY, MOSS, REINDEER_LICHEN))) {
          int y1 = rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER3) + WorldTypeClassic.ROCKLAYER3;
          BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

          int plantCount = (RNG.nextInt(3) + 1);
          for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
               i < (floraDensity + floraDiversity) * creepingUndergroundCount; i++) {
            BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
            if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 5
                && blockPos.getY() > WorldTypeClassic.ROCKLAYER2) {
              undergroundMoss.generate(world, rng, blockPos);
            }
          }
        }
      } else if (Category.isCategory(plant.getCategory(), HANGING)) {
        switch (RNG.nextInt(2)) {
          case 0: {
            if ((biome != BiomesWorld.OCEAN) && (Type.isType(plant, BEARDED_MOSS, GLOW_VINE, HANGING_VINE, JUNGLE_VINE, LIANA))) {
              int y1 =
                rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER2) + WorldTypeClassic.ROCKLAYER2;
              BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

              int plantCount = (RNG.nextInt(3) + 1);
              for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
                   i < (floraDensity + floraDiversity) * hangingVinesUndergroundCount; i++) {
                BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
                if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 5
                    && blockPos.getY() > WorldTypeClassic.ROCKLAYER2) {
                  undergroundVines.generate(world, rng, blockPos);
                }
              }
              break;
            }
          }
          case 1: {
            if ((biome != BiomesWorld.OCEAN) && (Type.isType(BEARDED_MOSS, GLOW_VINE, HANGING_VINE, JUNGLE_VINE))) {
              int y1 =
                rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER2)
                + WorldTypeClassic.ROCKLAYER2;
              BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

              int plantCount = (RNG.nextInt(3) + 1);
              for (int i = rng.nextInt(Math.round(plantCount / floraDiversity));
                   i < (floraDensity + floraDiversity) * creepingVinesUndergroundCount; i++) {
                BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
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
        if ((biome != BiomesWorld.OCEAN) && (Type.isType(TACKWEED, TAKAKIA, IVY, MORNING_GLORY, MOSS, REINDEER_LICHEN))) {
          int y1 = rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER3) + WorldTypeClassic.ROCKLAYER3;
          BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

          int plantCount = (RNG.nextInt(3) + 1);
          for (int i = rng.nextInt(Math.round(plantCount / floraDiversity)); i < (floraDensity + floraDiversity) * creepingUndergroundCount; i++) {
            BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
            if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 5 && blockPos.getY() > WorldTypeClassic.ROCKLAYER2) {
              undergroundMoss.generate(world, rng, blockPos);
            }
          }
        }
      } else if (Category.isCategory(plant.getCategory(), CREEPING)) {
        if ((biome != BiomesWorld.OCEAN) && (Type.isType(plant, TACKWEED, TAKAKIA, IVY, MORNING_GLORY, MOSS, REINDEER_LICHEN))) {
          int y1 = rng.nextInt((WorldTypeClassic.SEALEVEL - 5) - WorldTypeClassic.ROCKLAYER3) + WorldTypeClassic.ROCKLAYER3;
          BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y1, chunkZ << 4);

          int plantCount = (RNG.nextInt(3) + 1);
          for (int i = rng.nextInt(Math.round(plantCount / floraDiversity)); i < (floraDensity + floraDiversity) * creepingUndergroundCount; i++) {
            BlockPos blockPos = chunkBlockPos.add(rng.nextInt(16) + 8, 0, rng.nextInt(16) + 8);
            if (blockPos.getY() < WorldTypeClassic.SEALEVEL - 5 && blockPos.getY() > WorldTypeClassic.ROCKLAYER2) {
              undergroundMoss.generate(world, rng, blockPos);
            }
          }
        }
      }
    }

  }
}
