package su.terrafirmagreg.modules.device.objects.containers;

import su.terrafirmagreg.modules.device.objects.tiles.TileElectricForge;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.container.ContainerTE;
import net.dries007.tfc.objects.container.IButtonHandler;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import org.jetbrains.annotations.Nullable;

public class ContainerElectricForge extends ContainerTE<TileElectricForge> implements IButtonHandler {

    public ContainerElectricForge(InventoryPlayer playerInv, TileElectricForge te) {
        super(playerInv, te);
    }

    @Override
    public void onButtonPress(int i, @Nullable NBTTagCompound nbtTagCompound) {
        int value = i % 2 == 0 ? 1 : -1;
        if (i / 4 < 1) {
            if (i / 2 < 1) {
                value *= 50;
            } else {
                value *= 200;
            }
        }
        tile.addTargetTemperature(value);
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (inventory != null) {
            for (int i = 0; i < 9; i++) {
                int row = i / 3;
                int column = i % 3;
                int x = 62 + column * 18;
                int y = 10 + row * 18;
                addSlotToContainer(new SlotCallback(inventory, i, x, y, tile));
            }
            //Adds remaining 3 mold slots
            addSlotToContainer(new SlotCallback(inventory, 9, 134, 10, tile));
            addSlotToContainer(new SlotCallback(inventory, 10, 134, 28, tile));
            addSlotToContainer(new SlotCallback(inventory, 11, 134, 46, tile));
        }
    }
}
