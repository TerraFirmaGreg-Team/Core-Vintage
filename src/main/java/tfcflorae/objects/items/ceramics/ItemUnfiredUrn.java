package tfcflorae.objects.items.ceramics;

import net.minecraft.item.ItemStack;

import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import net.dries007.tfc.objects.items.ceramics.ItemPottery;

import org.jetbrains.annotations.NotNull;

public class ItemUnfiredUrn extends ItemPottery {

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return Size.VERY_LARGE; // Don't fit in chests
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.VERY_HEAVY; // Stack size = 1
    }
}
