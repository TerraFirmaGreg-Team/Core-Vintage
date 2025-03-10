package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.firmalife.init.FoodFL;
import net.dries007.firmalife.registry.ItemsFL;

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
