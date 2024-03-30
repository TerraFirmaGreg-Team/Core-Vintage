package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

@Getter
public class BlockWoodStairs extends BlockStairs implements IWoodBlock {


	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	public BlockWoodStairs(WoodBlockVariant modelBlock, WoodBlockVariant blockVariant, WoodType type) {
		super(modelBlock.get(type).getDefaultState());

		this.blockVariant = blockVariant;
		this.type = type;
		this.useNeighborBrightness = true;
		setHarvestLevel("axe", 0);

//            OreDictUtils.register(this, variant.toString());
//            OreDictUtils.register(this, variant.toString(), "wood");
//            OreDictUtils.register(this, variant.toString(), "wood", type.toString());
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}
}
