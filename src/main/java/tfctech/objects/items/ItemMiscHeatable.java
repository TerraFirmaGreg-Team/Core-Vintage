package tfctech.objects.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class ItemMiscHeatable extends ItemMiscTech {

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
  public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new CapabilityProviderHeat(nbt, heatCapacity, meltTemp);
  }
}
