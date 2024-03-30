package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

@Getter
public class BlockWoodFence extends BlockFence implements IWoodBlock {

	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	public BlockWoodFence(WoodBlockVariant blockVariant, WoodType type) {
		super(Material.WOOD, Material.WOOD.getMaterialMapColor());

		this.blockVariant = blockVariant;
		this.type = type;

		setSoundType(SoundType.WOOD);
		setHarvestLevel("axe", 0);
		setHardness(2.0F);
		setResistance(15.0F);

		//OreDictUtils.register(this, variant.toString());
		//OreDictUtils.register(this, variant.toString(), type.toString());
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}
}
