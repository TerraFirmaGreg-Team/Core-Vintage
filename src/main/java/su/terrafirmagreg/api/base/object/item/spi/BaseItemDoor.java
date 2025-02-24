package su.terrafirmagreg.api.base.object.item.spi;

import lombok.Getter;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import su.terrafirmagreg.api.base.object.item.api.IItemSettings;

@Getter
public class BaseItemDoor extends ItemDoor implements IItemSettings {

  protected final Settings settings;
  protected final Block block;

  public BaseItemDoor(Block block) {
    super(block);

    this.block = block;
    this.settings = Settings.of(block);
  }

  /**
   * Скопировано из ItemDoor.class
   */
  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (facing != EnumFacing.UP) {
      return EnumActionResult.FAIL;
    } else {
      IBlockState bottomDoorState = worldIn.getBlockState(pos);
      Block bottomDoorBlock = bottomDoorState.getBlock();

      if (!bottomDoorBlock.isReplaceable(worldIn, pos)) {
        pos = pos.offset(facing);
      }

      ItemStack itemstack = player.getHeldItem(hand);

      if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn, pos)) {
        EnumFacing enumfacing = EnumFacing.fromAngle(player.rotationYaw);
        int i = enumfacing.getXOffset();
        int j = enumfacing.getZOffset();
        boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
        placeDoor(worldIn, pos, enumfacing.getOpposite(), this.block, flag); // only line that we change

        SoundType soundtype = bottomDoorBlock.getSoundType(bottomDoorState, worldIn, pos, player);

        worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        itemstack.shrink(1);
        return EnumActionResult.SUCCESS;
      } else {
        return EnumActionResult.FAIL;
      }
    }
  }

  @Override
  public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return settings$initCapabilities(stack, nbt);
  }
}
