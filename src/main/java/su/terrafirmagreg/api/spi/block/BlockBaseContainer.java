package su.terrafirmagreg.api.spi.block;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;

public abstract class BlockBaseContainer extends BlockContainer implements IAutoReg, IItemSize {

	public BlockBaseContainer(Material materialIn) {
		super(materialIn);
	}

	public BlockBaseContainer(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMapColorIn);
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}
}
