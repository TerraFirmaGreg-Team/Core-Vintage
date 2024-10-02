package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.data.Properties.IntProp.JARS;

@MethodsReturnNonnullByDefault
public class ItemBlockJar extends ItemBlockTFC {

  public ItemBlockJar(Block block) {
    super(block);
  }

  @Override
  @NotNull
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
                                    float hitZ) {
    IBlockState state = worldIn.getBlockState(pos);
    if (state.getBlock() == block) {
      if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
        int jars = state.getValue(JARS);
        if (jars < 4) {
          worldIn.setBlockState(pos, state.withProperty(JARS, jars + 1));
          player.getHeldItem(hand).shrink(1);
          return EnumActionResult.SUCCESS;
        }
      }
      return EnumActionResult.FAIL;
    }
    if (worldIn.getBlockState(pos).isSideSolid(worldIn, pos, EnumFacing.UP)) {
      return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    } else {
      return EnumActionResult.FAIL;
    }
  }

  @Override
  public ItemStack getContainerItem(ItemStack itemStack) {
    return new ItemStack(ItemsCore.JAR);
  }

  @Override
  public boolean hasContainerItem(ItemStack stack) {
    return true;
  }
}
