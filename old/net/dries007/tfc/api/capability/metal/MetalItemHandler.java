package net.dries007.tfc.api.capability.metal;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetalItemHandler implements ICapabilityProvider, IMaterialItem {
    private final Material material;
    private final int amount;
    private final boolean canMelt;

    public MetalItemHandler(Material metal, int amount, boolean canMelt) {
        this.material = metal;
        this.amount = amount;
        this.canMelt = canMelt;
    }

    public MetalItemHandler() {
        this(TFGMaterials.Unknown, 0, false);
    }

    @Nullable
    @Override
    public Material getMaterial(ItemStack stack) {
        return material;
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
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityMetalItem.METAL_OBJECT_CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityMetalItem.METAL_OBJECT_CAPABILITY ? (T) this : null;
    }
}
