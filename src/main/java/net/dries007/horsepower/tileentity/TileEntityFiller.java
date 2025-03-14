package net.dries007.horsepower.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;

import net.dries007.horsepower.blocks.BlockFiller;
import net.dries007.horsepower.blocks.BlockHPBase;

public class TileEntityFiller extends TileEntity {

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
    if (!(state.getBlock() instanceof BlockFiller)) {return getPos();}
    EnumFacing facing = state.getValue(BlockDirectional.FACING);
    IBlockState state1 = getWorld().getBlockState(pos.offset(facing));
    if (!(state1.getBlock() instanceof BlockHPBase)) {return getPos();}
    return pos.offset(facing);
  }

  @Override
  public void markDirty() {
    TileEntityHPBase te = getFilledTileEntity();
    if (te != null) {te.markDirty();}
    super.markDirty();
  }

  @Override
  public ITextComponent getDisplayName() {
    TileEntityHPBase te = getFilledTileEntity();
    if (te != null) {return te.getDisplayName();}
    return null;
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    TileEntityHPBase te = getFilledTileEntity();
    if (te != null) {return te.hasCapability(capability, facing);}
    return super.hasCapability(capability, facing);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
    TileEntityHPBase te = getFilledTileEntity();
    if (te != null) {return te.getCapability(capability, facing);}
    return super.getCapability(capability, facing);
  }
}
