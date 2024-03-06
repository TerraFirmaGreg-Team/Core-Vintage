package su.terrafirmagreg.modules.wood.objects.blocks;


import lombok.Getter;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

@Getter
public class BlockWoodLadder extends BlockLadder implements IWoodBlock {

	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	public BlockWoodLadder(WoodBlockVariant blockVariant, WoodType type) {
		this.blockVariant = blockVariant;
		this.type = type;

		setSoundType(SoundType.LADDER);
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}
}
