package su.terrafirmagreg.modules.soil.object.item;

import su.terrafirmagreg.modules.core.capabilities.heat.ProviderHeat;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

public class ItemSoilMudBrick extends ItemSoil {

  public ItemSoilMudBrick(SoilItemVariant variant, SoilType type) {
    super(variant, type);

  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    // Heat capability, as pottery needs to be able to be fired, or survive despite not having a heat capability
    return new ProviderHeat(nbt, 1.0f, 1599f);
  }
}
