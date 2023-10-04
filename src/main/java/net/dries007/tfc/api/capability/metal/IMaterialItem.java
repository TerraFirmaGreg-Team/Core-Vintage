package net.dries007.tfc.api.capability.metal;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.module.core.api.recipes.heat.HeatRecipeMetalMelting;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/*
 * Must be on Item or Block (with ItemBlock, i.e. do not implement on blocks that have a separate item block)
 * Also, has a capability interface
 */
public interface IMaterialItem {
    /**
     * @param stack the item stack. This can assume that it is of the right item type and do casts without checking
     * @return the metal of the stack
     */
    @Nullable
    Material getMaterial(ItemStack stack);

    /**
     * @param stack The item stack
     * @return the amount of liquid metal that this item will create (in TFC units or mB: 1 unit = 1 mB)
     */
    int getSmeltAmount(ItemStack stack);

    /**
     * Can the metal melt directly from the stack into a fluid?
     * This is used by {@link HeatRecipeMetalMelting} to determine if metal melting is possible
     *
     * @param stack The item stack
     * @return true if the metal can be melted
     */
    default boolean canMelt(ItemStack stack) {
        return true;
    }

    default float getMeltTemp(ItemStack stack) {
        return 0f;
    }

    /**
     * Adds metal info to the item stack
     * This is only shown when advanced item tooltips is enabled
     *
     * @param stack The item stack
     * @param text  The text to be added
     */
    @SideOnly(Side.CLIENT)
    default void addMetalInfo(ItemStack stack, List<String> text) {
        var material = getMaterial(stack);
        if (material != null) {

            var property = material.getProperty(TFGPropertyKey.HEAT);
            if (property == null) throw new RuntimeException(String.format("No heat property for %s", material));

            var melttemp = (int) property.getMeltTemp();
            var metalTier = property.getTier();

            text.add("");
            text.add(I18n.format("tfc.tooltip.containsmetal"));
            text.add(I18n.format("tfc.tooltip.metalname", material.getLocalizedName()));
            text.add(I18n.format("tfc.tooltip.units", getSmeltAmount(stack) * stack.getCount()));
            text.add(I18n.format("tfc.tooltip.melttemp", melttemp));
            text.add(I18n.format("tfc.tooltip.tier", metalTier));
        }
    }
}
