package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityProviderForge;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemDiamondGrit extends ItemTFC {

  public ItemDiamondGrit() {
    this.setRegistryName("diamond_grit");
    this.setTranslationKey("diamond_grit");
    this.setCreativeTab(CreativeTabsTFC.CT_MISC);
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.VERY_SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.VERY_LIGHT;
  }

  @Nullable
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new CapabilityProviderForge(nbt);
  }
}
