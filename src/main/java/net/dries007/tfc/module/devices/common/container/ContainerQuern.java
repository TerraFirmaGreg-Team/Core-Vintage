package net.dries007.tfc.module.devices.common.container;

import net.dries007.tfc.module.api.common.container.ContainerTE;
import net.dries007.tfc.common.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.module.devices.common.tile.TEQuern;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerQuern extends ContainerTE<TEQuern> {
    public ContainerQuern(InventoryPlayer playerInv, TEQuern te) {
        super(playerInv, te);
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (inventory != null) {
            addSlotToContainer(new SlotCallback(inventory, TEQuern.SLOT_HANDSTONE, 93, 20, tile));
            addSlotToContainer(new SlotCallback(inventory, TEQuern.SLOT_INPUT, 66, 47, tile));
            addSlotToContainer(new SlotCallback(inventory, TEQuern.SLOT_OUTPUT, 93, 47, tile));
        }
    }
}
