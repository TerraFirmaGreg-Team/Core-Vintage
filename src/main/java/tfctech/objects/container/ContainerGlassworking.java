package tfctech.objects.container;

import su.terrafirmagreg.api.lib.MathConstants;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemStackHandler;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.recipes.GlassworkingRecipe;
import net.dries007.tfc.objects.container.IButtonHandler;
import net.dries007.tfc.objects.inventory.slot.SlotKnappingOutput;
import net.dries007.tfc.objects.items.glassworking.ItemGlassMolder;
import net.dries007.tfc.util.SimpleCraftMatrix;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("WeakerAccess")

@MethodsReturnNonnullByDefault
public class ContainerGlassworking extends Container implements IButtonHandler {

    private final SimpleCraftMatrix matrix;
    private final ItemStack stack;
    private final EntityPlayer player;
    private final int itemDragIndex;
    private final boolean isOffhand;
    private int itemIndex;
    private boolean requiresReset;

    public ContainerGlassworking(InventoryPlayer playerInv, ItemStack stack) {
        this.player = playerInv.player;
        this.stack = stack;
        this.itemDragIndex = playerInv.currentItem;
        if (stack == this.player.getHeldItemMainhand()) {
            this.itemIndex = playerInv.currentItem + 27;
            this.isOffhand = false;
        } else {
            this.itemIndex = -100;
            this.isOffhand = true;
        }

        this.addContainerSlots();
        this.addPlayerInventorySlots(playerInv);

        ++this.itemIndex;
        this.matrix = new SimpleCraftMatrix();
        requiresReset = true;
    }

    @NotNull
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            if (index == this.itemIndex) {
                return ItemStack.EMPTY;
            } else {
                ItemStack itemstack1 = slot.getStack();
                ItemStack itemstack = itemstack1.copy();
                int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
                if (index < containerSlots) {
                    if (!this.mergeItemStack(itemstack1, containerSlots, this.inventorySlots.size(), true)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                    return ItemStack.EMPTY;
                }

                if (itemstack1.getCount() == 0) {
                    slot.putStack(ItemStack.EMPTY);
                } else {
                    slot.onSlotChanged();
                }

                if (itemstack1.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                } else {
                    slot.onTake(player, itemstack1);
                    return itemstack;
                }
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @NotNull
    public ItemStack slotClick(int slotID, int dragType, ClickType clickType, EntityPlayer player) {
        if (slotID == this.itemIndex &&
                (clickType == ClickType.QUICK_MOVE || clickType == ClickType.PICKUP || clickType == ClickType.THROW || clickType == ClickType.SWAP)) {
            return ItemStack.EMPTY;
        } else {
            return dragType == this.itemDragIndex && clickType == ClickType.SWAP ? ItemStack.EMPTY :
                    super.slotClick(slotID, dragType, clickType, player);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable NBTTagCompound nbtTagCompound) {
        setSlotState(buttonID, false);
        Slot slot = this.inventorySlots.get(0);
        if (slot != null) {
            GlassworkingRecipe recipe = GlassworkingRecipe.get(matrix);
            if (recipe != null) {
                slot.putStack(recipe.getOutput());
            } else {
                slot.putStack(ItemStack.EMPTY);
            }
        }
    }

    public boolean requiresReset() {
        return this.requiresReset;
    }

    public void setRequiresReset(boolean value) {
        this.requiresReset = value;
    }

    /**
     * Used in client to check a slot state in the matrix
     *
     * @param index the slot index
     * @return the boolean state for the checked slot
     */
    public boolean getSlotState(int index) {
        return matrix.get(index);
    }

    public void setSlotState(int index, boolean value) {
        matrix.set(index, value);
        // Check if glass has not solidified
        if (!canWork()) {
            matrix.setAll(false);
        }
    }

    public boolean isSolidified() {
        IItemHeat capHeat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
        return capHeat instanceof ItemGlassMolder.GlassMolderCapability && ((ItemGlassMolder.GlassMolderCapability) capHeat).isSolidified();
    }

    public boolean canWork() {
        IItemHeat capHeat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
        return capHeat instanceof ItemGlassMolder.GlassMolderCapability && ((ItemGlassMolder.GlassMolderCapability) capHeat).canWork();
    }

    private void addContainerSlots() {
        this.addSlotToContainer(new SlotKnappingOutput(new ItemStackHandler(1), 0, 128, 44, this::finishCraft));
    }

    private void finishCraft() {
        matrix.setAll(false);
        requiresReset = true;
        ItemStack emptyBlowpipe = stack;
        IFluidHandlerItem cap = emptyBlowpipe.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (cap instanceof ItemGlassMolder.GlassMolderCapability) {
            ((ItemGlassMolder.GlassMolderCapability) cap).empty();
        }
        emptyBlowpipe.attemptDamageItem(1, MathConstants.RNG, null);
        if (emptyBlowpipe.getItemDamage() >= emptyBlowpipe.getMaxDamage()) {
            emptyBlowpipe = ItemStack.EMPTY;
        }
        if (this.isOffhand) {
            this.player.setHeldItem(EnumHand.OFF_HAND, emptyBlowpipe);
        } else {
            this.player.setHeldItem(EnumHand.MAIN_HAND, emptyBlowpipe);
        }
    }

    private void addPlayerInventorySlots(InventoryPlayer playerInv) {
        // Add Player Inventory Slots (lower down)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142 + 18));
        }
    }
}
