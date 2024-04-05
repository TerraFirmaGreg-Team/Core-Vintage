package tfcflorae.objects.blocks.wood.cinnamon;

import com.google.common.collect.ImmutableList;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.OreDictionaryHelper;

import java.util.List;
import java.util.Random;

public class BlockCeylonCinnamonLeaves extends BlockLeavesTFC {
	public BlockCeylonCinnamonLeaves() {
		super(TreesTFCF.CEYLON_CINNAMON_TREE);
		setSoundType(SoundType.PLANT);
		setDefaultState(blockState.getBaseState().withProperty(DECAYABLE, true));
		OreDictionaryHelper.register(this, "tree", "leaves");
		OreDictionaryHelper.register(this, "tree", "leaves", wood.getRegistryName().getPath());
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		int chance = 10;
		if (RANDOM.nextInt(101) < chance) {
			drops.add(new ItemStack(BlocksTFCF.CEYLON_CINNAMON_SAPLING));
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.scheduleUpdate(pos, state.getBlock(), 1);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World world, BlockPos pos, @Nullable Block blockIn, @Nullable BlockPos fromPos) {
		for (EnumFacing d : EnumFacing.VALUES) {
			for (int i = 0; i < 4; i++) {
				Block offsetBlock = world.getBlockState(pos.offset(d, i)).getBlock();
				if (offsetBlock instanceof BlockCeylonCinnamonLog)
					return;
			}
		}
		world.destroyBlock(pos, true);
	}

	@Override
	@NotNull
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return ImmutableList.of(ItemStack.EMPTY);
	}
}
