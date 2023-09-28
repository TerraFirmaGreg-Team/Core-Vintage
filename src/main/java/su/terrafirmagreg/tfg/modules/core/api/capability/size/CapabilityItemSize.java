package su.terrafirmagreg.tfg.modules.core.api.capability.size;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.BlockLadder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import su.terrafirmagreg.tfg.modules.core.api.capability.DumbStorage;
import su.terrafirmagreg.tfg.modules.core.api.inventory.ingredient.IIngredient;
import su.terrafirmagreg.tfg.modules.core.api.util.Helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

public final class CapabilityItemSize {
    public static final ResourceLocation KEY = Helpers.getID("item_size");
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new Object2ObjectLinkedOpenHashMap<>(); //Used inside CT, set custom IItemSize for items outside TFC
    @CapabilityInject(IItemSize.class)
    public static Capability<IItemSize> ITEM_SIZE_CAPABILITY;

    public static void preInit() {
        // Register the capability
        CapabilityManager.INSTANCE.register(IItemSize.class, new DumbStorage<>(), ItemSizeHandler::getDefault);
    }

    public static void init() {
        // Add hardcoded size values for vanilla items
        CUSTOM_ITEMS.put(IIngredient.of(Items.COAL), () -> ItemSizeHandler.get(Size.SMALL, Weight.LIGHT, true)); // Store anywhere stacksize = 32
        //CUSTOM_ITEMS.put(IIngredient.of(Items.STICK), ItemStickCapability::new); // Store anywhere stacksize = 64
        CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL), () -> ItemSizeHandler.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64
        CUSTOM_ITEMS.put(IIngredient.of(Items.BED), () -> ItemSizeHandler.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1
        CUSTOM_ITEMS.put(IIngredient.of(Items.MINECART), () -> ItemSizeHandler.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1
        CUSTOM_ITEMS.put(IIngredient.of(Items.ARMOR_STAND), () -> ItemSizeHandler.get(Size.LARGE, Weight.HEAVY, true)); // Store only in chests stacksize = 4
        CUSTOM_ITEMS.put(IIngredient.of(Items.CAULDRON), () -> ItemSizeHandler.get(Size.LARGE, Weight.LIGHT, true)); // Store only in chests stacksize = 32
        CUSTOM_ITEMS.put(IIngredient.of(Blocks.TRIPWIRE_HOOK), () -> ItemSizeHandler.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64
    }

    /**
     * Checks if an item is of a given size and weight
     */
    public static boolean checkItemSize(ItemStack stack, Size size, Weight weight) {
        IItemSize cap = getIItemSize(stack);
        if (cap != null) {
            return cap.getWeight(stack) == weight && cap.getSize(stack) == size;
        }
        return false;
    }

    /**
     * Gets the IItemSize instance from an itemstack, either via capability or via interface
     *
     * @param stack The stack
     * @return The IItemSize if it exists, or null if it doesn't
     */
    @Nullable
    public static IItemSize getIItemSize(ItemStack stack) {
        if (!stack.isEmpty()) {
            IItemSize size = stack.getCapability(ITEM_SIZE_CAPABILITY, null);
            if (size != null) {
                return size;
            } else if (stack.getItem() instanceof IItemSize) {
                return (IItemSize) stack.getItem();
            } else if (stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).getBlock() instanceof IItemSize) {
                return (IItemSize) ((ItemBlock) stack.getItem()).getBlock();
            }
        }
        return null;
    }

    @Nonnull
    public static ICapabilityProvider getCustomSize(ItemStack stack) {
        for (Map.Entry<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> entry : CUSTOM_ITEMS.entrySet()) {
            if (entry.getKey().testIgnoreCount(stack)) {
                return entry.getValue().get();
            }
        }
        // Check for generic item types
        Item item = stack.getItem();
        if (item instanceof ItemTool || item instanceof ItemSword) {
            return ItemSizeHandler.get(Size.LARGE, Weight.MEDIUM, true); // Stored only in chests, stacksize should be limited to 1 since it is a tool
        } else if (item instanceof ItemArmor) {
            return ItemSizeHandler.get(Size.LARGE, Weight.VERY_HEAVY, true); // Stored only in chests and stacksize = 1
        } else if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof BlockLadder) {
            return ItemSizeHandler.get(Size.SMALL, Weight.VERY_LIGHT, true); // Fits small vessels and stacksize = 64
        } else if (item instanceof ItemBlock) {
            return ItemSizeHandler.get(Size.SMALL, Weight.LIGHT, true); // Fits small vessels and stacksize = 32
        } else {
            return ItemSizeHandler.get(Size.VERY_SMALL, Weight.VERY_LIGHT, true); // Stored anywhere and stacksize = 64
        }
    }
}
