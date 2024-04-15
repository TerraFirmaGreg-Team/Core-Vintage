package su.terrafirmagreg.modules.device.objects.container;

import su.terrafirmagreg.api.spi.container.ContainerBaseTE;
import su.terrafirmagreg.modules.device.objects.tiles.TELogPile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ContainerLogPile extends ContainerBaseTE<TELogPile> {

    public ContainerLogPile(InventoryPlayer playerInv, TELogPile te) {
        super(playerInv, te);
        te.setContainerOpen(true);
    }

    @Override
    public boolean canInteractWith(@NotNull EntityPlayer player) {
        return tile.canInteractWith(player);
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

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        // Marks the log pile as closed, allows it to delete itself if there aren't any logs in it
        tile.setContainerOpen(false);
        super.onContainerClosed(playerIn);
    }
}
