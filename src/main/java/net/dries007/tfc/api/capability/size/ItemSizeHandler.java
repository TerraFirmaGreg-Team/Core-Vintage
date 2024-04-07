package net.dries007.tfc.api.capability.size;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class ItemSizeHandler implements ICapabilityProvider, IItemSize {

    private static final EnumMap<Size, EnumMap<Weight, ItemSizeHandler[]>> CACHE = new EnumMap<>(Size.class);
    private final Size size;
    private final Weight weight;
    private final boolean canStack;

    public ItemSizeHandler(Size size, Weight weight, boolean canStack) {
        this.size = size;
        this.weight = weight;
        this.canStack = canStack;
    }

    public static ItemSizeHandler get(Size size, Weight weight, boolean canStack) {
        EnumMap<Weight, ItemSizeHandler[]> nested = CACHE.get(size);
        if (nested == null) {
            CACHE.put(size, nested = new EnumMap<>(Weight.class));
        }
        ItemSizeHandler[] handlers = nested.get(weight);
        if (handlers == null) {
            nested.put(weight, handlers = new ItemSizeHandler[2]);
        }
        if (handlers[canStack ? 1 : 0] == null) {
            handlers[canStack ? 1 : 0] = new ItemSizeHandler(size, weight, canStack);
        }
        return handlers[canStack ? 1 : 0];
    }

    public static ItemSizeHandler getDefault() {
        return get(Size.SMALL, Weight.LIGHT, true); // Default to fitting in small vessels and stacksize = 32
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemSize.ITEM_SIZE_CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemSize.ITEM_SIZE_CAPABILITY ? (T) this : null;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return this.size;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return this.weight;
    }

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return canStack;
    }

    /**
     * Should be called from {@link net.minecraft.item.Item#getItemStackLimit(ItemStack)}
     */
    @Override
    public int getStackSize(@NotNull ItemStack stack) {
        return this.canStack ? this.weight.stackSize : 1;
    }
}
