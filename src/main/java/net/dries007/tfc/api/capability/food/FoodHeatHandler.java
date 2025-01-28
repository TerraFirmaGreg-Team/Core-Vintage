package net.dries007.tfc.api.capability.food;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import net.dries007.tfc.util.agriculture.Food;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * This is a combined capability class that delegates to two implementations: - super = ItemHeatHandler - internalFoodCap = FoodHandler
 */
public class FoodHeatHandler extends CapabilityProviderHeat implements IFood, ICapabilitySerializable<NBTTagCompound> {

  private final FoodHandler internalFoodCap;

  public FoodHeatHandler() {
    this(null, new FoodData(), 1, 100);
  }

  public FoodHeatHandler(@Nullable NBTTagCompound nbt, @Nonnull Food food) {
    this(nbt, food.getData(), food.getHeatCapacity(), food.getCookingTemp());
  }

  public FoodHeatHandler(@Nullable NBTTagCompound nbt, FoodData data, float heatCapacity, float meltTemp) {
    this.heatCapacity = heatCapacity;
    this.meltTemp = meltTemp;

    this.internalFoodCap = new FoodHandler(nbt, data);

    deserializeNBT(nbt);
  }

  @Override
  public long getCreationDate() {
    return internalFoodCap.getCreationDate();
  }

  @Override
  public void setCreationDate(long creationDate) {
    internalFoodCap.setCreationDate(creationDate);
  }

  @Override
  public long getRottenDate() {
    return internalFoodCap.getRottenDate();
  }

  @Nonnull
  @Override
  public FoodData getData() {
    return internalFoodCap.getData();
  }

  @Override
  public float getDecayDateModifier() {
    return internalFoodCap.getDecayDateModifier();
  }

  @Override
  public void setNonDecaying() {
    internalFoodCap.setNonDecaying();
  }

  @Nonnull
  @Override
  public List<FoodTrait> getTraits() {
    return internalFoodCap.getTraits();
  }

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityFood.CAPABILITY || capability == CapabilityHeat.CAPABILITY;
  }

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) this : null;
  }

  @Override
  @Nonnull
  public NBTTagCompound serializeNBT() {
    NBTTagCompound nbt = super.serializeNBT();
    nbt.setTag("food", internalFoodCap.serializeNBT());
    return nbt;
  }

  @Override
  public void deserializeNBT(@Nullable NBTTagCompound nbt) {
    if (nbt != null) {
      internalFoodCap.deserializeNBT(nbt.getCompoundTag("food"));
      super.deserializeNBT(nbt);
    }
  }
}
