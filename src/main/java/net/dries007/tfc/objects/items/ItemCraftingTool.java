package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.feature.size.capability.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.capability.spi.Weight;

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
