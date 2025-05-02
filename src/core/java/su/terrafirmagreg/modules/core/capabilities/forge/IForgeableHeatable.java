package su.terrafirmagreg.modules.core.capabilities.forge;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

/**
 * This is an advanced IForgeable capability that also needs heat If you implement this capability, you MUST implement {@link CapabilityHeat} as well You should
 * return the same instance from the getCapability calls
 */
public interface IForgeableHeatable extends ICapabilityForge, ICapabilityHeat {

  /**
   * Gets the working temperature of the item
   *
   * @return a temperature
   */
  default float getWorkTemp() {
    return getMeltTemp() * 0.6f;
  }

  /**
   * Checks if the item is hot enough to be worked
   *
   * @return true if the item is workable
   */
  @Override
  default boolean isWorkable() {
    return getTemperature() > getWorkTemp();
  }

  /**
   * Checks if the item is hot enough to be worked
   *
   * @return true if the item is weldable
   */
  @Override
  default boolean isWeldable() {
    return getTemperature() > getWeldTemp();
  }

  /**
   * Gets the welding temperature of the item
   *
   * @return a temperature
   */
  default float getWeldTemp() {
    return getMeltTemp() * 0.8f;
  }

  @SideOnly(Side.CLIENT)
  @Override
  default void addHeatInfo(@Nonnull ItemStack stack, @Nonnull List<String> text) {
    float temperature = getTemperature();
    String tooltip = Heat.getTooltip(temperature);
    if (tooltip != null) {
      tooltip += TextFormatting.WHITE;
      if (temperature > getMeltTemp()) {
        tooltip += " - " + I18n.format(TFC + ".tooltip.liquid");
      } else if (temperature > getWeldTemp()) {
        tooltip += " - " + I18n.format(TFC + ".tooltip.weldable");
      } else if (temperature > getWorkTemp()) {
        tooltip += " - " + I18n.format(TFC + ".tooltip.workable");
      }

      if (temperature > 0.9 * getMeltTemp()) {
        tooltip += " (" + I18n.format(TFC + ".tooltip.danger") + ")";
      }
      text.add(tooltip);
    }
  }
}
