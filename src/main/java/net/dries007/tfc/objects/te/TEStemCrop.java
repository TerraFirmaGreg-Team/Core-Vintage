package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.util.MathUtils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;

public class TEStemCrop extends TECropBase {

  private EnumFacing fruitDirection = EnumFacing.Plane.HORIZONTAL.random(MathUtils.RNG);

  @Override
  @Nonnull
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    tag.setInteger("fruitDirection", fruitDirection.getIndex());
    return super.writeToNBT(tag);
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    fruitDirection = EnumFacing.byIndex(tag.getInteger("fruitDirection"));
    super.readFromNBT(tag);
  }

  public EnumFacing getFruitDirection() {
    return fruitDirection;
  }
}
