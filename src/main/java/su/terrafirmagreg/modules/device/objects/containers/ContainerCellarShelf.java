package su.terrafirmagreg.modules.device.objects.containers;

import su.terrafirmagreg.api.spi.gui.container.BaseContainerTile;
import su.terrafirmagreg.modules.device.objects.tiles.TileCellarShelf;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.inventory.slot.SlotCallback;

public class ContainerCellarShelf extends BaseContainerTile<TileCellarShelf> {

    public ContainerCellarShelf(InventoryPlayer playerInv, TileCellarShelf tile) {
        super(playerInv, tile);
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if (inventory != null) {
            for (int y = 0; y < 2; y++) {
                for (int x = 0; x < 7; x++) {

                    addSlotToContainer(new SlotCallback(inventory, x + y * 7, x * 18 + 26, y * 18 + 25, tile));
                }
            }
        }
    }
}
