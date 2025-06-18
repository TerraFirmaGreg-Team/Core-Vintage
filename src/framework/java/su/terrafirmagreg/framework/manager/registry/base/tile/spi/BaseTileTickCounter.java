package su.terrafirmagreg.framework.manager.registry.base.tile.spi;


import su.terrafirmagreg.modules.core.feature.calendar.spi.Calendar;

import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.NotNull;

public abstract class BaseTileTickCounter extends BaseTile {

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
