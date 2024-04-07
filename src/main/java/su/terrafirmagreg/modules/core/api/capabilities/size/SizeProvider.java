package su.terrafirmagreg.modules.core.api.capabilities.size;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class SizeProvider implements ICapabilityProvider, ISizeCapability {

    private static final EnumMap<Size, EnumMap<Weight, SizeProvider[]>> CACHE = new EnumMap<>(Size.class);
    private final Size size;
    private final Weight weight;
    private final boolean canStack;

    public SizeProvider(Size size, Weight weight, boolean canStack) {
        this.size = size;
        this.weight = weight;
        this.canStack = canStack;
    }

    public static SizeProvider get(Size size, Weight weight, boolean canStack) {
        EnumMap<Weight, SizeProvider[]> nested = CACHE.get(size);
        if (nested == null) {
            CACHE.put(size, nested = new EnumMap<>(Weight.class));
        }
        SizeProvider[] handlers = nested.get(weight);
        if (handlers == null) {
            nested.put(weight, handlers = new SizeProvider[2]);
        }
        if (handlers[canStack ? 1 : 0] == null) {
            handlers[canStack ? 1 : 0] = new SizeProvider(size, weight, canStack);
        }
        return handlers[canStack ? 1 : 0];
    }

    public static SizeProvider getDefault() {
        return get(Size.SMALL, Weight.LIGHT, true); // Default to fitting in small vessels and stacksize = 32
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == SizeCapability.SIZE_CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == SizeCapability.SIZE_CAPABILITY ? (T) this : null;
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
