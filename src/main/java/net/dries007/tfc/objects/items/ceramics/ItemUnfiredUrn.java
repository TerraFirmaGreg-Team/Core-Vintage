package net.dries007.tfc.objects.items.ceramics;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;


import org.jetbrains.annotations.NotNull;

public class ItemUnfiredUrn extends ItemPottery {

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return Weight.VERY_HEAVY; // Stack size = 1
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return Size.VERY_LARGE; // Don't fit in chests
  }
}
