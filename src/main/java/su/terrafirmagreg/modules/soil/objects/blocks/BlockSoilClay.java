package su.terrafirmagreg.modules.soil.objects.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import java.util.Random;

public class BlockSoilClay extends BlockSoil {

	public BlockSoilClay(SoilBlockVariant blockVariant, SoilType type) {
		super(blockVariant, type);

//        DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.DIRTLIKE);
	}

	@Override
	public int quantityDropped(@NotNull IBlockState state, int fortune, Random random) {
		return random.nextInt(4);
	}

	@NotNull
	@Override
	public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
		return Items.CLAY_BALL;
	}

	@NotNull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
