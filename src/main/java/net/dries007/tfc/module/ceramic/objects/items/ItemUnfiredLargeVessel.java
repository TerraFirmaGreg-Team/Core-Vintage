package net.dries007.tfc.module.ceramic.objects.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemUnfiredLargeVessel extends ItemPottery {
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
