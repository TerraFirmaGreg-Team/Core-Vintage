package su.terrafirmagreg.modules.soil.objects.blocks;


import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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

	@Override
	public void getSubBlocks(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, 0));
		items.add(new ItemStack(this, 1, 1));
	}

	@Override
	public int getMetaFromState(@NotNull IBlockState state) {
		return state.getValue(MOSSY) ? 1 : 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(MOSSY, meta == 1);
	}


	@NotNull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, MOSSY);
	}
}
