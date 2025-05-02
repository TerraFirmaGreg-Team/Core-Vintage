package net.dries007.tfc.objects.items.food;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityProviderFood;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;

public class ItemBlockRot extends ItemBlockTFC {

  public ItemBlockRot(Block b) {
    super(b);
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
    return new CapabilityProviderFood();
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    long foodCreationDate = Long.MIN_VALUE;
    if (!worldIn.isRemote) {
      ItemStack stack = player.getHeldItem(hand);
      CapabilityProviderFood handler = (CapabilityProviderFood) stack.getCapability(CapabilityFood.CAPABILITY, null);
      foodCreationDate = handler.getCreationDate();
    }
    EnumActionResult result = super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    if (!worldIn.isRemote && result == EnumActionResult.SUCCESS) {
      TETickCounter te = Helpers.getTE(worldIn, pos.offset(facing), TETickCounter.class);
      if (te != null) {
        long currentTime = Calendar.PLAYER_TIME.getTicks();
        te.resetCounter(); //te counter is at currentTime
        te.reduceCounter(foodCreationDate - currentTime); //teCounter is now at foodCrationDate
      }
    }
    return result;
  }
}
