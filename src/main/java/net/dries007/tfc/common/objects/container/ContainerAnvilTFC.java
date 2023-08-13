package net.dries007.tfc.common.objects.container;

import gregtech.common.items.ToolItems;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.common.objects.inventory.slot.SlotCallback;
import net.dries007.tfc.common.objects.tileentities.TEAnvilTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.forge.ForgeStep;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.client.gui.GuiAnvilTFC.*;

@ParametersAreNonnullByDefault
public class ContainerAnvilTFC extends ContainerTE<TEAnvilTFC> implements IButtonHandler {
    public ContainerAnvilTFC(InventoryPlayer playerInv, TEAnvilTFC te) {
        super(playerInv, te, 26);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
        if (buttonID >= BUTTON_ID_STEP_MIN && buttonID <= BUTTON_ID_STEP_MAX) {
            // Add a step to the anvil
            if (attemptWork()) {
                tile.addStep(player, ForgeStep.valueOf(buttonID - BUTTON_ID_STEP_MIN));
            }
        } else if (buttonID == BUTTON_ID_PLAN) {
            // Switch to anvil plan gui
            Slot slotInput = inventorySlots.get(TEAnvilTFC.SLOT_INPUT_1);
            if (slotInput != null) {
                ItemStack stack = slotInput.getStack();
                if (!stack.isEmpty() && !AnvilRecipe.getAllFor(stack).isEmpty()) {
                    TFCGuiHandler.openGui(player.world, tile.getPos(), player, TFCGuiHandler.Type.ANVIL_PLAN);
                }
            }
        }
    }

    @Override
    protected void addContainerSlots() {
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (inventory != null) {
            addSlotToContainer(new SlotCallback(inventory, TEAnvilTFC.SLOT_INPUT_1, 31, 68, tile));
            addSlotToContainer(new SlotCallback(inventory, TEAnvilTFC.SLOT_INPUT_2, 13, 68, tile));
            addSlotToContainer(new SlotCallback(inventory, TEAnvilTFC.SLOT_HAMMER, 129, 68, tile));
            addSlotToContainer(new SlotCallback(inventory, TEAnvilTFC.SLOT_FLUX, 147, 68, tile));
        }
    }

    @Override
    protected boolean transferStackIntoContainer(ItemStack stack, int containerSlots) {
        return !mergeItemStack(stack, TEAnvilTFC.SLOT_FLUX, TEAnvilTFC.SLOT_FLUX + 1, false) &&
                !mergeItemStack(stack, TEAnvilTFC.SLOT_HAMMER, TEAnvilTFC.SLOT_HAMMER + 1, false) &&
                !mergeItemStack(stack, TEAnvilTFC.SLOT_INPUT_1, TEAnvilTFC.SLOT_INPUT_2 + 1, false);
    }

    private boolean attemptWork() {
        // This only runs on server

        // Get the slot for input
        Slot slotInput = inventorySlots.get(TEAnvilTFC.SLOT_INPUT_1);
        if (slotInput == null)
            return false;

        ItemStack stack = slotInput.getStack();
        IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);

        // The input must have the forge item capability
        if (cap == null)
            return false;

        // A recipe must exist
        var recipe = TFCRegistries.ANVIL.getValue(cap.getRecipeName());
        if (recipe == null) {
            return false;
        }
        if (!Helpers.isAtLeast(tile.getTier(), recipe.getTier())) {
            TerraFirmaCraft.LOGGER.info("Anvil Tier: {} + Recipe Tier: {}", tile.getTier(), recipe.getTier());
            player.sendMessage(new TextComponentTranslation(MOD_ID + ".tooltip.anvil_tier_too_low"));
            return false;
        }

        if (!cap.isWorkable()) {
            player.sendMessage(new TextComponentTranslation(MOD_ID + ".tooltip.anvil_too_cold"));
            return false;
        }

        Slot slot = inventorySlots.get(TEAnvilTFC.SLOT_HAMMER);
        if (slot == null)
            return false;

        stack = slot.getStack();
        if (!stack.isEmpty()) {
            stack.damageItem(1, player);
            if (stack.getCount() <= 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.putStack(stack);
            }
            return true;
        } else {
            // Fallback to the held item if it is a hammer
            stack = player.inventory.mainInventory.get(player.inventory.currentItem);
            if (stack.getItem() == ToolItems.HARD_HAMMER.get()) {
                stack.damageItem(1, player);
                return true;
            } else {
                player.sendMessage(new TextComponentString("" + TextFormatting.RED).appendSibling(new TextComponentTranslation(MOD_ID + ".tooltip.anvil_no_hammer")));
                return false;
            }
        }
    }
}
