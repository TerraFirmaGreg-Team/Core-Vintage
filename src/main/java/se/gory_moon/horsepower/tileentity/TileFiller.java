package se.gory_moon.horsepower.tileentity;

import su.terrafirmagreg.api.base.tile.BaseTile;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;

import se.gory_moon.horsepower.blocks.BlockChopperHorse;
import se.gory_moon.horsepower.blocks.BlockFiller;
import se.gory_moon.horsepower.blocks.BlockPress;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.DIRECTIONAL;

public class TileFiller extends BaseTile {

  @Override
  public void markDirty() {
    var tile = TileUtils.getTile(world, getFilledPos(), TileHPBase.class);
    tile.ifPresent(TileHPBase::markDirty);
    super.markDirty();
  }


  public BlockPos getFilledPos() {
    IBlockState state = world.getBlockState(pos);
    if (!(state.getBlock() instanceof BlockFiller)) {
      return getPos();
    }
    EnumFacing facing = state.getValue(DIRECTIONAL);
    IBlockState state1 = world.getBlockState(pos.offset(facing));
    if (!(state1.getBlock() instanceof BlockChopperHorse)) {
      return getPos();
    }

    if (!(state1.getBlock() instanceof BlockPress)) {
      return getPos();
    }
    return pos.offset(facing);
  }

  @Override
  public ITextComponent getDisplayName() {
    var tile = TileUtils.getTile(world, getFilledPos(), TileHPBase.class);
    return tile.map(TileEntity::getDisplayName).orElse(super.getDisplayName());
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    var tile = TileUtils.getTile(world, getFilledPos(), TileHPBase.class);
    return tile.map(tileHPBase -> tileHPBase.hasCapability(capability, facing)).orElse(super.hasCapability(capability, facing));
  }

  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    return TileUtils.getTile(world, getFilledPos(), TileHPBase.class)
                    .map(tile -> tile.getCapability(capability, facing))
                    .orElse(super.getCapability(capability, facing));
  }
}
