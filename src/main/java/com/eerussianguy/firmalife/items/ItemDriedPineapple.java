package com.eerussianguy.firmalife.items;

import net.minecraft.item.ItemStack;

import com.eerussianguy.firmalife.init.FoodFL;
import com.eerussianguy.firmalife.registry.ItemsFL;
import mcp.MethodsReturnNonnullByDefault;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import net.dries007.tfc.objects.items.ItemMisc;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemDriedPineapple extends ItemMisc {

  public ItemDriedPineapple() {
    super(Size.SMALL, Weight.LIGHT);
  }

  @Override
  public ItemStack getContainerItem(ItemStack itemStack) {
    return new ItemStack(ItemsFL.getFood(FoodFL.PINEAPPLE_CHUNKS));
  }

  @Override
  public boolean hasContainerItem(ItemStack stack) {
    return true;
  }
}
