package su.terrafirmagreg.api.spi.itemblock;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.ItemSizeHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;

public class ItemBlockBase extends ItemBlock implements IItemSize {

	private final IItemSize size;

	public ItemBlockBase(Block block) {
		this(block, block instanceof IItemSize ? (IItemSize) block : ItemSizeHandler.getDefault());
	}

	public ItemBlockBase(Block block, IItemSize size) {
		super(block);

		this.size = size;
	}

	@NotNull
	@Override
	public Size getSize(@NotNull ItemStack stack) {
		return size.getSize(stack);
	}

	@NotNull
	@Override
	public Weight getWeight(@NotNull ItemStack stack) {
		return size.getWeight(stack);
	}

	@Override
	public boolean canStack(@NotNull ItemStack stack) {
		return size.canStack(stack);
	}

	/**
	 * @see ItemBase#getItemStackLimit(ItemStack)
	 */
	@Override
	public int getItemStackLimit(@NotNull ItemStack stack) {
		return getWeight(stack).getStackSize();
	}
}
