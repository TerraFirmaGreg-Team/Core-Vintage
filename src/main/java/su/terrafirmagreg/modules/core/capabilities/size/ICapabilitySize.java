package su.terrafirmagreg.modules.core.capabilities.size;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.api.base.object.item.spi.BaseItemBlock;
import su.terrafirmagreg.api.data.Unicode;
import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Interface for item size. To implement this, you can (preferred) implement this interface on your Item / Block and return the size or Expose this capability via Item#initCapabilities() Note: if you implement this via an interface, you
 * must also change the stack-size of the item to agree with {@link ICapabilitySize#getStackSize} If you implement the capability, TFC will try and auto-adjust the max stacksize of the item for you Otherwise, your item will be assigned a
 * default capability on creation
 *
 * @see BaseItem
 * @see BaseItemBlock
 */
public interface ICapabilitySize {

  @SideOnly(Side.CLIENT)
  default void addTooltipInfo(ItemStack stack, List<String> text) {
    text.add(
      Unicode.WEIGHT + " " + I18n.format(TranslatorUtils.getEnumName(getWeight(stack))) + " " +
      Unicode.SIZE + " " + I18n.format(TranslatorUtils.getEnumName(getSize(stack))) + " " +
      Unicode.STACK_SIZE + " " + getStackSize(stack)
    );
  }

  default Weight getWeight(ItemStack stack) {
    return Weight.LIGHT;
  }

  default Size getSize(ItemStack stack) {
    return Size.SMALL;
  }

  /**
   * Should be called from {@link Item#getItemStackLimit(ItemStack)}
   */
  default int getStackSize(ItemStack stack) {
    return canStack(stack) ? getWeight(stack).getStackSize() : 1;
  }

  default boolean canStack(ItemStack stack) {
    return true;
  }
}
