package net.dries007.tfc.module.ceramic.common.container;

import net.dries007.tfc.module.ceramic.common.blocks.BlockLargeVessel;
import net.dries007.tfc.common.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.module.ceramic.common.tiles.TELargeVessel;
import net.dries007.tfc.module.core.api.container.ContainerTE;
import net.dries007.tfc.module.core.api.container.IButtonHandler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class ContainerLargeVessel extends ContainerTE<TELargeVessel> implements IButtonHandler {
    public ContainerLargeVessel(InventoryPlayer playerInv, TELargeVessel tile) {
        super(playerInv, tile);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
        // Slot will always be 0, extraNBT will be empty
        if (!tile.getWorld().isRemote) {
            BlockLargeVessel.toggleLargeVesselSeal(tile.getWorld(), tile.getPos());
        }
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if (inventory != null) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    addSlotToContainer(new SlotCallback(inventory, x * 3 + y, 34 + x * 18, 19 + y * 18, tile));
                }
            }
        }
    }
}
