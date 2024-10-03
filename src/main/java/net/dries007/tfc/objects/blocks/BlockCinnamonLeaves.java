package net.dries007.tfc.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.eerussianguy.firmalife.registry.BlocksFL;
import com.google.common.collect.ImmutableList;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.types.TreesTFCF;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class BlockCinnamonLeaves extends BlockLeavesTFC {

  public BlockCinnamonLeaves() {
    // LOOK AWAY!!!!
    // Is this necessary? I don't know
    super(TreesTFCF.CINNAMON_TREE);
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World world, BlockPos pos, @Nullable Block blockIn, @Nullable BlockPos fromPos) {
    for (EnumFacing d : EnumFacing.VALUES) {
      for (int i = 1; i < 4; i++) {
        Block offsetBlock = world.getBlockState(pos.offset(d, i)).getBlock();
        if (offsetBlock instanceof BlockCinnamonLog) {
          return;
        }
      }
    }
    world.destroyBlock(pos, true);
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    worldIn.scheduleUpdate(pos, state.getBlock(), 1);
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    int chance = 10; // this should be config
    if (RANDOM.nextInt(101) < chance) {
      drops.add(new ItemStack(BlocksFL.CINNAMON_SAPLING));
    }
  }

  @Override
  @NotNull
  public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    return ImmutableList.of(ItemStack.EMPTY);
  }
}
