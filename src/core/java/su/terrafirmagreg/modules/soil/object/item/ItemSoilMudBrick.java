package su.terrafirmagreg.modules.soil.object.item;

import su.terrafirmagreg.modules.core.feature.heat.capability.CapabilityProviderHeat;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

public class ItemSoilMudBrick extends ItemSoil {

  public ItemSoilMudBrick(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("mud_brick"));

  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    // Heat capability, as pottery needs to be able to be fired, or survive despite not having a heat capability
    return new CapabilityProviderHeat(nbt, 1.0f, 1599f);
  }
}
