package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.Fluid;

import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.plants.property.ITallPlant;
import net.dries007.tfc.util.climate.Climate;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.data.Properties.EnumProp.PLANT_PART;
import static su.terrafirmagreg.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.data.Properties.IntProp.DAYPERIOD;
import static su.terrafirmagreg.modules.world.classic.ChunkGenClassic.SALT_WATER;

public class BlockTallWaterPlantTFCF extends BlockWaterPlantTFCF implements IGrowable, ITallPlant {

  private static final Map<Plant, BlockTallWaterPlantTFCF> MAP = new HashMap<>();

  public BlockTallWaterPlantTFCF(Fluid fluid, Plant plant) {
    super(fluid, plant);
    this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }

    plant.getOreDictName().ifPresent(name -> OreDictionaryHelper.register(this, name));
  }

  public static BlockTallWaterPlantTFCF get(Plant plant) {
    return BlockTallWaterPlantTFCF.MAP.get(plant);
  }

  @Override
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    super.onBlockHarvested(worldIn, pos, state, player);
  }

  @Override
  public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
                                 net.minecraftforge.common.IPlantable plantable) {
    IBlockState plant = plantable.getPlant(world, pos.offset(direction));

    if (plant.getBlock() == this) {
      return true;
    }
    return super.canSustainPlant(state, world, pos, direction, plantable);
  }

  @Override
  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer.Builder(this)
      .add(LEVEL)
      .add(FLUID_RENDER_PROPS.toArray(new IUnlistedProperty<?>[0]))
      .add(growthStageProperty)
      .add(DAYPERIOD)
      .add(AGE_4)
      .add(PLANT_PART)
      .build();
  }

  @Override
  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return super.getActualState(state, worldIn, pos).withProperty(PLANT_PART, getPlantPart(worldIn, pos));
  }

  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return getTallBoundingBax(state.getValue(AGE_4), state, source, pos);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.XZ;
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());

    if (worldIn.getBlockState(pos.down(plant.getMaxHeight())).getBlock() == this) {
      return false;
    }
    if (worldIn.isAirBlock(pos.up())) {
      return false;
    }
    if (state.getBlock() == this) {
      return (soil.getBlock()
                  .canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) || BlockUtils.isGround(soil)) &&
             plant.isValidTemp(Climate.getActualTemp(worldIn, pos)) && plant.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    }
    return this.canSustainBush(soil);
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }

    if (plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
        plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) &&
          net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (j == 3 && canGrow(worldIn, pos, state, worldIn.isRemote)) {
          grow(worldIn, rand, pos, state);
        } else if (j < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j + 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) ||
               !plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (j == 0 && canShrink(worldIn, pos)) {
          shrink(worldIn, pos);
        } else if (j > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j - 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);
  }

  @Override
  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    IBlockState water = plant.getWaterType();
    int i;
    //noinspection StatementWithEmptyBody
    for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i)
      ;
    if (water == SALT_WATER) {
      return i < plant.getMaxHeight() && BlockUtils.isSaltWater(worldIn.getBlockState(pos.up())) && canBlockStay(worldIn, pos.up(), state) &&
             !worldIn.isAirBlock(pos.up());
    } else {
      return i < plant.getMaxHeight() && BlockUtils.isFreshWater(worldIn.getBlockState(pos.up())) && canBlockStay(worldIn, pos.up(), state) &&
             !worldIn.isAirBlock(pos.up());
    }
  }

  @Override
  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    return false;
  }

  @Override
  public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    worldIn.setBlockState(pos.up(), this.getDefaultState());
    IBlockState iblockstate = state.withProperty(AGE_4, 0)
                                   .withProperty(growthStageProperty, plant.getStageForMonth())
                                   .withProperty(PLANT_PART, getPlantPart(worldIn, pos));
    worldIn.setBlockState(pos, iblockstate);
    iblockstate.neighborChanged(worldIn, pos.up(), this, pos);
  }

  private boolean canShrink(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up())
                                                                          .getBlock() != this;
  }

  public void shrink(World worldIn, BlockPos pos) {
    worldIn.setBlockState(pos, plant.getWaterType());
    worldIn.getBlockState(pos).neighborChanged(worldIn, pos.down(), this, pos);
  }
}
