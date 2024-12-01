package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.base.tile.BaseTileTickCounter;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendarTickable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;

import org.jetbrains.annotations.NotNull;

public class TECropBase extends BaseTileTickCounter implements ICalendarTickable, ITickable {

  protected long lastTickCalChecked;

  public TECropBase() {
    lastTickCalChecked = Calendar.PLAYER_TIME.getTicks();
  }

  @Override
  public long getLastUpdateTick() {
    return lastTickCalChecked;
  }

  @Override
  public void setLastUpdateTick(long tick) {
    lastTickCalChecked = tick;
    markDirty();
  }

  @Override
  public void onCalendarUpdate(long playerTickDelta) {
    BlockCropTFC block = (BlockCropTFC) getBlockType();
    block.checkGrowth(world, pos, world.getBlockState(pos), world.rand);
  }

  @Override
  public void readFromNBT(@NotNull NBTTagCompound nbt) {
    lastTickCalChecked = nbt.getLong("lastTickCalChecked");
    super.readFromNBT(nbt);
  }

  @NotNull
  @Override
  public NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
    nbt.setLong("lastTickCalChecked", lastTickCalChecked);
    return super.writeToNBT(nbt);
  }

  @Override
  public void update() {
    ICalendarTickable.super.checkForCalendarUpdate();
  }
}
