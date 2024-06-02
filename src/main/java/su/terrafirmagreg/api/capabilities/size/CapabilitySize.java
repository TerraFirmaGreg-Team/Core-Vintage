package su.terrafirmagreg.api.capabilities.size;

import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.BlockLadder;
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


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CapabilitySize {

    public static final ResourceLocation KEY = ModUtils.resource("size_capability");

    @CapabilityInject(ICapabilitySize.class)
    public static final Capability<ICapabilitySize> CAPABILITY = ModUtils.getNull();

    public static void register() {
        CapabilityManager.INSTANCE.register(ICapabilitySize.class, new StorageSize(), ProviderSize::new);
    }

    public static ICapabilitySize get(ItemStack itemStack) {
        return itemStack.getCapability(CAPABILITY, null);
    }

    public static boolean has(ItemStack itemStack) {
        return itemStack.hasCapability(CAPABILITY, null);
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
            } else if (stack.getItem() instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof ICapabilitySize capabilitySize) {
                return capabilitySize;
            }
        }
        return null;
    }

    @NotNull
    public static ICapabilityProvider getCustom(ItemStack stack) {

        for (var entry : HandlerSize.CUSTOM_ITEMS.entrySet()) {
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
        } else if (item instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof BlockLadder) {
            return ProviderSize.get(Size.SMALL, Weight.VERY_LIGHT, true); // Fits small vessels and stacksize = 64
        } else if (item instanceof ItemBlock) {
            return ProviderSize.get(Size.SMALL, Weight.LIGHT, true); // Fits small vessels and stacksize = 32
        } else {
            return ProviderSize.get(Size.VERY_SMALL, Weight.VERY_LIGHT, true); // Stored anywhere and stacksize = 64
        }
    }
}
