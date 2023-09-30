package net.dries007.tfc.module.devices.common.container;

import net.dries007.tfc.module.core.api.objects.container.ContainerTE;
import net.dries007.tfc.common.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.module.devices.common.tile.TECharcoalForge;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ContainerCharcoalForge extends ContainerTE<TECharcoalForge> {
    public ContainerCharcoalForge(InventoryPlayer playerInv, TECharcoalForge te) {
        super(playerInv, te);
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (inventory != null) {
            // Fuel slots
            // Note: the order of these statements is important
            int index = TECharcoalForge.SLOT_FUEL_MIN;
            addSlotToContainer(new SlotCallback(inventory, index++, 80, 62, tile));
            addSlotToContainer(new SlotCallback(inventory, index++, 98, 44, tile));
            addSlotToContainer(new SlotCallback(inventory, index++, 62, 44, tile));
            addSlotToContainer(new SlotCallback(inventory, index++, 116, 26, tile));
            addSlotToContainer(new SlotCallback(inventory, index, 44, 26, tile));

            // Input slots
            // Note: the order of these statements is important
            index = TECharcoalForge.SLOT_INPUT_MIN;
            addSlotToContainer(new SlotCallback(inventory, index++, 80, 44, tile));
            addSlotToContainer(new SlotCallback(inventory, index++, 98, 26, tile));
            addSlotToContainer(new SlotCallback(inventory, index++, 62, 26, tile));
            addSlotToContainer(new SlotCallback(inventory, index++, 116, 8, tile));
            addSlotToContainer(new SlotCallback(inventory, index, 44, 8, tile));

            // Extra slots (for ceramic molds)
            for (int i = TECharcoalForge.SLOT_EXTRA_MIN; i <= TECharcoalForge.SLOT_EXTRA_MAX; i++) {
                addSlotToContainer(new SlotCallback(inventory, i, 152, 8 + 18 * (i - TECharcoalForge.SLOT_EXTRA_MIN), tile));
            }
        }
    }

    @Override
    protected boolean transferStackIntoContainer(ItemStack stack, int containerSlots) {
        return !mergeItemStack(stack, TECharcoalForge.SLOT_EXTRA_MIN, TECharcoalForge.SLOT_EXTRA_MAX + 1, false) && !mergeItemStack(stack, TECharcoalForge.SLOT_FUEL_MIN, TECharcoalForge.SLOT_INPUT_MAX + 1, false);
    }
}
