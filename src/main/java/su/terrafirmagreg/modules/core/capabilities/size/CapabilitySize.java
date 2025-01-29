package su.terrafirmagreg.modules.core.capabilities.size;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public final class CapabilitySize {

    public static final ResourceLocation KEY = ModUtils.resource("size_capability");

    @CapabilityInject(ICapabilitySize.class)
    public static final Capability<ICapabilitySize> CAPABILITY = ModUtils.getNull();

    public static void register() {
        CapabilityManager.INSTANCE.register(ICapabilitySize.class, new CapabilityStorageSize(), CapabilityProviderSize::new);
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
            }
            Item item = stack.getItem();
            if (item instanceof ICapabilitySize capabilitySize) {
                return capabilitySize;
            }
            if (item instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof ICapabilitySize capabilitySize) {
                return capabilitySize;
            }
        }
        return null;
    }

}
