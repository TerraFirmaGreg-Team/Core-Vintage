package su.terrafirmagreg.api.capabilities.egg;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HandlerEgg {

    //Used inside CT, set custom IItemHeat for items outside TFC
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();

    public static void init() {
        CUSTOM_ITEMS.put(IIngredient.of(Items.EGG), () -> new ProviderEgg());
    }

    @Nullable
    public static ICapabilityProvider getCustom(ItemStack stack) {
        for (var entry : CUSTOM_ITEMS.entrySet()) {
            if (entry.getKey().testIgnoreCount(stack)) {
                return entry.getValue().get();
            }
        }

        return null;
    }
}
