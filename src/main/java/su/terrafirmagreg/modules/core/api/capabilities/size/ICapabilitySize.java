package su.terrafirmagreg.modules.core.api.capabilities.size;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.util.Helpers;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Interface for item size. To implement this, you can (preferred) implement this interface on your Item / Block and return the size or Expose this capability via
 * Item#initCapabilities() Note: if you implement this via an interface, you must also change the stack-size of the item to agree with {@link ICapabilitySize#getStackSize} If you
 * implement the capability, TFC will try and auto-adjust the max stacksize of the item for you Otherwise, your item will be assigned a default capability on creation
 *
 * @see net.dries007.tfc.objects.items.ItemTFC
 * @see net.dries007.tfc.objects.items.itemblock.ItemBlockTFC
 */
public interface ICapabilitySize extends ICapabilityProvider {

    @NotNull
    default Size getSize(@NotNull ItemStack stack) {
        return Size.SMALL;
    }

    @NotNull
    default Weight getWeight(@NotNull ItemStack stack) {
        return Weight.LIGHT;
    }

    default boolean canStack(@NotNull ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    default void addSizeInfo(@NotNull ItemStack stack, @NotNull List<String> text) {
        text.add("\u2696 " + I18n.format(Helpers.getEnumName(getWeight(stack))) + " \u21F2 " + I18n.format(Helpers.getEnumName(getSize(stack))));
    }

    /**
     * Should be called from {@link net.minecraft.item.Item#getItemStackLimit(ItemStack)}
     */
    default int getStackSize(@NotNull ItemStack stack) {
        return canStack(stack) ? getWeight(stack).getStackSize() : 1;
    }
}
