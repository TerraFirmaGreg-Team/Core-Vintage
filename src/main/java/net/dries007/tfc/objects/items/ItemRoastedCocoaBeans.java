package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;

import net.dries007.firmalife.registry.ItemsFL;

import javax.annotation.Nonnull;

public class ItemRoastedCocoaBeans extends ItemMisc {

  public ItemRoastedCocoaBeans() {
    super(Size.SMALL, Weight.LIGHT);
  }

  @Override
  @Nonnull
  public ItemStack getContainerItem(ItemStack itemStack) {
    return new ItemStack(ItemsFL.COCOA_POWDER);
  }

  @Override
  public boolean hasContainerItem(ItemStack stack) {
    return true;
  }
}
