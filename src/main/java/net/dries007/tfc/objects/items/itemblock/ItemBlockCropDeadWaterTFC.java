package net.dries007.tfc.objects.items.itemblock;

import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.world.classic.ChunkGenTFC;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemBlockCropDeadWaterTFC extends ItemBlockTFC {

  protected final BlockCropDead block;

  public ItemBlockCropDeadWaterTFC(BlockCropDead block) {
    super(block);
    this.block = block;
  }

  @Nonnull
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
    if (raytraceresult == null) {
      return new ActionResult(EnumActionResult.PASS, itemstack);
    } else {
      if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
        BlockPos blockpos = raytraceresult.getBlockPos();
        if (!worldIn.isBlockModifiable(playerIn, blockpos)
            || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
          return new ActionResult(EnumActionResult.FAIL, itemstack);
        }

        BlockPos blockpos1 = blockpos.up();
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        if (iblockstate.getMaterial() == Material.WATER && (Integer) iblockstate.getValue(BlockLiquid.LEVEL) == 0 && worldIn.isAirBlock(blockpos1)
            && iblockstate == ChunkGenTFC.FRESH_WATER) {
          BlockSnapshot blocksnapshot = BlockSnapshot.getBlockSnapshot(worldIn, blockpos1);
          worldIn.setBlockState(blockpos1, this.block.getDefaultState());
          if (ForgeEventFactory.onPlayerBlockPlace(playerIn, blocksnapshot, EnumFacing.UP, handIn).isCanceled()) {
            blocksnapshot.restore(true, false);
            return new ActionResult(EnumActionResult.FAIL, itemstack);
          }

          worldIn.setBlockState(blockpos1, this.block.getDefaultState(), 11);
          if (playerIn instanceof EntityPlayerMP) {
            CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) playerIn, blockpos1, itemstack);
          }

          if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
          }

          playerIn.addStat(StatList.getObjectUseStats(this));
          worldIn.playSound(playerIn, blockpos, SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
          return new ActionResult(EnumActionResult.SUCCESS, itemstack);
        }
      }

      return new ActionResult(EnumActionResult.FAIL, itemstack);
    }
  }
}
