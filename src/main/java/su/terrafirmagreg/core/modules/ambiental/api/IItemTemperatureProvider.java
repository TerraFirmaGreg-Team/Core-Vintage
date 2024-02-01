package su.terrafirmagreg.core.modules.ambiental.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import su.terrafirmagreg.core.modules.ambiental.modifier.TempModifier;
import su.terrafirmagreg.core.modules.ambiental.modifier.TempModifierStorage;

import java.util.Optional;

@FunctionalInterface
public interface IItemTemperatureProvider {

    static void evaluateAll(EntityPlayer player, TempModifierStorage modifiers) {
        for (ItemStack stack : player.inventoryContainer.inventoryItemStacks) {
            for (IItemTemperatureProvider provider : AmbientalRegistry.ITEMS) {
                modifiers.add(provider.getModifier(player, stack));
            }
        }
    }

    static Optional<TempModifier> handleTemperatureCapability(EntityPlayer player, ItemStack stack) {
        if (stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null)) {
            IItemHeat heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
            float temp = heat.getTemperature() / 800;
            float change = temp;
            float potency = 0f;
            return TempModifier.defined("heat_item", change, potency * stack.getCount());
        } else {
            return TempModifier.none();
        }
    }

    Optional<TempModifier> getModifier(EntityPlayer player, ItemStack stack);
}
