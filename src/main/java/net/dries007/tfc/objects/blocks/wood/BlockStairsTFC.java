package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class BlockStairsTFC extends BlockStairs {
	private static final Map<Tree, BlockStairsTFC> WOOD_MAP = new HashMap<>();


	public BlockStairsTFC(Tree wood) {
		super(BlockPlanksTFC.get(wood).getDefaultState());
		if (WOOD_MAP.put(wood, this) != null) {
			throw new IllegalStateException("There can only be one.");
		}

		Block baseBlock = BlockPlanksTFC.get(wood);
		//noinspection ConstantConditions
		setHarvestLevel(baseBlock.getHarvestTool(baseBlock.getDefaultState()), baseBlock.getHarvestLevel(baseBlock.getDefaultState()));
		useNeighborBrightness = true;

		OreDictionaryHelper.register(this, "stair");
		OreDictionaryHelper.register(this, "stair", "wood");
		OreDictionaryHelper.register(this, "stair", "wood", wood);

		Blocks.FIRE.setFireInfo(this, 5, 20);
	}

	public static BlockStairsTFC get(Tree wood) {
		return WOOD_MAP.get(wood);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		// Prevents cobble stairs from falling
	}

	@Override
	public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
		// Prevents chiseled smooth stone stairs from collapsing
	}

	@Override
	public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, IBlockState state) {
		// Prevents cobble stairs from falling
	}
}
