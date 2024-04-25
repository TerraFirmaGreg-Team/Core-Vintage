package su.terrafirmagreg.modules.core.api.capabilities.metal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;


import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderMetal implements ICapabilityMetal {

    private final Metal metal;
    private final int amount;
    private final boolean canMelt;

    public ProviderMetal(Metal metal, int amount, boolean canMelt) {
        this.metal = metal;
        this.amount = amount;
        this.canMelt = canMelt;
    }

    public ProviderMetal() {
        this(Metal.UNKNOWN, 0, false);
    }

    @Nullable
    @Override
    public Metal getMetal(ItemStack stack) {
        return metal;
    }

    @Override
    public int getSmeltAmount(ItemStack stack) {
        return amount;
    }

    @Override
    public boolean canMelt(ItemStack stack) {
        return canMelt;
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityMetal.METAL_CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? (T) this : null;
    }
}
