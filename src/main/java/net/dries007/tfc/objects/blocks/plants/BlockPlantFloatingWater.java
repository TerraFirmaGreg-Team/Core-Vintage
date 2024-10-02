package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.util.BlockUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.api.types.Plant;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.data.Properties.IntProp.DAYPERIOD;

public class BlockPlantFloatingWater extends BlockPlant {

  private static final AxisAlignedBB LILY_PAD_AABB = new AxisAlignedBB(0.0D, -0.125D, 0.0D, 1.0D, 0.0625D, 1.0D);
  private static final Map<Plant, BlockPlantFloatingWater> MAP = new HashMap<>();

  public BlockPlantFloatingWater(Plant plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
  }

  public static BlockPlantFloatingWater get(Plant plant) {
    return BlockPlantFloatingWater.MAP.get(plant);
  }

  @Override
  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, growthStageProperty, DAYPERIOD, AGE_4);
  }

  @Override
  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    world.setBlockState(pos, state.withProperty(DAYPERIOD, getDayPeriod())
                                  .withProperty(growthStageProperty, plant.getStageForMonth()));
    this.checkAndDropBlock(world, pos, state);
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    super.onEntityCollision(worldIn, pos, state, entityIn);

    if (entityIn instanceof EntityBoat) {
      worldIn.destroyBlock(new BlockPos(pos), true);
    }
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.NONE;
  }

  @Override
  protected boolean canSustainBush(IBlockState state) {
    return (BlockUtils.isWater(state) || state.getMaterial() == Material.ICE && state == plant.getWaterType()) ||
           (state.getMaterial() == Material.CORAL && !(state.getBlock() instanceof BlockPlantEmergentTallWater));
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    if (pos.getY() >= 0 && pos.getY() < 256) {
      IBlockState stateDown = worldIn.getBlockState(pos.down());
      Material material = stateDown.getMaterial();
      return (material == Material.WATER && stateDown.getValue(BlockLiquid.LEVEL) == 0 && stateDown == plant.getWaterType()) ||
             material == Material.ICE || (material == Material.CORAL && !(state.getBlock() instanceof BlockPlantEmergentTallWater));
    } else {
      return false;
    }
  }

  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return LILY_PAD_AABB.offset(state.getOffset(source, pos));
  }
}
