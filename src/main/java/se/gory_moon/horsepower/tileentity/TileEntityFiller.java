package se.gory_moon.horsepower.tileentity;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;


import se.gory_moon.horsepower.blocks.BlockFiller;
import se.gory_moon.horsepower.blocks.BlockHPBase;

import org.jetbrains.annotations.Nullable;

public class TileEntityFiller extends TileEntity {

  @Override
  public void markDirty() {
    TileEntityHPBase tile = getFilledTileEntity();
    if (tile != null) {
      tile.markDirty();
    }
    super.markDirty();
  }

  public TileEntityHPBase getFilledTileEntity() {
    BlockPos pos = getFilledPos();
    TileEntity tileEntity = getWorld().getTileEntity(pos);
    if (tileEntity instanceof TileEntityHPBase) {
      return (TileEntityHPBase) tileEntity;
    }
    return null;
  }

  public BlockPos getFilledPos() {
    IBlockState state = getWorld().getBlockState(getPos());
    if (!(state.getBlock() instanceof BlockFiller)) {
      return getPos();
    }
    EnumFacing facing = state.getValue(BlockDirectional.FACING);
    IBlockState state1 = getWorld().getBlockState(pos.offset(facing));
    if (!(state1.getBlock() instanceof BlockHPBase)) {
      return getPos();
    }
    return pos.offset(facing);
  }

  @Override
  public ITextComponent getDisplayName() {
    TileEntityHPBase tile = getFilledTileEntity();
    if (tile != null) {
      return tile.getDisplayName();
    }
    return null;
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    TileEntityHPBase tile = getFilledTileEntity();
    if (tile != null) {
      return tile.hasCapability(capability, facing);
    }
    return super.hasCapability(capability, facing);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
    TileEntityHPBase tile = getFilledTileEntity();
    if (tile != null) {
      return tile.getCapability(capability, facing);
    }
    return super.getCapability(capability, facing);
  }
}
