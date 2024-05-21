package su.terrafirmagreg.api.capabilities.pull;

import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderPull implements ICapabilityPull {

    private EntityWoodCart drawn;

    public ProviderPull() {
        this(null);
    }

    public ProviderPull(EntityWoodCart drawn) {
        this.drawn = drawn;
    }

    @Override
    public EntityWoodCart getDrawn() {
        return drawn;
    }

    @Override
    public void setDrawn(EntityWoodCart drawn) {
        this.drawn = drawn;
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityPull.CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? (T) this : null;
    }

}
