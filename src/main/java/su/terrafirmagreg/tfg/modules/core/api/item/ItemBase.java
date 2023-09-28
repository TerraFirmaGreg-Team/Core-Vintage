package su.terrafirmagreg.tfg.modules.core.api.item;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.IItemSize;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.Size;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.Weight;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static su.terrafirmagreg.tfg.modules.core.api.capability.size.Size.TINY;
import static su.terrafirmagreg.tfg.modules.core.api.capability.size.Weight.VERY_LIGHT;

@ParametersAreNonnullByDefault
public abstract class ItemBase extends Item implements IItemSize {
    /**
     * This should NOT be overridden except for VERY SPECIAL cases
     * If an item needs to not stack, i.e. small vessels, override {@link IItemSize#canStack(ItemStack)}
     * If an item needs a variable stack size, override {@link IItemSize#getWeight(ItemStack)} / {@link IItemSize#getSize(ItemStack)} and return a different value to get a different stack size
     */
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }


    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return TINY;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return VERY_LIGHT;
    }
}
