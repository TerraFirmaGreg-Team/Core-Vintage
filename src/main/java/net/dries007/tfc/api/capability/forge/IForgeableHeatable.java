package net.dries007.tfc.api.capability.forge;

import su.terrafirmagreg.api.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.api.capabilities.heat.ICapabilityHeat;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.capabilities.heat.spi.Heat;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

/**
 * This is an advanced IForgeable capability that also needs heat If you implement this capability, you MUST implement {@link CapabilityHeat} as well You should return the same
 * instance from the getCapability calls
 */
public interface IForgeableHeatable extends IForgeable, ICapabilityHeat {

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
    default void addHeatInfo(@NotNull ItemStack stack, @NotNull List<String> text) {
        float temperature = getTemperature();
        String tooltip = Heat.getTooltip(temperature);
        if (tooltip != null) {
            tooltip += TextFormatting.WHITE;
            if (temperature > getMeltTemp()) {
                tooltip += " - " + I18n.format(MODID_TFC + ".tooltip.liquid");
            } else if (temperature > getWeldTemp()) {
                tooltip += " - " + I18n.format(MODID_TFC + ".tooltip.weldable");
            } else if (temperature > getWorkTemp()) {
                tooltip += " - " + I18n.format(MODID_TFC + ".tooltip.workable");
            }

            if (temperature > 0.9 * getMeltTemp()) {
                tooltip += " (" + I18n.format(MODID_TFC + ".tooltip.danger") + ")";
            }
            text.add(tooltip);
        }
    }
}
