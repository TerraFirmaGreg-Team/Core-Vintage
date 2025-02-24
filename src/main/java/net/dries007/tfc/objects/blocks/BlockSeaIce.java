package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.dries007.tfc.Constants;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockSeaIce extends BlockIceTFC {


  public BlockSeaIce() {
    super(FluidsCore.SALT_WATER.get());

  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    EntityPlayer player = harvesters.get();
    if (player != null) {
      var tool = player.getHeldItemMainhand();
      if (OreDictionaryHelper.doesStackMatchOre(tool, "iceSaw")) {
        drops.clear();
        drops.add(new ItemStack(ItemsCore.ICE_SHARD.get(), 2 + Constants.RNG.nextInt(4)));
      } else {
        super.getDrops(drops, world, pos, state, fortune);
      }
    }

  }
}
