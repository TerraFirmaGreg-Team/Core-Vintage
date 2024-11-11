package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.api.library.types.category.Category;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.plant.object.block.BlockPlant;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantCactus;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantCreeping;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantEmergentTallWater;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantEpiphyte;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantFloatingWater;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantHanging;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantMushroom;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantShortGrass;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTall;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTallGrass;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantTallWater;
import su.terrafirmagreg.modules.plant.object.block.BlockPlantWater;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import net.dries007.tfc.util.climate.ClimateTFC;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;
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
import static su.terrafirmagreg.modules.rock.init.BlocksRock.SAND;

public class GeneratorPlant extends WorldGenerator {

  private PlantType type;

  public void setGeneratedPlant(PlantType plantIn) {
    this.type = plantIn;
  }

  public boolean generate(World worldIn, Random rand, BlockPos position) {
    if (type.isClayMarking()) {
      return false;
    }
    if (type.isSwampPlant() &&
        (/*!ClimateTFC.isSwamp(worldIn, position) ||*/ !BiomeDictionary.hasType(worldIn.getBiome(position), BiomeDictionary.Type.SWAMP))) {
      return false;
    }

    if (Category.isCategory(type.getCategory(), MUSHROOM)) {
      var plantBlock = (BlockPlantMushroom) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(4) - rand.nextInt(4));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (Category.isCategory(type.getCategory(), SHORT_GRASS)) {
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
    } else if (Category.isCategory(type.getCategory(), TALL_GRASS)) {
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
    } else if (Category.isCategory(type.getCategory(), CREEPING)) {
      var plantBlock = (BlockPlantCreeping) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(4) - rand.nextInt(4));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            plantBlock.canBlockStay(worldIn, blockpos, state) &&
            !Variant.isVariant(worldIn.getBlockState(blockpos.down()), SAND)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (Category.isCategory(type.getCategory(), HANGING)) {
      var plantBlock = (BlockPlantHanging) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 4; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), rand.nextInt(16),
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
    } else if (Category.isCategory(type.getCategory(), REED, REED_SEA)) {
      BlockPlant plantBlock = (BlockPlant) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 16; ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            worldIn.getBlockState(blockpos.down())
                   .getBlock()
                   .canSustainPlant(state, worldIn, blockpos.down(), EnumFacing.UP, plantBlock)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (Category.isCategory(type.getCategory(), TALL_REED, TALL_REED_SEA)) {
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
    } else if (Category.isCategory(type.getCategory(), DESERT)) {
      var plantBlock = (BlockPlant) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            !BiomeDictionary.hasType(worldIn.getBiome(blockpos), Type.BEACH) &&
            plantBlock.canBlockStay(worldIn, blockpos, state)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (Category.isCategory(type.getCategory(), DESERT_TALL_PLANT)) {
      var plantBlock = (BlockPlantTall) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
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
    } else if (Category.isCategory(type.getCategory(), DRY)) {
      var plantBlock = (BlockPlant) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
        BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7),
                                         rand.nextInt(4) - rand.nextInt(4),
                                         rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            !BiomeDictionary.hasType(worldIn.getBiome(blockpos), Type.BEACH) &&
            plantBlock.canBlockStay(worldIn, blockpos, state)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (Category.isCategory(type.getCategory(), DRY_TALL_PLANT)) {
      var plantBlock = (BlockPlantTall) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position); ++i) {
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
    } else if (Category.isCategory(type.getCategory(), TALL_PLANT)) {
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
    } else if (Category.isCategory(type.getCategory(), WATER, WATER_SEA)) {
      var plantBlock = (BlockPlantWater) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      int depth = type.getValidWaterDepth(worldIn, position, water);
      if (depth == -1) {
        return false;
      }

      BlockPos blockpos = position.add(0, -depth + 1, 0);

      if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
          type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
          plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
        int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos,
                                    state.withProperty(AGE_4, plantAge));
      }
    } else if (Category.isCategory(type.getCategory(), EMERGENT_TALL_WATER, EMERGENT_TALL_WATER_SEA)) {
      var plantBlock = (BlockPlantEmergentTallWater) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      int depth = type.getValidWaterDepth(worldIn, position, water);
      if (depth == -1) {
        return false;
      }
      BlockPos blockpos = position.add(0, -depth + 1, 0);

      if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
          type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
          plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
        int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos,
                                    state.withProperty(AGE_4, plantAge));
        if (rand.nextInt(3) < plantAge && plantBlock.canGrow(worldIn, blockpos, state,
                                                             worldIn.isRemote)) {
          setBlockAndNotifyAdequately(worldIn, blockpos.up(), state);
        }
      }
    } else if (Category.isCategory(type.getCategory(), TALL_WATER, TALL_WATER_SEA)) {
      var plantBlock = (BlockPlantTallWater) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      int depth = type.getValidWaterDepth(worldIn, position, water);
      if (depth == -1) {
        return false;
      }
      BlockPos blockpos = position.add(0, -depth + 1, 0);

      if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
          type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
          plantBlock.canPlaceBlockAt(worldIn, blockpos)) {
        int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
        setBlockAndNotifyAdequately(worldIn, blockpos,
                                    state.withProperty(AGE_4, plantAge));
        if (rand.nextInt(4) < plantAge && plantBlock.canGrow(worldIn, blockpos, state,
                                                             worldIn.isRemote)) {
          setBlockAndNotifyAdequately(worldIn, blockpos.up(), state);
        }
      }
    } else if (Category.isCategory(type.getCategory(), FLOATING)) {
      var plantBlock = (BlockPlantFloatingWater) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      for (int i = 0; i < 8; ++i) {
        final BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0,
                                               rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos) &&
            type.isValidFloatingWaterDepth(worldIn, blockpos, water)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (Category.isCategory(type.getCategory(), FLOATING_SEA)) {
      var plantBlock = (BlockPlantFloatingWater) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();
      IBlockState water = type.getWaterType();

      for (int i = 0; i < 128; ++i) {
        final BlockPos blockpos = position.add(rand.nextInt(7) - rand.nextInt(7), 0,
                                               rand.nextInt(7) - rand.nextInt(7));

        if (type.isValidTemp(ClimateTFC.getActualTemp(worldIn, blockpos)) &&
            type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, blockpos)) &&
            worldIn.isAirBlock(blockpos) &&
            plantBlock.canPlaceBlockAt(worldIn, blockpos) &&
            type.isValidFloatingWaterDepth(worldIn, blockpos, water)) {
          int plantAge = type.getAgeForWorldgen(rand, ClimateTFC.getActualTemp(worldIn, blockpos));
          setBlockAndNotifyAdequately(worldIn, blockpos,
                                      state.withProperty(AGE_4, plantAge));
        }
      }
    } else if (Category.isCategory(type.getCategory(), CACTUS)) {
      var plantBlock = (BlockPlantCactus) BlocksPlant.PLANT.get(type);
      IBlockState state = plantBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, position) / 8; ++i) {
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
    } else if (Category.isCategory(type.getCategory(), EPIPHYTE)) {
      BlockPlantEpiphyte plantBlock = (BlockPlantEpiphyte) BlocksPlant.PLANT.get(type);

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
