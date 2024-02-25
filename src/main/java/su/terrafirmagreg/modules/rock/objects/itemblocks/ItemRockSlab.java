package su.terrafirmagreg.modules.rock.objects.itemblocks;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockRockSlab;

public class ItemRockSlab extends ItemSlab implements IItemSize {

	public ItemRockSlab(BlockRockSlab.Half slab, BlockRockSlab.Half slab1, BlockRockSlab.Double doubleSlab) {
		super(slab, slab1, doubleSlab);
	}

	@NotNull
	@Override
	public Size getSize(@NotNull ItemStack stack) {
		return Size.SMALL; // if blocks fits in small vessels, this should too
	}

	@NotNull
	@Override
	public Weight getWeight(@NotNull ItemStack stack) {
		return Weight.VERY_LIGHT; // Double the stacksize of a block (or 64)
	}
}
