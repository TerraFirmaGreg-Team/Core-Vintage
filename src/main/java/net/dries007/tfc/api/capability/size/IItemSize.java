package net.dries007.tfc.api.capability.size;

import net.dries007.tfc.module.api.common.block.itemblocks.ItemBlockBase;
import net.dries007.tfc.module.api.common.item.ItemBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Interface for item size.
 * To implement this, you can (preferred) implement this interface on your Item / Block and return the size or
 * Expose this capability via Item#initCapabilities()
 * Note: if you implement this via an interface, you must also change the stack-size of the item to agree with {@link IItemSize#getStackSize}
 * If you implement the capability, TFC will try and auto-adjust the max stacksize of the item for you
 * Otherwise, your item will be assigned a default capability on creation
 *
 * @see ItemBase
 * @see ItemBlockBase
 */
public interface IItemSize {
    @Nonnull
    Size getSize(@Nonnull ItemStack stack);

    @Nonnull
    Weight getWeight(@Nonnull ItemStack stack);

    default boolean canStack(@Nonnull ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    default void addSizeInfo(@Nonnull ItemStack stack, @Nonnull List<String> text) {
        text.add(1, "\u2696 " + getWeight(stack).getLocalizedName() + " \u21F2 " + getSize(stack).getLocalizedName());
    }

    /**
     * Should be called from {@link net.minecraft.item.Item#getItemStackLimit(ItemStack)}
     */
    default int getStackSize(@Nonnull ItemStack stack) {
        return canStack(stack) ? getWeight(stack).getStackSize() : 1;
    }
}
