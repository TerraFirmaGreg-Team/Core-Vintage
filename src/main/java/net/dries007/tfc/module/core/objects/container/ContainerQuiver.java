package net.dries007.tfc.module.core.objects.container;

import net.dries007.tfc.common.objects.inventory.capability.ISlotCallback;
import net.dries007.tfc.common.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.module.core.api.container.ContainerItemStack;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerQuiver extends ContainerItemStack implements ISlotCallback {
    public ContainerQuiver(InventoryPlayer playerInv, ItemStack stack) {
        super(playerInv, stack);
        this.itemIndex += 8;
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (inventory instanceof ISlotCallback callback) {
            addSlotToContainer(new SlotCallback(inventory, 0, 53, 23, callback));
            addSlotToContainer(new SlotCallback(inventory, 1, 71, 23, callback));
            addSlotToContainer(new SlotCallback(inventory, 2, 89, 23, callback));
            addSlotToContainer(new SlotCallback(inventory, 3, 107, 23, callback));
            addSlotToContainer(new SlotCallback(inventory, 4, 53, 41, callback));
            addSlotToContainer(new SlotCallback(inventory, 5, 71, 41, callback));
            addSlotToContainer(new SlotCallback(inventory, 6, 89, 41, callback));
            addSlotToContainer(new SlotCallback(inventory, 7, 107, 41, callback));
        }
    }
}
