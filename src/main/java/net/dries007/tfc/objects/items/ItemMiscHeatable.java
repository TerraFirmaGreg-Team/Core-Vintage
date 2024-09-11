package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.heat.ProviderHeat;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("WeakerAccess")
public class ItemMiscHeatable extends ItemMisc {

  private final float heatCapacity;
  private final float meltTemp;

  public ItemMiscHeatable(Size size, Weight weight, float heatCapacity, float meltTemp) {
    super(size, weight);
    this.heatCapacity = heatCapacity;
    this.meltTemp = meltTemp;
  }

  public ItemMiscHeatable(Size size, Weight weight, float heatCapacity, float meltTemp, String oreDictionary) {
    super(size, weight, oreDictionary);
    this.heatCapacity = heatCapacity;
    this.meltTemp = meltTemp;
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ProviderHeat(nbt, heatCapacity, meltTemp);
  }
}
