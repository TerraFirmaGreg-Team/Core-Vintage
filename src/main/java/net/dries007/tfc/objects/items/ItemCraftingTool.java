package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemCraftingTool extends ItemMisc {

  public ItemCraftingTool(int durability, Size size, Weight weight, Object... oreNameParts) {
    super(size, weight, oreNameParts);
    setMaxDamage(durability);
    setMaxStackSize(1);
    setNoRepair();
  }

  @Override
  public boolean canStack(@Nonnull ItemStack stack) {
    return false;
  }
}
