/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.dries007.tfc.Constants;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.sharkbark.cellars.init.ModItems.SEA_ICE_SHARD;

@ParametersAreNonnullByDefault
public class BlockSeaIce extends BlockIceTFC {


  public BlockSeaIce() {
    super(FluidsTFC.SALT_WATER.get());

  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    EntityPlayer player = harvesters.get();
    if (player != null) {
      var tool = player.getHeldItemMainhand();
      if (OreDictionaryHelper.doesStackMatchOre(tool, "iceSaw")) {
        drops.clear();
        drops.add(new ItemStack(SEA_ICE_SHARD, 2 + Constants.RNG.nextInt(4)));
      } else {
        super.getDrops(drops, world, pos, state, fortune);
      }
    }

  }
}
