package su.terrafirmagreg.modules.device.objects.container;

import net.dries007.tfc.objects.inventory.slot.SlotCallback;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.container.ContainerBaseTE;
import su.terrafirmagreg.api.spi.container.IButtonHandler;
import su.terrafirmagreg.modules.device.objects.blocks.BlockCrate;
import su.terrafirmagreg.modules.device.objects.tiles.TECrate;

public class ContainerCrate extends ContainerBaseTE<TECrate> implements IButtonHandler {
	public ContainerCrate(InventoryPlayer playerInv, TECrate tile) {
		super(playerInv, tile);
	}

	@Override
	public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
		// Slot will always be 0, extraNBT will be empty
		if (!tile.getWorld().isRemote) {
			BlockCrate.toggleCrateSeal(tile.getWorld(), tile.getPos());
		}
	}

	@Override
	protected void addContainerSlots() {
		IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		if (inventory != null) {
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 5; x++) {
					addSlotToContainer(new SlotCallback(inventory, x * 3 + y, 34 + x * 18, 19 + y * 18, tile));
				}
			}
		}
	}
}
