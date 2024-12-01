package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.base.tile.BaseTileTickCounter;

import net.minecraft.nbt.NBTTagCompound;

import net.dries007.eerussianguy.firmalife.util.GreenhouseHelpers;

import org.jetbrains.annotations.NotNull;

public class TEHangingPlanter extends BaseTileTickCounter implements GreenhouseHelpers.IGreenhouseReceiver {

  private boolean isClimateValid;
  private int tier;

  public TEHangingPlanter() {
    super();
    isClimateValid = false;
    tier = 0;
  }

  @Override
  public void setValidity(boolean approvalStatus, int tierIn) {
    isClimateValid = approvalStatus;
    tier = tierIn;
    markForSync();
  }

  public boolean isClimateValid() {
    return isClimateValid;
  }

  public boolean isClimateValid(int tierMinimum) {
    return isClimateValid && tier >= tierMinimum;
  }

  @Override
  public void readFromNBT(@NotNull NBTTagCompound nbt) {
    isClimateValid = nbt.getBoolean("isClimateValid");
    tier = nbt.getInteger("tier");
    super.readFromNBT(nbt);
  }

  @Override
  public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
    nbt.setBoolean("isClimateValid", isClimateValid);
    nbt.setInteger("tier", tier);
    return super.writeToNBT(nbt);
  }
}
