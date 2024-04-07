package com.lumintorious.ambiental.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.lumintorious.ambiental.api.IItemTemperatureOwner;
import com.lumintorious.ambiental.api.IItemTemperatureProvider;
import com.lumintorious.ambiental.api.TemperatureRegistry;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;

public class ItemModifier extends BaseModifier {

    public ItemModifier(String name) {
        super(name);
    }

    public ItemModifier(String name, float change, float potency) {
        super(name, change, potency);
    }

    public static void computeModifiers(EntityPlayer player, ModifierStorage modifiers) {
        for (ItemStack stack : player.inventoryContainer.inventoryItemStacks) {
            if (stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null)) {
                IItemHeat heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
                float temp = heat.getTemperature() / 500;
                float change = temp;
                float potency = 0f;
                modifiers.add(new ItemModifier("heat_item", change, potency * stack.getCount()));
            }
            if (stack.getItem() instanceof IItemTemperatureOwner) {
                IItemTemperatureOwner owner = (IItemTemperatureOwner) stack.getItem();
                modifiers.add(owner.getModifier(stack, player));
            }
            for (IItemTemperatureProvider provider : TemperatureRegistry.ITEMS) {
                modifiers.add(provider.getModifier(stack, player));
            }
        }
    }

}
