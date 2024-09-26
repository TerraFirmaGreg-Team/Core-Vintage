package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.object.tile.TileIngotPile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.NotNull;

public class ItemIngot extends ItemMetal {

  public ItemIngot(Metal metal, Metal.ItemType type) {
    super(metal, type);
  }

  @Override
  @NotNull
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (player.isSneaking() && ConfigTFC.General.OVERRIDES.enableIngotPiles) {
      ItemStack stack = player.getHeldItem(hand);
      ItemIngot item = (ItemIngot) stack.getItem();
      // Placing an ingot pile erases data, and since I really don't want to rewrite all of this, let's be sufficient with this for now
      // todo: rewrite ingot piles. They should store inventory, allow multiple ingots per pile, and be placed on event handler.
      if (!ItemStack.areItemStacksEqual(new ItemStack(item, stack.getCount()), stack)) {
        return EnumActionResult.FAIL;
      }
      if (worldIn.getBlockState(pos).getBlock() != BlocksCore.INGOT_PILE) {
        if (facing == EnumFacing.UP && worldIn.getBlockState(pos).isSideSolid(worldIn, pos, EnumFacing.UP)) {
          BlockPos up = pos.up();
          if (worldIn.mayPlace(BlocksCore.INGOT_PILE, up, false, EnumFacing.UP, null)) {
            if (!worldIn.isRemote) {
              worldIn.setBlockState(up, BlocksCore.INGOT_PILE.getDefaultState());
              var tile = TileUtils.getTile(worldIn, up, TileIngotPile.class);
              if (tile != null) {
                tile.setMetal(item.metal);
                tile.setCount(1);
              }
              worldIn.playSound(null, up, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.3F, 1.5F);
              stack.shrink(1);
              player.setHeldItem(hand, stack);
            }
            return EnumActionResult.SUCCESS;
          }
        }
      } else {
        // Place an ingot pile on top of the existing one
        BlockPos posTop = pos.down();
        IBlockState stateTop;
        do {
          posTop = posTop.up();
          stateTop = worldIn.getBlockState(posTop);
          if (stateTop.getBlock() == BlocksCore.INGOT_PILE) {
            var tile = TileUtils.getTile(worldIn, posTop, TileIngotPile.class);
            if (tile != null && tile.getCount() < 64 && (tile.getMetal() == item.metal) &&
                worldIn.checkNoEntityCollision(new AxisAlignedBB(0, 0, 0, 1, (1 + tile.getCount()) / 64d, 1).offset(posTop))) {
              tile.setCount(tile.getCount() + 1);
              worldIn.playSound(null, posTop, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.3F, 1.5F);
              stack.shrink(1);
              player.setHeldItem(hand, stack);
              return EnumActionResult.SUCCESS;
            }
          } else if (stateTop.getBlock()
                             .isReplaceable(worldIn, posTop) && worldIn.mayPlace(BlocksCore.INGOT_PILE, posTop, false, EnumFacing.UP, null) &&
                     worldIn.getBlockState(posTop.down())
                            .isSideSolid(worldIn, posTop.down(), EnumFacing.UP)) {
            worldIn.setBlockState(posTop, BlocksCore.INGOT_PILE.getDefaultState());
            var tile = TileUtils.getTile(worldIn, posTop, TileIngotPile.class);
            if (tile != null) {
              tile.setMetal(item.metal);
              tile.setCount(1);
            }
            worldIn.playSound(null, posTop, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.3F, 1.5F);
            stack.shrink(1);
            player.setHeldItem(hand, stack);
            return EnumActionResult.SUCCESS;
          } else {
            return EnumActionResult.FAIL;
          }

        } while (posTop.getY() <= 256);
      }
      return EnumActionResult.FAIL;
    }
    return EnumActionResult.PASS;
  }
}
