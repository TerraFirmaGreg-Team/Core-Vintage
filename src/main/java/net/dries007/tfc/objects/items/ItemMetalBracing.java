package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemMetalBracing extends ItemTFC {

  public ItemMetalBracing() {
    this.setRegistryName("metal_bracing");
    this.setTranslationKey("metal_bracing");
    this.setCreativeTab(CreativeTabsTFC.CT_METAL);
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.LIGHT;
  }
}
