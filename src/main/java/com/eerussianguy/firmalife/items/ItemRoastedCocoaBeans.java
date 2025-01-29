package com.eerussianguy.firmalife.items;

import net.minecraft.item.ItemStack;

import com.eerussianguy.firmalife.registry.ItemsFL;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.items.ItemMisc;

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
