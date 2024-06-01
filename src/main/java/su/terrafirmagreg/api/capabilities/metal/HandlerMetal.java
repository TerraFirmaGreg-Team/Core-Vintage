package su.terrafirmagreg.api.capabilities.metal;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.capability.metal.MetalItemHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HandlerMetal {

    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_METAL_ITEMS = new HashMap<>(); //Used inside CT, set custom IMetalItem for items outside TFC

    public static void init() {
        CUSTOM_METAL_ITEMS.put(IIngredient.of(Blocks.IRON_BARS), () -> new MetalItemHandler(Metal.WROUGHT_IRON, 25, true));
    }
}
