package su.terrafirmagreg.modules.core.capabilities.size;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CapabilityHandlerSize {

    //Used inside CT, set custom IItemSize for items outside TFC
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new LinkedHashMap<>();

    public static void init() {
        // Add hardcoded size values for vanilla items
        CUSTOM_ITEMS.put(IIngredient.of(Items.COAL),
                () -> CapabilityProviderSize.get(Size.SMALL, Weight.LIGHT, true)); // Store anywhere stacksize = 32

        CUSTOM_ITEMS.put(IIngredient.of(Items.STICK),
                () -> CapabilityProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64

        CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL),
                () -> CapabilityProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64

        CUSTOM_ITEMS.put(IIngredient.of(Items.BED),
                () -> CapabilityProviderSize.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1

        CUSTOM_ITEMS.put(IIngredient.of(Items.MINECART),
                () -> CapabilityProviderSize.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1

        CUSTOM_ITEMS.put(IIngredient.of(Items.ARMOR_STAND),
                () -> CapabilityProviderSize.get(Size.LARGE, Weight.HEAVY, true)); // Store only in chests stacksize = 4

        CUSTOM_ITEMS.put(IIngredient.of(Items.CAULDRON),
                () -> CapabilityProviderSize.get(Size.LARGE, Weight.LIGHT, true)); // Store only in chests stacksize = 32

        CUSTOM_ITEMS.put(IIngredient.of(Blocks.TRIPWIRE_HOOK),
                () -> CapabilityProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64

        CUSTOM_ITEMS.put(IIngredient.of(Blocks.TRIPWIRE_HOOK),
                () -> CapabilityProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64
    }

    @NotNull
    public static ICapabilityProvider getCustom(ItemStack stack) {

        for (var entry : CUSTOM_ITEMS.entrySet()) {
            if (entry.getKey().testIgnoreCount(stack)) {
                return entry.getValue().get();
            }
        }
        // Check for generic item types
        Item item = stack.getItem();
        if (item instanceof IItemSettings provider) {
            var settings = provider.getSettings();
            return CapabilityProviderSize.get(settings.getSize(), settings.getWeight(), settings.isCanStack());

        }
        if (item instanceof ItemTool || item instanceof ItemSword) {
            return CapabilityProviderSize.get(Size.LARGE, Weight.MEDIUM, true); // Stored only in chests, stacksize should be limited to 1 since it is a tool

        }
        if (item instanceof ItemArmor) {
            return CapabilityProviderSize.get(Size.LARGE, Weight.VERY_HEAVY, true); // Stored only in chests and stacksize = 1

        }
        if (item instanceof ItemBlock itemBlock) {
            Block block = itemBlock.getBlock();
            if (block instanceof BlockLadder) {
                return CapabilityProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true); // Fits small vessels and stacksize = 64
            }
//            if (block instanceof IBlockSettings provider) {
//                var settings = provider.getSettings();
//                return CapabilityProviderSize.get(settings.getSize(), settings.getWeight(), settings.isCanStack());
//            }
            return CapabilityProviderSize.get(Size.SMALL, Weight.LIGHT, true); // Fits small vessels and stacksize = 32

        }
        return CapabilityProviderSize.get(Size.VERY_SMALL, Weight.VERY_LIGHT, true); // Stored anywhere and stacksize = 64
    }
}
