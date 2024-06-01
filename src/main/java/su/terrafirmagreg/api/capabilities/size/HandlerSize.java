package su.terrafirmagreg.api.capabilities.size;

import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.capability.ItemStickCapability;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HandlerSize {

    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new LinkedHashMap<>(); //Used inside CT, set custom IItemSize for items outside TFC

    public static void init() {
        // Add hardcoded size values for vanilla items
        CUSTOM_ITEMS.put(IIngredient.of(Items.COAL),
                () -> ProviderSize.get(Size.SMALL, Weight.LIGHT, true)); // Store anywhere stacksize = 32

        CUSTOM_ITEMS.put(IIngredient.of(Items.STICK), ItemStickCapability::new); // Store anywhere stacksize = 64

        CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL),
                () -> ProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64

        CUSTOM_ITEMS.put(IIngredient.of(Items.BED),
                () -> ProviderSize.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1

        CUSTOM_ITEMS.put(IIngredient.of(Items.MINECART),
                () -> ProviderSize.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1

        CUSTOM_ITEMS.put(IIngredient.of(Items.ARMOR_STAND),
                () -> ProviderSize.get(Size.LARGE, Weight.HEAVY, true)); // Store only in chests stacksize = 4

        CUSTOM_ITEMS.put(IIngredient.of(Items.CAULDRON),
                () -> ProviderSize.get(Size.LARGE, Weight.LIGHT, true)); // Store only in chests stacksize = 32

        CUSTOM_ITEMS.put(IIngredient.of(Blocks.TRIPWIRE_HOOK),
                () -> ProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64
    }
}
