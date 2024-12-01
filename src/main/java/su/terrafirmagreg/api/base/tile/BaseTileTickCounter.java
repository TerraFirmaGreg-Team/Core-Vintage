package su.terrafirmagreg.api.base.tile;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.NotNull;

public class BaseTileTickCounter extends BaseTile {

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
