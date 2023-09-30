package net.dries007.tfc.module.animal.common.container;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.module.core.api.objects.container.ContainerTE;
import net.dries007.tfc.common.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.module.animal.common.tiles.TENestBox;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ContainerNestBox extends ContainerTE<TENestBox> {
    public ContainerNestBox(InventoryPlayer playerInv, TENestBox te) {
        super(playerInv, te);
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (inventory != null) {
            addSlotToContainer(new SlotCallback(inventory, 0, 71, 23, tile));
            addSlotToContainer(new SlotCallback(inventory, 1, 89, 23, tile));
            addSlotToContainer(new SlotCallback(inventory, 2, 71, 41, tile));
            addSlotToContainer(new SlotCallback(inventory, 3, 89, 41, tile));
        }
    }
}
