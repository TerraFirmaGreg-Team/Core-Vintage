package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.api.library.types.category.Category;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.plant.object.block.BlockPlant;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantEpiphyte;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantShortGrass;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTall;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTallGrass;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTallGrassWater;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTallWater;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantWater;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.util.climate.ClimateTFC;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.EPIPHYTE;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.HANGING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.SHORT_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER_SEA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BEARDED_MOSS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GLOW_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HANGING_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.JUNGLE_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LIANA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SAWGRASS;

public class WorldGenPlants extends WorldGenerator {

  private PlantType type;

  public void setGeneratedPlant(PlantType plantIn) {
    this.type = plantIn;
  }

  public boolean generate(World worldIn, Random rand, BlockPos position) {
    if (Category.isCategory(type.getCategory(), HANGING)) {
      if (Type.isType(type, BEARDED_MOSS, GLOW_VINE, HANGING_VINE, JUNGLE_VINE, LIANA)) {
        var plantBlock = (BlockPlantWater) BlocksPlant.PLANT.get(type);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(type.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.down(k))) &&
                worldIn.isAirBlock(blockpos.down(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.down(k), state) &&
                plantBlock.canPlaceBlockAt(worldIn, blockpos.down(k))) {
              int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.down(k), state.withProperty(AGE_4, plantAge));
            }
          }
        }
        return true;
      }

      var plantBlock = BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      int depth = type.getValidWaterDepth(worldIn, position, water);
      if (depth == -1) {
        return false;
      }

      BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), -depth + 1,
                                       rand.nextInt(7) - rand.nextInt(7));

      if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
          type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
          plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
        int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos,
                                    state.withProperty(AGE_4, plantAge));
      }
    } else if (type.getCategory().equals(WATER) || type.getCategory().equals(WATER_SEA)) {
      var plantBlock = (BlockPlantWater) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      int depth = type.getValidWaterDepth(worldIn, position, water);
      if (depth == -1) {
        return false;
      }

      BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), -depth + 1,
                                       rand.nextInt(7) - rand.nextInt(7));

      if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
          type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
          plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
        int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos,
                                    state.withProperty(AGE_4, plantAge));
      }
    } else if (type.getCategory().equals(TALL_WATER) || type.getCategory().equals(TALL_WATER_SEA)) {
      var plantBlock = (BlockPlantTallWater) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      int depth = type.getValidWaterDepth(worldIn, position, water);
      if (depth == -1) {
        return false;
      }
      BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), -depth + 1,
                                       rand.nextInt(7) - rand.nextInt(7));

      int j = 1 + rand.nextInt(type.getMaxHeight());

      for (int k = 0; k < j; ++k) {
        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos.up(k))) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                      state.withProperty(AGE_4, plantAge));
          if (rand.nextInt(4) < plantAge && plantBlock.canGrow(worldIn, blockpos, state,
                                                               worldIn.isRemote)) {
            setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state);
          }
        }
      }
    } else if (type.getCategory().equals(SHORT_GRASS)) {
      var plantBlock = (BlockPlantShortGrass) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            plantBlock.canBlockStay(worldIn, blockpos, state)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (type.getCategory().equals(TALL_GRASS)) {
      if (type != SAWGRASS) {
        var plantBlock = (BlockPlantTallGrass) BlocksPlant.PLANT.get(type);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(type.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = type.getAgeForWorldgen(rand,
                                                    ClimateTFC.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(AGE_4, plantAge));
            }
          }
        }
      }
      if (type == SAWGRASS) {
        var plantBlock = (BlockPlantTallGrassWater) BlocksPlant.PLANT.get(type);
        IBlockState state = plantBlock.getDefaultState();
        IBlockState water = type.getWaterType();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0,
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(type.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
                type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canPlaceBlockAt(worldIn, blockpos.up(k)) &&
                type.isValidFloatingWaterDepth(worldIn, blockpos.up(k), water) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = type.getAgeForWorldgen(rand,
                                                    ClimateTFC.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(AGE_4, plantAge));
            }
          }
        }
      }
    } else if (type.getCategory().equals(TALL_PLANT)) {
      var plantBlock = (BlockPlantTall) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        int j = 1 + rand.nextInt(type.getMaxHeight());

        for (int k = 0; k < j; ++k) {
          if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
              type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
              worldIn.isAirBlock(blockpos.up(k)) &&
              plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
            int plantAge = type.getAgeForWorldgen(rand,
                                                  ClimateTFC.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                        state.withProperty(AGE_4, plantAge));
          }
        }
      }
    } else if (type.getCategory().equals(EPIPHYTE)) {
      var plantBlock = (BlockPlantEpiphyte) BlocksPlant.PLANT.get(type);

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      plantBlock.getStateForWorldGen(worldIn, blockpos)
                                                .withProperty(AGE_4, plantAge));
        }
      }
    } else {
      var plantBlock = (BlockPlant) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
        BlockPos blockpos = position.add(
          rand.nextInt(7) - rand.nextInt(7),
          rand.nextInt(4) - rand.nextInt(4),
          rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            plantBlock.canBlockStay(worldIn, blockpos, state)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    }
    return true;
  }
}
