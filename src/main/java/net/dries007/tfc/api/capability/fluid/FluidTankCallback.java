package net.dries007.tfc.api.capability.fluid;

import net.minecraftforge.fluids.FluidTank;

public class FluidTankCallback extends FluidTank {

    private final int ID;
    private final IFluidTankCallback callback;

    public FluidTankCallback(IFluidTankCallback callback, int fluidTankID, int capacity) {
        super(capacity);
        this.callback = callback;
        this.ID = fluidTankID;
    }

    @Override
    protected void onContentsChanged() {
        callback.setAndUpdateFluidTank(ID);
    }
}
