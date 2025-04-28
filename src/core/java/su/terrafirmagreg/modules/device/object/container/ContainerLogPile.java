package su.terrafirmagreg.modules.device.object.container;

import su.terrafirmagreg.modules.device.object.tile.TileLogPile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.container.ContainerTE;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ContainerLogPile extends ContainerTE<TileLogPile> {

  public ContainerLogPile(InventoryPlayer playerInv, TileLogPile te) {
    super(playerInv, te);
    te.setContainerOpen(true);
  }

  @Override
  public boolean canInteractWith(@Nonnull EntityPlayer player) {
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
