package su.terrafirmagreg.modules.core.api.capabilities.size;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.BlockLadder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.capability.ItemStickCapability;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class CapabilitySize {

    public static final ResourceLocation KEY = ModUtils.id("size_capability");
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new LinkedHashMap<>(); //Used inside CT, set custom IItemSize for items outside TFC
    @CapabilityInject(ICapabilitySize.class)
    public static Capability<ICapabilitySize> SIZE_CAPABILITY;

    public static void preInit() {
        // Register the capability
        CapabilityManager.INSTANCE.register(ICapabilitySize.class, new StorageSize(), ProviderSize::new);
    }

    public static void init() {
        // Add hardcoded size values for vanilla items
        CUSTOM_ITEMS.put(IIngredient.of(Items.COAL), () -> ProviderSize.get(Size.SMALL, Weight.LIGHT, true)); // Store anywhere stacksize = 32
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

    public static ICapabilitySize get(ItemStack itemStack) {
        return itemStack.getCapability(SIZE_CAPABILITY, null);
    }

    public static boolean has(ItemStack itemStack) {
        return itemStack.hasCapability(SIZE_CAPABILITY, null);
    }

    /**
     * Checks if an item is of a given size and weight
     */
    public static boolean checkItemSize(ItemStack stack, Size size, Weight weight) {
        ICapabilitySize cap = getIItemSize(stack);
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
    public static ICapabilitySize getIItemSize(ItemStack stack) {
        if (!stack.isEmpty()) {
            ICapabilitySize size = CapabilitySize.get(stack);
            if (size != null) {
                return size;
            } else if (stack.getItem() instanceof ICapabilitySize item) {
                return item;
            } else if (stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).getBlock() instanceof ICapabilitySize) {
                return (ICapabilitySize) ((ItemBlock) stack.getItem()).getBlock();
            }
        }
        return null;
    }

    @NotNull
    public static ICapabilityProvider getCustomSize(ItemStack stack) {
        for (Map.Entry<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> entry : CUSTOM_ITEMS.entrySet()) {
            if (entry.getKey().testIgnoreCount(stack)) {
                return entry.getValue().get();
            }
        }
        // Check for generic item types
        Item item = stack.getItem();
        if (item instanceof ItemTool || item instanceof ItemSword) {
            return ProviderSize.get(Size.LARGE, Weight.MEDIUM, true); // Stored only in chests, stacksize should be limited to 1 since it is a tool
        } else if (item instanceof ItemArmor) {
            return ProviderSize.get(Size.LARGE, Weight.VERY_HEAVY, true); // Stored only in chests and stacksize = 1
        } else if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof BlockLadder) {
            return ProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true); // Fits small vessels and stacksize = 64
        } else if (item instanceof ItemBlock) {
            return ProviderSize.get(Size.SMALL, Weight.LIGHT, true); // Fits small vessels and stacksize = 32
        } else {
            return ProviderSize.get(Size.VERY_SMALL, Weight.VERY_LIGHT, true); // Stored anywhere and stacksize = 64
        }
    }
}
