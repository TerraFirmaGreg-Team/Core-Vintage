package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.base.tile.BaseTile;

import net.minecraft.nbt.NBTTagCompound;

import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;

public class TETickCounter extends BaseTile {

  private long lastUpdateTick;

  public long getTicksSinceUpdate() {
    return Calendar.PLAYER_TIME.getTicks() - lastUpdateTick;
  }

  public void resetCounter() {
    lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
    markForSync();
  }

  public void reduceCounter(long amount) {
    lastUpdateTick += amount;
    markForSync();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    lastUpdateTick = nbt.getLong("tick");
    super.readFromNBT(nbt);
  }


  @Override
  public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setLong("tick", lastUpdateTick);
    return super.writeToNBT(nbt);
  }
}
