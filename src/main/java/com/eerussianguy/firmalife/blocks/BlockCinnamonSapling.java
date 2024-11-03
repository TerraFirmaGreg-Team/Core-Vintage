package com.eerussianguy.firmalife.blocks;

import com.eerussianguy.firmalife.init.PlantsFL;
import com.eerussianguy.firmalife.world.WorldgenCinnamon;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
