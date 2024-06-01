package su.terrafirmagreg.api.capabilities.damage;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HandlerDamageResistance {

    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ARMOR = new HashMap<>(); //Used inside CT, set custom IDamageResistance for armor items outside TFC

    public static void init() {

    }
}
