package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.api.base.object.inventory.spi.container.BaseContainerTile;
import su.terrafirmagreg.api.base.object.inventory.spi.slot.SlotCallback;
import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.modules.device.object.tile.TileLogPile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;

import mcp.MethodsReturnNonnullByDefault;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ContainerLogPile extends BaseContainerTile<TileLogPile> {

  public ContainerLogPile(InventoryPlayer playerInv, TileLogPile tile) {
    super(playerInv, tile);
    tile.setContainerOpen(true);
  }

  @Override
  protected void addContainerSlots() {
    CapabilityUtils.getOptional(tile, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(cap -> {
      addSlotToContainer(new SlotCallback(cap, 0, 71, 23, tile));
      addSlotToContainer(new SlotCallback(cap, 1, 89, 23, tile));
      addSlotToContainer(new SlotCallback(cap, 2, 71, 41, tile));
      addSlotToContainer(new SlotCallback(cap, 3, 89, 41, tile));
    });
  }

  @Override
  public boolean canInteractWith(@NotNull EntityPlayer player) {
    return tile.canInteractWith(player);
  }

  @Override
  public void onContainerClosed(EntityPlayer playerIn) {
    // Marks the log pile as closed, allows it to delete itself if there aren't any logs in it
    tile.setContainerOpen(false);
    super.onContainerClosed(playerIn);
  }
}
