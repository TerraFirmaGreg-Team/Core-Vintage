package su.terrafirmagreg.modules.core.capabilities.size;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class ProviderSize implements ICapabilitySize, ICapabilityProvider {

    private static final EnumMap<Size, EnumMap<Weight, ProviderSize[]>> CACHE = new EnumMap<>(Size.class);
    private final Size size;
    private final Weight weight;
    private final boolean canStack;

    public ProviderSize() {

        this(Size.SMALL, Weight.LIGHT, true);
    }

    public static ProviderSize getDefault() {
        return get(Size.SMALL, Weight.LIGHT, true); // Default to fitting in small vessels and stacksize = 32
    }

    public ProviderSize(Size size, Weight weight, boolean canStack) {

        this.size = size;
        this.weight = weight;
        this.canStack = canStack;
    }

    public static ProviderSize get(Size size, Weight weight, boolean canStack) {
        EnumMap<Weight, ProviderSize[]> nested = CACHE.computeIfAbsent(size, k -> new EnumMap<>(Weight.class));
        ProviderSize[] handlers = nested.computeIfAbsent(weight, k -> new ProviderSize[2]);
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
