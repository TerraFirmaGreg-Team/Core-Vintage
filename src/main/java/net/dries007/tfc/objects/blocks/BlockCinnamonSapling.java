package net.dries007.tfc.objects.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.firmalife.init.PlantsFL;
import net.dries007.firmalife.world.WorldgenCinnamon;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BlockCinnamonSapling extends BlockSaplingTFC {

  public BlockCinnamonSapling() {
    super(PlantsFL.CINNAMON_TREE);
  }

  @Override
  public void grow(World world, Random rand, BlockPos pos, IBlockState blockState) {
    WorldgenCinnamon.generateCinnamon(world, rand, pos, false);
  }
}
