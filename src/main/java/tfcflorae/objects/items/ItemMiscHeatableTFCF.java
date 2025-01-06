package tfcflorae.objects.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import javax.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class ItemMiscHeatableTFCF extends ItemMiscTFCF {

  private final float heatCapacity;
  private final float meltTemp;

  public ItemMiscHeatableTFCF(Size size, Weight weight, float heatCapacity, float meltTemp) {
    super(size, weight);
    this.heatCapacity = heatCapacity;
    this.meltTemp = meltTemp;
  }

  public ItemMiscHeatableTFCF(Size size, Weight weight, float heatCapacity, float meltTemp, String oreDictionary) {
    super(size, weight, oreDictionary);
    this.heatCapacity = heatCapacity;
    this.meltTemp = meltTemp;
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new CapabilityProviderHeat(nbt, heatCapacity, meltTemp);
  }
}
