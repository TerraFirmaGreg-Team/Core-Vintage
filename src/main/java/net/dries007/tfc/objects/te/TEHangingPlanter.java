package net.dries007.tfc.objects.te;

import net.minecraft.nbt.NBTTagCompound;

import net.dries007.firmalife.util.GreenhouseHelpers;

import javax.annotation.Nonnull;

public class TEHangingPlanter extends TETickCounter implements GreenhouseHelpers.IGreenhouseReceiver {

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
  public void readFromNBT(NBTTagCompound nbt) {
    isClimateValid = nbt.getBoolean("isClimateValid");
    tier = nbt.getInteger("tier");
    super.readFromNBT(nbt);
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setBoolean("isClimateValid", isClimateValid);
    nbt.setInteger("tier", tier);
    return super.writeToNBT(nbt);
  }
}
