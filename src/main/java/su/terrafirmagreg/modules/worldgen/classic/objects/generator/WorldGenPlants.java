package su.terrafirmagreg.modules.worldgen.classic.objects.generator;

import su.terrafirmagreg.api.library.types.category.Category;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;
import su.terrafirmagreg.modules.flora.init.BlocksFlora;
import su.terrafirmagreg.modules.flora.object.block.BlockPlant;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantEpiphyte;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantShortGrass;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantTall;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantTallGrass;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantTallGrassWater;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantTallWater;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantWater;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.EPIPHYTE;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.HANGING;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.SHORT_GRASS;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_GRASS;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_PLANT;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_WATER;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_WATER_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.WATER;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.WATER_SEA;
import static su.terrafirmagreg.modules.flora.api.types.type.FloraTypes.BEARDED_MOSS;
import static su.terrafirmagreg.modules.flora.api.types.type.FloraTypes.GLOW_VINE;
import static su.terrafirmagreg.modules.flora.api.types.type.FloraTypes.HANGING_VINE;
import static su.terrafirmagreg.modules.flora.api.types.type.FloraTypes.JUNGLE_VINE;
import static su.terrafirmagreg.modules.flora.api.types.type.FloraTypes.LIANA;
import static su.terrafirmagreg.modules.flora.api.types.type.FloraTypes.SAWGRASS;

public class WorldGenPlants extends WorldGenerator {

  private FloraType type;

  public void setGeneratedPlant(FloraType plantIn) {
    this.type = plantIn;
  }

  public boolean generate(World worldIn, Random rand, BlockPos position) {
    if (Category.isCategory(type.getCategory(), HANGING)) {
      if (Type.isType(type, BEARDED_MOSS, GLOW_VINE, HANGING_VINE, JUNGLE_VINE, LIANA)) {
        var plantBlock = (BlockPlantWater) BlocksFlora.PLANT.get(type);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(type.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.down(k))) &&
                worldIn.isAirBlock(blockpos.down(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.down(k), state) &&
                plantBlock.canPlaceBlockAt(worldIn, blockpos.down(k))) {
              int plantAge = type.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.down(k), state.withProperty(AGE_4, plantAge));
            }
          }
        }
        return true;
      }

      var plantBlock = BlocksFlora.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      int depth = type.getValidWaterDepth(worldIn, position, water);
      if (depth == -1) {
        return false;
      }

      BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), -depth + 1,
                                       rand.nextInt(7) - rand.nextInt(7));

      if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
          type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
          plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
        int plantAge = type.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos,
                                    state.withProperty(AGE_4, plantAge));
      }
    } else if (type.getCategory().equals(WATER) || type.getCategory().equals(WATER_SEA)) {
      var plantBlock = (BlockPlantWater) BlocksFlora.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      int depth = type.getValidWaterDepth(worldIn, position, water);
      if (depth == -1) {
        return false;
      }

      BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), -depth + 1,
                                       rand.nextInt(7) - rand.nextInt(7));

      if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
          type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
          plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
        int plantAge = type.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos,
                                    state.withProperty(AGE_4, plantAge));
      }
    } else if (type.getCategory().equals(TALL_WATER) || type.getCategory().equals(TALL_WATER_SEA)) {
      var plantBlock = (BlockPlantTallWater) BlocksFlora.PLANT.get(type);
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
        if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos.up(k))) {
          int plantAge = type.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                      state.withProperty(AGE_4, plantAge));
          if (rand.nextInt(4) < plantAge && plantBlock.canGrow(worldIn, blockpos, state,
                                                               worldIn.isRemote)) {
            setBlockAndNotifyAdequately(worldIn, blockpos.up(k), state);
          }
        }
      }
    } else if (type.getCategory().equals(SHORT_GRASS)) {
      var plantBlock = (BlockPlantShortGrass) BlocksFlora.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidGrowthTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            plantBlock.canBlockStay(worldIn, blockpos, state)) {
          int plantAge = type.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (type.getCategory().equals(TALL_GRASS)) {
      if (type != SAWGRASS) {
        var plantBlock = (BlockPlantTallGrass) BlocksFlora.PLANT.get(type);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(type.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = type.getAgeForWorldgen(rand,
                                                    Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(AGE_4, plantAge));
            }
          }
        }
      }
      if (type == SAWGRASS) {
        var plantBlock = (BlockPlantTallGrassWater) BlocksFlora.PLANT.get(type);
        IBlockState state = plantBlock.getDefaultState();
        IBlockState water = type.getWaterType();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0,
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(type.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canPlaceBlockAt(worldIn, blockpos.up(k)) &&
                type.isValidFloatingWaterDepth(worldIn, blockpos.up(k), water) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = type.getAgeForWorldgen(rand,
                                                    Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(AGE_4, plantAge));
            }
          }
        }
      }
    } else if (type.getCategory().equals(TALL_PLANT)) {
      var plantBlock = (BlockPlantTall) BlocksFlora.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        int j = 1 + rand.nextInt(type.getMaxHeight());

        for (int k = 0; k < j; ++k) {
          if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
              worldIn.isAirBlock(blockpos.up(k)) &&
              plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
            int plantAge = type.getAgeForWorldgen(rand,
                                                  Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                        state.withProperty(AGE_4, plantAge));
          }
        }
      }
    } else if (type.getCategory().equals(EPIPHYTE)) {
      var plantBlock = (BlockPlantEpiphyte) BlocksFlora.PLANT.get(type);

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
          int plantAge = type.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      plantBlock.getStateForWorldGen(worldIn, blockpos)
                                                .withProperty(AGE_4, plantAge));
        }
      }
    } else {
      var plantBlock = (BlockPlant) BlocksFlora.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
        BlockPos blockpos = position.add(
          rand.nextInt(7) - rand.nextInt(7),
          rand.nextInt(4) - rand.nextInt(4),
          rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            plantBlock.canBlockStay(worldIn, blockpos, state)) {
          int plantAge = type.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    }
    return true;
  }
}
