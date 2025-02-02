package net.dries007.tfcflorae.objects.items.ceramics;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.objects.items.ceramics.ItemPottery;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemUnfiredUrn extends ItemPottery {

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack stack) {
    return Size.VERY_LARGE; // Don't fit in chests
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack stack) {
    return Weight.VERY_HEAVY; // Stack size = 1
  }
}
