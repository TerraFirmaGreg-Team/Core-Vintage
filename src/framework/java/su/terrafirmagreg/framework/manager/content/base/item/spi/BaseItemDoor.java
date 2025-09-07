package su.terrafirmagreg.framework.manager.content.base.item.spi;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
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

import lombok.Getter;

@Getter
public class BaseItemDoor extends BaseItemBlock {


  public BaseItemDoor(Block block) {
    super(block);

  }

  public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
    BlockPos blockpos = pos.offset(facing.rotateY());
    BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
    int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
    int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
    boolean flag = worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door;
    boolean flag1 = worldIn.getBlockState(blockpos).getBlock() == door || worldIn.getBlockState(blockpos.up()).getBlock() == door;

    if ((!flag || flag1) && j <= i) {
      if (flag1 && !flag || j < i) {
        isRightHinge = false;
      }
    } else {
      isRightHinge = true;
    }

    BlockPos blockpos2 = pos.up();
    boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos2);
    IBlockState iblockstate = door.getDefaultState().withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT)
      .withProperty(BlockDoor.POWERED, Boolean.valueOf(flag2)).withProperty(BlockDoor.OPEN, Boolean.valueOf(flag2));
    worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
    worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
    worldIn.notifyNeighborsOfStateChange(pos, door, false);
    worldIn.notifyNeighborsOfStateChange(blockpos2, door, false);
  }

  @Override
  public String getTranslationKey() {

    return block.getTranslationKey();
  }

  @Override
  public String getTranslationKey(ItemStack stack) {

    return getTranslationKey();
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
