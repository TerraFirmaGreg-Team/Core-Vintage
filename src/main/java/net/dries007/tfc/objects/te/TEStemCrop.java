package net.dries007.tfc.objects.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class TEStemCrop extends TECropBase {

  private EnumFacing fruitDirection = EnumFacing.Plane.HORIZONTAL.random(RNG);

  @Override
  public void readFromNBT(@NotNull NBTTagCompound tag) {
    fruitDirection = EnumFacing.byIndex(tag.getInteger("fruitDirection"));
    super.readFromNBT(tag);
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(@NotNull NBTTagCompound tag) {
    tag.setInteger("fruitDirection", fruitDirection.getIndex());
    return super.writeToNBT(tag);
  }

  public EnumFacing getFruitDirection() {
    return fruitDirection;
  }
}
