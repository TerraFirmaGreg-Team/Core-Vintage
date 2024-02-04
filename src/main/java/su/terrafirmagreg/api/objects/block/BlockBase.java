package su.terrafirmagreg.api.objects.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.objects.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.IItemProvider;

public abstract class BlockBase extends Block implements IItemProvider {

	public BlockBase(Material materialIn) {
		super(materialIn);
	}

	public BlockBase(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMapColorIn);
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}
}
