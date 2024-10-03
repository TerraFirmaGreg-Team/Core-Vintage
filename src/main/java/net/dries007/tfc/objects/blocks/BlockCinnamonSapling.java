package net.dries007.tfc.objects.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.eerussianguy.firmalife.world.WorldgenCinnamon;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;
import net.dries007.tfc.types.TreesTFCF;

import java.util.Random;

public class BlockCinnamonSapling extends BlockSaplingTFC {

  public BlockCinnamonSapling() {
    super(TreesTFCF.CINNAMON_TREE);
  }

  @Override
  public void grow(World world, Random rand, BlockPos pos, IBlockState blockState) {
    WorldgenCinnamon.generateCinnamon(world, rand, pos, false);
  }
}
