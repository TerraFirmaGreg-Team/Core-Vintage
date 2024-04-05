package net.dries007.tfc.objects.inventory.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public class ItemHandlerSidedWrapper implements IItemHandlerModifiable {
	private final IItemHandlerSidedCallback callback;
	private final IItemHandlerModifiable handler;
	private final EnumFacing side;

	public ItemHandlerSidedWrapper(IItemHandlerSidedCallback callback, IItemHandlerModifiable handler, EnumFacing side) {
		this.callback = callback;
		this.handler = handler;
		this.side = side;
	}

	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack) {
		handler.setStackInSlot(slot, stack);
	}

	@Override
	public int getSlots() {
		return handler.getSlots();
	}

	@NotNull
	@Override
	public ItemStack getStackInSlot(int slot) {
		return handler.getStackInSlot(slot);
	}

	@NotNull
	@Override
	public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
		if (callback.canInsert(slot, stack, side)) {
			return handler.insertItem(slot, stack, simulate);
		}
		return stack;
	}

	@NotNull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (callback.canExtract(slot, side)) {
			return handler.extractItem(slot, amount, simulate);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public int getSlotLimit(int slot) {
		return handler.getSlotLimit(slot);
	}
}
