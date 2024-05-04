package su.terrafirmagreg.modules.device.objects.containers;

import su.terrafirmagreg.api.spi.button.IButtonHandler;
import su.terrafirmagreg.api.spi.container.BaseContainerTile;
import su.terrafirmagreg.modules.device.objects.blocks.BlockPowderKeg;
import su.terrafirmagreg.modules.device.objects.tiles.TilePowderKeg;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import org.jetbrains.annotations.Nullable;

public class ContainerPowderKeg extends BaseContainerTile<TilePowderKeg> implements IButtonHandler {

    public ContainerPowderKeg(InventoryPlayer playerInv, TilePowderKeg tile) {
        super(playerInv, tile);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
        // Slot will always be 0, extraNBT will be empty
        if (!tile.getWorld().isRemote) {
            BlockPowderKeg.togglePowderKegSeal(tile.getWorld(), tile.getPos());
        }
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if (inventory != null) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 4; x++) {
                    addSlotToContainer(new SlotCallback(inventory, x * 3 + y, 25 + x * 18, 19 + y * 18, tile));
                }
            }
        }
    }
}
