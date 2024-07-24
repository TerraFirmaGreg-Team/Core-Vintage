package su.terrafirmagreg.api.capabilities.size;

import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.lib.Unicode;
import su.terrafirmagreg.api.util.GameUtils;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import java.util.List;

/**
 * Interface for item size. To implement this, you can (preferred) implement this interface on your Item / Block and return the size or Expose this capability via
 * Item#initCapabilities() Note: if you implement this via an interface, you must also change the stack-size of the item to agree with {@link ICapabilitySize#getStackSize} If you
 * implement the capability, TFC will try and auto-adjust the max stacksize of the item for you Otherwise, your item will be assigned a default capability on creation
 *
 * @see net.dries007.tfc.objects.items.ItemTFC
 * @see net.dries007.tfc.objects.items.itemblock.ItemBlockTFC
 */
public interface ICapabilitySize {

    default Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    default Weight getWeight(ItemStack stack) {
        return Weight.LIGHT;
    }

    default boolean canStack(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    default void addSizeInfo(ItemStack stack, List<String> text) {
        text.add(Unicode.WEIGHT + " " + I18n.format(GameUtils.getEnumName(getWeight(stack))) + " " + Unicode.SIZE + " " + I18n.format(GameUtils.getEnumName(getSize(stack))));
    }

    /**
     * Should be called from {@link net.minecraft.item.Item#getItemStackLimit(ItemStack)}
     */
    default int getStackSize(ItemStack stack) {
        return canStack(stack) ? getWeight(stack).getStackSize() : 1;
    }
}
