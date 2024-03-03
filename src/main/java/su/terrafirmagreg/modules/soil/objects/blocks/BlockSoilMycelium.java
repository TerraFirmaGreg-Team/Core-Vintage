package su.terrafirmagreg.modules.soil.objects.blocks;


import lombok.Getter;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariants;
import su.terrafirmagreg.modules.soil.data.ItemsSoil;

import java.util.Random;

import static su.terrafirmagreg.api.models.Blockstates.*;

@Getter
public class BlockSoilMycelium extends BlockMycelium implements ISoilBlock {

	private final SoilBlockVariant blockVariant;
	private final SoilType type;

	public BlockSoilMycelium(SoilBlockVariant blockVariant, SoilType type) {

		this.blockVariant = blockVariant;
		this.type = type;

		FallingBlockManager.registerFallable(this, blockVariant.getSpecification());


		setDefaultState(this.blockState.getBaseState()
				.withProperty(NORTH, Boolean.FALSE)
				.withProperty(EAST, Boolean.FALSE)
				.withProperty(SOUTH, Boolean.FALSE)
				.withProperty(WEST, Boolean.FALSE)
				.withProperty(SNOWY, Boolean.FALSE));


		//DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}

	@NotNull
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, @NotNull BlockPos pos) {
		pos = pos.add(0, -1, 0);
		Block blockUp = world.getBlockState(pos.up()).getBlock();
		return state
				.withProperty(NORTH, world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockSoilMycelium)
				.withProperty(EAST, world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockSoilMycelium)
				.withProperty(SOUTH, world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockSoilMycelium)
				.withProperty(WEST, world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockSoilMycelium)
				.withProperty(SNOWY, blockUp == Blocks.SNOW || blockUp == Blocks.SNOW_LAYER);
	}

	@NotNull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, SNOWY);
	}


	@NotNull
	@Override
	public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
		return ItemsSoil.getItem(SoilItemVariants.PILE, type);
	}

	@NotNull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
