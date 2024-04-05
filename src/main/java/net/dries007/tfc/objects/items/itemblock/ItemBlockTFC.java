package net.dries007.tfc.objects.items.itemblock;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.ItemSizeHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemBlockTFC extends ItemBlock implements IItemSize {
	private final IItemSize size;

	public ItemBlockTFC(Block block) {
		this(block, block instanceof IItemSize ? (IItemSize) block : ItemSizeHandler.getDefault());
	}

	public ItemBlockTFC(Block block, IItemSize size) {
		super(block);

		this.size = size;
	}


	@Override
	public @NotNull Size getSize(@NotNull ItemStack stack) {
		return size.getSize(stack);
	}


	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack stack) {
		return size.getWeight(stack);
	}

	@Override
	public boolean canStack(@NotNull ItemStack stack) {
		return size.canStack(stack);
	}

	/**
	 * @see net.dries007.tfc.objects.items.ItemTFC#getItemStackLimit(ItemStack)
	 */
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return getWeight(stack).stackSize;
	}
}
