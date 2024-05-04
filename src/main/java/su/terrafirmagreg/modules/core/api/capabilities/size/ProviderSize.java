package su.terrafirmagreg.modules.core.api.capabilities.size;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class ProviderSize implements ICapabilitySize {

    private static final EnumMap<Size, EnumMap<Weight, ProviderSize[]>> CACHE = new EnumMap<>(Size.class);
    private final Size size;
    private final Weight weight;
    private final boolean canStack;

    public ProviderSize() {
        this(Size.SMALL, Weight.LIGHT, true);
    }

    public ProviderSize(Size size, Weight weight, boolean canStack) {
        this.size = size;
        this.weight = weight;
        this.canStack = canStack;
    }

    public static ProviderSize get(Size size, Weight weight, boolean canStack) {
        EnumMap<Weight, ProviderSize[]> nested = CACHE.get(size);
        if (nested == null) {
            CACHE.put(size, nested = new EnumMap<>(Weight.class));
        }
        ProviderSize[] handlers = nested.get(weight);
        if (handlers == null) {
            nested.put(weight, handlers = new ProviderSize[2]);
        }
        if (handlers[canStack ? 1 : 0] == null) {
            handlers[canStack ? 1 : 0] = new ProviderSize(size, weight, canStack);
        }
        return handlers[canStack ? 1 : 0];
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

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilitySize.CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? (T) this : null;
    }

}
