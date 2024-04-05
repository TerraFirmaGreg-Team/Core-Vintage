package net.dries007.tfc.objects.blocks;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.wood.BlockPlanksTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.BlockUtils;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


public class BlockStairsTFC extends BlockStairs {
	private static final Map<Rock, EnumMap<Rock.Type, BlockStairsTFC>> ROCK_TABLE = new HashMap<>();
	private static final Map<Tree, BlockStairsTFC> WOOD_MAP = new HashMap<>();

	public BlockStairsTFC(Rock rock, Rock.Type type) {
		super(BlockRockVariant.get(rock, type).getDefaultState());

		if (!ROCK_TABLE.containsKey(rock))
			ROCK_TABLE.put(rock, new EnumMap<>(Rock.Type.class));
		ROCK_TABLE.get(rock).put(type, this);

		Block baseBlock = BlockRockVariant.get(rock, type);
		//noinspection ConstantConditions
		setHarvestLevel(baseBlock.getHarvestTool(baseBlock.getDefaultState()), baseBlock.getHarvestLevel(baseBlock.getDefaultState()));
		useNeighborBrightness = true;
		OreDictionaryHelper.register(this, "stair");
		OreDictionaryHelper.registerRockType(this, type, "stair");
	}

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

		BlockUtils.setFireInfo(this, 5, 20);
	}

	public static BlockStairsTFC get(Rock rock, Rock.Type type) {
		return ROCK_TABLE.get(rock).get(type);
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
	public void onBlockAdded(@NotNull World worldIn, @NotNull BlockPos pos, IBlockState state) {
		// Prevents cobble stairs from falling
	}
}
