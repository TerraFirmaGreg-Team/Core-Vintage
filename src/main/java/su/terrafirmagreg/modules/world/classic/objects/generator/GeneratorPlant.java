package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;

import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.plants.BlockPlant;
import net.dries007.tfc.objects.blocks.plants.BlockPlantCactus;
import net.dries007.tfc.objects.blocks.plants.BlockPlantCreeping;
import net.dries007.tfc.objects.blocks.plants.BlockPlantEmergentTallWater;
import net.dries007.tfc.objects.blocks.plants.BlockPlantEpiphyte;
import net.dries007.tfc.objects.blocks.plants.BlockPlantFloatingWater;
import net.dries007.tfc.objects.blocks.plants.BlockPlantHanging;
import net.dries007.tfc.objects.blocks.plants.BlockPlantMushroom;
import net.dries007.tfc.objects.blocks.plants.BlockPlantShortGrass;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTall;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTallGrass;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTallWater;
import net.dries007.tfc.objects.blocks.plants.BlockPlantWater;
import net.dries007.tfc.util.climate.Climate;

import java.util.Random;

public class GeneratorPlant extends WorldGenerator {

  private Plant plant;

  public void setGeneratedPlant(Plant plantIn) {
    this.plant = plantIn;
  }

  public boolean generate(World worldIn, Random rand, BlockPos position) {
    if (plant.getIsClayMarking()) {
      return false;
    }
    if (plant.getIsSwampPlant() &&
        (/*!ClimateTFC.isSwamp(worldIn, position) ||*/ !BiomeDictionary.hasType(
          worldIn.getBiome(position), BiomeDictionary.Type.SWAMP))) {
      return false;
    }

    switch (plant.getPlantType()) {
      case MUSHROOM: {
        BlockPlantMushroom plantBlock = BlockPlantMushroom.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(4) - rand.nextInt(4));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlant.AGE, plantAge));
          }
        }
        break;
      }
      case SHORT_GRASS: {
        BlockPlantShortGrass plantBlock = BlockPlantShortGrass.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              plantBlock.canBlockStay(worldIn, blockpos, state)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlantShortGrass.AGE, plantAge));
          }
        }
        break;
      }
      case TALL_GRASS: {
        BlockPlantTallGrass plantBlock = BlockPlantTallGrass.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(plant.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = plant.getAgeForWorldgen(rand,
                                                     Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(BlockPlantShortGrass.AGE, plantAge));
            }
          }
        }
        break;
      }
      case CREEPING: {
        BlockPlantCreeping plantBlock = BlockPlantCreeping.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(4) - rand.nextInt(4));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              plantBlock.canBlockStay(worldIn, blockpos, state) &&
              !BlockUtils.isSand(worldIn.getBlockState(blockpos.down()))) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlantCreeping.AGE, plantAge));
          }
        }
        break;
      }
      case HANGING: {
        BlockPlantHanging plantBlock = BlockPlantHanging.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16),
                                           rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              plantBlock.canBlockStay(worldIn, blockpos, state)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlantHanging.AGE, plantAge));
          }
        }
        break;
      }
      case REED:
      case REED_SEA: {
        BlockPlant plantBlock = BlockPlant.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              worldIn.getBlockState(blockpos.down())
                     .getBlock()
                     .canSustainPlant(state, worldIn, blockpos.down(), EnumFacing.UP, plantBlock)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlant.AGE, plantAge));
          }
        }
        break;
      }
      case TALL_REED:
      case TALL_REED_SEA: {
        BlockPlantTall plantBlock = BlockPlantTall.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(plant.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = plant.getAgeForWorldgen(rand,
                                                     Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(BlockPlantTall.AGE, plantAge));
            }
          }
        }
        break;
      }
      case DESERT: {
        BlockPlant plantBlock = BlockPlant.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              !BiomeDictionary.hasType(worldIn.getBiome(blockpos), BiomeDictionary.Type.BEACH) &&
              plantBlock.canBlockStay(worldIn, blockpos, state)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlant.AGE, plantAge));
          }
        }
        break;
      }
      case DESERT_TALL_PLANT: {
        BlockPlantTall plantBlock = BlockPlantTall.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(plant.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = plant.getAgeForWorldgen(rand,
                                                     Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(BlockPlantTall.AGE, plantAge));
            }
          }
        }
        break;
      }
      case DRY: {
        BlockPlant plantBlock = BlockPlant.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              !BiomeDictionary.hasType(worldIn.getBiome(blockpos), BiomeDictionary.Type.BEACH) &&
              plantBlock.canBlockStay(worldIn, blockpos, state)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlant.AGE, plantAge));
          }
        }
        break;
      }
      case DRY_TALL_PLANT: {
        BlockPlantTall plantBlock = BlockPlantTall.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(plant.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = plant.getAgeForWorldgen(rand,
                                                     Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(BlockPlantTall.AGE, plantAge));
            }
          }
        }
        break;
      }
      case TALL_PLANT: {
        BlockPlantTall plantBlock = BlockPlantTall.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(plant.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = plant.getAgeForWorldgen(rand,
                                                     Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(BlockPlantTall.AGE, plantAge));
            }
          }
        }
        break;
      }
      case WATER:
      case WATER_SEA: {
        BlockPlantWater plantBlock = BlockPlantWater.get(plant);
        IBlockState state = plantBlock.getDefaultState();
        IBlockState water = plant.getWaterType();

        int depth = plant.getValidWaterDepth(worldIn, position, water);
        if (depth == -1) {
          return false;
        }

        BlockPos blockpos = position.add(0, -depth + 1, 0);

        if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
          int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(BlockPlantWater.AGE, plantAge));
        }
        break;
      }
      case EMERGENT_TALL_WATER:
      case EMERGENT_TALL_WATER_SEA: {
        BlockPlantEmergentTallWater plantBlock = BlockPlantEmergentTallWater.get(plant);
        IBlockState state = plantBlock.getDefaultState();
        IBlockState water = plant.getWaterType();

        int depth = plant.getValidWaterDepth(worldIn, position, water);
        if (depth == -1) {
          return false;
        }
        BlockPos blockpos = position.add(0, -depth + 1, 0);

        if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
          int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(BlockPlantEmergentTallWater.AGE, plantAge));
          if (rand.nextInt(3) < plantAge && plantBlock.canGrow(worldIn, blockpos, state,
                                                               worldIn.isRemote)) {
            setBlockAndNotifyAdequately(worldIn, blockpos.up(), state);
          }
        }
        break;
      }
      case TALL_WATER:
      case TALL_WATER_SEA: {
        BlockPlantTallWater plantBlock = BlockPlantTallWater.get(plant);
        IBlockState state = plantBlock.getDefaultState();
        IBlockState water = plant.getWaterType();

        int depth = plant.getValidWaterDepth(worldIn, position, water);
        if (depth == -1) {
          return false;
        }
        BlockPos blockpos = position.add(0, -depth + 1, 0);

        if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
          int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(BlockPlantTall.AGE, plantAge));
          if (rand.nextInt(4) < plantAge && plantBlock.canGrow(worldIn, blockpos, state,
                                                               worldIn.isRemote)) {
            setBlockAndNotifyAdequately(worldIn, blockpos.up(), state);
          }
        }
        break;
      }
      case FLOATING: {
        BlockPlantFloatingWater plantBlock = BlockPlantFloatingWater.get(plant);
        IBlockState state = plantBlock.getDefaultState();
        IBlockState water = plant.getWaterType();

        for (int i = 0; i < 8; ++i) {
          final BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0,
                                                 rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              plantBlock.canPlaceBlockAt(worldIn, blockpos) &&
              plant.isValidFloatingWaterDepth(worldIn, blockpos, water)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlantFloatingWater.AGE, plantAge));
          }
        }
        break;
      }
      case FLOATING_SEA: {
        BlockPlantFloatingWater plantBlock = BlockPlantFloatingWater.get(plant);
        IBlockState state = plantBlock.getDefaultState();
        IBlockState water = plant.getWaterType();

        for (int i = 0; i < 128; ++i) {
          final BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0,
                                                 rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              plantBlock.canPlaceBlockAt(worldIn, blockpos) &&
              plant.isValidFloatingWaterDepth(worldIn, blockpos, water)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlantFloatingWater.AGE, plantAge));
          }
        }
        break;
      }
      case CACTUS: {
        BlockPlantCactus plantBlock = BlockPlantCactus.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 8; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                           rand.nextInt(4) - rand.nextInt(4),
                                           rand.nextInt(7) - rand.nextInt(7));

          int j = 1 + rand.nextInt(plant.getMaxHeight());

          for (int k = 0; k < j; ++k) {
            if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
                plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos.up(k))) &&
                worldIn.isAirBlock(blockpos.up(k)) &&
                plantBlock.canBlockStay(worldIn, blockpos.up(k), state)) {
              int plantAge = plant.getAgeForWorldgen(rand,
                                                     Climate.getActualTemp(worldIn, blockpos));
              setBlockAndNotifyAdequately(worldIn, blockpos.up(k),
                                          state.withProperty(BlockPlantCactus.AGE, plantAge));
            }
          }
        }
        break;
      }
      case EPIPHYTE: {
        BlockPlantEpiphyte plantBlock = BlockPlantEpiphyte.get(plant);

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
          BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16),
                                           rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos) &&
              plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        plantBlock.getStateForWorldGen(worldIn, blockpos)
                                                  .withProperty(BlockPlantEpiphyte.AGE, plantAge));
          }
        }
        break;
      }
      default: {
        BlockPlant plantBlock = BlockPlant.get(plant);
        IBlockState state = plantBlock.getDefaultState();

        for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
          BlockPos blockpos = position.add(
            rand.nextInt(7) - rand.nextInt(7),
            rand.nextInt(4) - rand.nextInt(4),
            rand.nextInt(7) - rand.nextInt(7));

          if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
              plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
              worldIn.isAirBlock(blockpos) &&
              plantBlock.canBlockStay(worldIn, blockpos, state)) {
            int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
            setBlockAndNotifyAdequately(worldIn, blockpos,
                                        state.withProperty(BlockPlant.AGE, plantAge));
          }
        }
      }
    }
    return true;
  }
}
