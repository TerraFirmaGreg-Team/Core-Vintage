package net.dries007.tfc.common.objects.container;

import net.dries007.tfc.common.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.common.objects.tileentities.TEBlastFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerBlastFurnace extends ContainerTE<TEBlastFurnace> {
    public ContainerBlastFurnace(InventoryPlayer playerInv, TEBlastFurnace tile) {
        super(playerInv, tile);
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (inventory != null) {
            addSlotToContainer(new SlotCallback(inventory, TEBlastFurnace.SLOT_TUYERE, 153, 7, tile));
        }
    }
}
