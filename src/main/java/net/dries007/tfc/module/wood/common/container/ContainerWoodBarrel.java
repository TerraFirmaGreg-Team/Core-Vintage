package net.dries007.tfc.module.wood.common.container;

import net.dries007.tfc.module.core.common.objects.container.ContainerTE;
import net.dries007.tfc.module.core.common.objects.container.IButtonHandler;
import net.dries007.tfc.module.core.common.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.module.wood.common.blocks.BlockWoodBarrel;
import net.dries007.tfc.module.wood.common.tile.TEWoodBarrel;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class ContainerWoodBarrel extends ContainerTE<TEWoodBarrel> implements IButtonHandler {
    public ContainerWoodBarrel(InventoryPlayer playerInv, TEWoodBarrel teWoodBarrel) {
        super(playerInv, teWoodBarrel);
    }

    @Nullable
    public IFluidHandler getBarrelTank() {
        return tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
        // Slot will always be 0, extraNBT will be empty
        if (!tile.getWorld().isRemote) {
            BlockWoodBarrel.toggleBarrelSeal(tile.getWorld(), tile.getPos());
        }
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if (inventory != null) {
            addSlotToContainer(new SlotCallback(inventory, TEWoodBarrel.SLOT_FLUID_CONTAINER_IN, 35, 20, tile));
            addSlotToContainer(new SlotCallback(inventory, TEWoodBarrel.SLOT_FLUID_CONTAINER_OUT, 35, 54, tile));
            addSlotToContainer(new SlotCallback(inventory, TEWoodBarrel.SLOT_ITEM, 89, 37, tile));
        }
    }
}
