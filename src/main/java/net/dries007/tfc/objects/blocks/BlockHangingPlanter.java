package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.modules.device.object.block.BlockGreenhouseWall;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static su.terrafirmagreg.data.Properties.DirectionProp.HORIZONTAL;
import static su.terrafirmagreg.data.Properties.EnumProp.XZ;
import static su.terrafirmagreg.data.Properties.IntProp.STAGE_3;

@MethodsReturnNonnullByDefault

public class BlockHangingPlanter extends BlockBonsai {

  private static final AxisAlignedBB SHAPE = new AxisAlignedBB(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);

  public BlockHangingPlanter(Supplier<? extends Item> fruit, Supplier<? extends Item> seed, int period) {
    super(fruit, seed, period, 0, Material.IRON);
    setDefaultState(blockState
                      .getBaseState()
                      .withProperty(XZ, EnumFacing.Axis.X)
                      .withProperty(STAGE_3, 0));
  }

  @Override
  @NotNull
  public IBlockState getStateFromMeta(int meta) {
    EnumFacing.Axis axis = EnumFacing.Axis.Z;
    if (meta > 2) {
      axis = EnumFacing.Axis.X;
      meta -= 3;
    }
    return getDefaultState().withProperty(XZ, axis).withProperty(STAGE_3, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    EnumFacing.Axis axis = state.getValue(XZ); // 0, 3
    int stage = state.getValue(STAGE_3); // 0, 1, 2
    return stage + (axis == EnumFacing.Axis.X ? 3 : 0);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return SHAPE;
  }

  @Override
  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, XZ, STAGE_3);
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!world.isRemote) {
      for (EnumFacing d : EnumFacing.Plane.HORIZONTAL) {
        Block block = world.getBlockState(pos.offset(d)).getBlock();
        if (block instanceof BlockGreenhouseWall || block instanceof BlockHangingPlanter) {
          return;
        }
      }
      world.destroyBlock(pos, true);
    }
  }

  @Override
  @SuppressWarnings("deprecation")
  @NotNull
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
                                          EntityLivingBase placer) {
    if (facing.getAxis() == EnumFacing.Axis.Y) {
      facing = placer.getHorizontalFacing();
    }
    IBlockState state = worldIn.getBlockState(pos.offset(facing));
    final Block block = state.getBlock();
    if (block instanceof BlockGreenhouseWall) {
      facing = state.getValue(HORIZONTAL);
    } else if (block instanceof BlockHangingPlanter) {
      return getDefaultState().withProperty(XZ, state.getValue(XZ));
    }
    return getDefaultState().withProperty(XZ, facing.getAxis());
  }
}
