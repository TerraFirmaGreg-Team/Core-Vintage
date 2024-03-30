package su.terrafirmagreg.modules.soil.objects.blocks;

import lombok.Getter;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

@Getter
public class BlockSoilMudStairs extends BlockStairs implements ISoilBlock {

	private final SoilBlockVariant blockVariant;
	private final SoilType type;

	public BlockSoilMudStairs(SoilBlockVariant modelBlock, SoilBlockVariant blockVariant, SoilType type) {
		super(modelBlock.get(type).getDefaultState());

		this.blockVariant = blockVariant;
		this.type = type;
		this.useNeighborBrightness = true;

		setSoundType(SoundType.GROUND);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public void onRegisterOreDict() {
		OreDictUtils.register(this, "stairs");
		OreDictUtils.register(this, "stairs", "mud", "bricks");
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}

	@NotNull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}


}
