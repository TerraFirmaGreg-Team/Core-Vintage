package su.terrafirmagreg.modules.soil.objects.blocks;


import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.Blockstates;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import static su.terrafirmagreg.api.models.Blockstates.MOSSY;

public class BlockSoilMudBrick extends BlockSoil {

	public BlockSoilMudBrick(SoilBlockVariant blockVariant, SoilType type) {
		super(blockVariant, type);

		setDefaultState(blockState.getBaseState().withProperty(Blockstates.MOSSY, Boolean.FALSE));
	}


	@NotNull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, MOSSY);
	}

	@NotNull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
