package dev.quarris.tfcalloycalc;

import net.dries007.tfc.util.Alloy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityCalc extends TileEntity {

    public final ItemStackHandler stacks = new ItemStackHandler(9);
    private Alloy alloy;

    public TileEntityCalc() {

    }

    public Alloy getAlloy() {
        return this.alloy;
    }

    public void calculateAlloy() {
        Alloy computedAlloy = new Alloy();
        for (int slot = 0; slot < this.stacks.getSlots(); slot++) {
            computedAlloy.add(this.stacks.getStackInSlot(slot));
        }
        if (computedAlloy.getAmount() == 0)
            this.alloy = null;
        else
            this.alloy = computedAlloy;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("Stacks", this.stacks.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.stacks.deserializeNBT(compound.getCompoundTag("Stacks"));
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) this.stacks;
        }

        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }
}
