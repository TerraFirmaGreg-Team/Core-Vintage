package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.modules.worldgen.classic.objects.generator.GeneratorCinnamon;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;
import net.dries007.tfc.types.TreesTFCF;

import java.util.Random;

public class BlockCinnamonSapling extends BlockSaplingTFC {

  public BlockCinnamonSapling() {
    super(TreesTFCF.CINNAMON_TREE);
  }

  @Override
  public void grow(World world, Random rand, BlockPos pos, IBlockState blockState) {
    GeneratorCinnamon.generateCinnamon(world, rand, pos, false);
  }
}
