package su.terrafirmagreg.modules.core.capabilities.heat;

import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * It is recommended that if you extend {@link CapabilityProviderHeat} rather than implement this directly. If you do extend this, look at ItemHeatHandler to
 * observe how heat decays over time.
 */
public interface ICapabilityHeat extends ICapabilitySerializable<NBTTagCompound> {

  /**
   * Gets the Heat capacity. (A measure of how fast this items heats up or cools down) Implementation is left up to the heating object. (See TEFirePit for
   * example)
   *
   * @return the heat capacity. Typically 0 - 1, can be outside this range, must be non-negative
   */
  float getHeatCapacity();

  /**
   * If the object can melt / transform, return if it is transformed This can mean many different things depending on the object
   *
   * @return is the object transformed.
   */
  default boolean isMolten() {
    return getTemperature() > getMeltTemp();
  }

  /**
   * Gets the current temperature. Should call {@link CapabilityHeat#adjustTemp(float, float, long)} internally
   *
   * @return the temperature.
   */
  float getTemperature();

  /**
   * Sets the temperature. Used for anything that modifies the temperature.
   *
   * @param temperature the temperature to set.
   */
  void setTemperature(float temperature);

  /**
   * Gets the melting point of the item. Depending on the item, this may not mean anything.
   *
   * @return a temperature at which this item should melt at
   */
  float getMeltTemp();

  /**
   * Adds the heat info tooltip when hovering over. When overriding this to show additional information, fall back to IItemHeat.super.addHeatInfo()
   *
   * @param stack The stack to add information to
   * @param text  The list of tooltips
   */
  @SideOnly(Side.CLIENT)
  default void addHeatInfo(ItemStack stack, List<String> text) {
    float temperature = getTemperature();
    if (stack.getItem() == Items.STICK) {
      if (temperature > getMeltTemp() * 0.9f) {
        text.add(I18n.format("tfc.enum.heat.torch.lit"));
      } else if (temperature > 1f) {
        text.add(I18n.format("tfc.enum.heat.torch.catching_fire"));
      }
    } else {
      String tooltip = Heat.getTooltip(temperature);
      if (tooltip != null) {
        text.add(tooltip);
      }
    }
  }
}
