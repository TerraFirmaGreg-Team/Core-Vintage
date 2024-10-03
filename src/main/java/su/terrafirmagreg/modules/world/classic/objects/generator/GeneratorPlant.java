package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

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

import static su.terrafirmagreg.data.Properties.IntProp.AGE_4;
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
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_REED;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_REED_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER_SEA;

public class GeneratorPlant extends WorldGenerator {

  private PlantType plant;

  public void setGeneratedPlant(PlantType plantIn) {
    this.plant = plantIn;
  }

  public boolean generate(World worldIn, Random rand, BlockPos position) {
    if (plant.isClayMarking()) {
      return false;
    }
    if (plant.isSwampPlant() &&
        (/*!ClimateTFC.isSwamp(worldIn, position) ||*/ !BiomeDictionary.hasType(worldIn.getBiome(position), BiomeDictionary.Type.SWAMP))) {
      return false;
    }

    if (plant.getCategory().equals(MUSHROOM)) {
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
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(SHORT_GRASS)) {
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
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(TALL_GRASS)) {
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
                                        state.withProperty(AGE_4, plantAge));
          }
        }
      }
    } else if (plant.getCategory().equals(CREEPING)) {
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
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(HANGING)) {
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
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(REED) || plant.getCategory().equals(REED_SEA)) {
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
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(TALL_REED) || plant.getCategory().equals(TALL_REED_SEA)) {
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
                                        state.withProperty(AGE_4, plantAge));
          }
        }
      }
    } else if (plant.getCategory().equals(DESERT)) {
      BlockPlant plantBlock = BlockPlant.get(plant);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            !BiomeDictionary.hasType(worldIn.getBiome(blockpos), Type.BEACH) &&
            plantBlock.canBlockStay(worldIn, blockpos, state)) {
          int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(DESERT_TALL_PLANT)) {
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
                                        state.withProperty(AGE_4, plantAge));
          }
        }
      }
    } else if (plant.getCategory().equals(DRY)) {
      BlockPlant plantBlock = BlockPlant.get(plant);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (plant.isValidTemp(Climate.getActualTemp(worldIn, blockpos)) &&
            plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            !BiomeDictionary.hasType(worldIn.getBiome(blockpos), Type.BEACH) &&
            plantBlock.canBlockStay(worldIn, blockpos, state)) {
          int plantAge = plant.getAgeForWorldgen(rand, Climate.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(DRY_TALL_PLANT)) {
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
                                        state.withProperty(AGE_4, plantAge));
          }
        }
      }
    } else if (plant.getCategory().equals(TALL_PLANT)) {
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
                                        state.withProperty(AGE_4, plantAge));
          }
        }
      }
    } else if (plant.getCategory().equals(WATER) || plant.getCategory().equals(WATER_SEA)) {
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
                                    state.withProperty(AGE_4, plantAge));
      }
    } else if (plant.getCategory().equals(EMERGENT_TALL_WATER) || plant.getCategory().equals(EMERGENT_TALL_WATER_SEA)) {
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
                                    state.withProperty(AGE_4, plantAge));
        if (rand.nextInt(3) < plantAge && plantBlock.canGrow(worldIn, blockpos, state,
                                                             worldIn.isRemote)) {
          setBlockAndNotifyAdequately(worldIn, blockpos.up(), state);
        }
      }
    } else if (plant.getCategory().equals(TALL_WATER) || plant.getCategory().equals(TALL_WATER_SEA)) {
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
                                    state.withProperty(AGE_4, plantAge));
        if (rand.nextInt(4) < plantAge && plantBlock.canGrow(worldIn, blockpos, state,
                                                             worldIn.isRemote)) {
          setBlockAndNotifyAdequately(worldIn, blockpos.up(), state);
        }
      }
    } else if (plant.getCategory().equals(FLOATING)) {
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
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(FLOATING_SEA)) {
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
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (plant.getCategory().equals(CACTUS)) {
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
                                        state.withProperty(AGE_4, plantAge));
          }
        }
      }
    } else if (plant.getCategory().equals(EPIPHYTE)) {
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
                                                .withProperty(AGE_4, plantAge));
        }
      }
    } else {
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
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    }
    return true;
  }
}
