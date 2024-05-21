package su.terrafirmagreg.api.features.ambiental.modifiers;

import su.terrafirmagreg.api.features.ambiental.AmbientalRegistry;
import su.terrafirmagreg.api.features.ambiental.provider.ITemperatureItemProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;

public class ModifierItem extends ModifierBase {

    public ModifierItem(String name) {
        super(name);
    }

    public ModifierItem(String name, float change, float potency) {
        super(name, change, potency);
    }

    public static void computeModifiers(EntityPlayer player, ModifierStorage modifiers) {
        for (ItemStack stack : player.inventoryContainer.inventoryItemStacks) {
            if (stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null)) {
                IItemHeat heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
                float temp = heat.getTemperature() / 500;
                float change = temp;
                float potency = 0f;
                modifiers.add(new ModifierItem("heat_item", change, potency * stack.getCount()));
            }
            if (stack.getItem() instanceof ITemperatureItemProvider provider) {
                modifiers.add(provider.getModifier(player, stack));
            }
            for (ITemperatureItemProvider provider : AmbientalRegistry.ITEMS) {
                modifiers.add(provider.getModifier(player, stack));
            }
        }
    }

}
