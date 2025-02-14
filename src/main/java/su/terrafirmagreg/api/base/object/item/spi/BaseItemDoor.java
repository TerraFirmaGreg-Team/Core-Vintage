package su.terrafirmagreg.api.base.object.item.spi;

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

import static net.minecraft.block.BlockDoor.HALF;
import static net.minecraft.block.BlockDoor.HINGE;
import static su.terrafirmagreg.api.data.Properties.BoolProp.OPEN;
import static su.terrafirmagreg.api.data.Properties.BoolProp.POWERED;
import static su.terrafirmagreg.api.data.Properties.EnumProp.FACING;

@Getter
public class BaseItemDoor extends BaseItemBlock {

  public BaseItemDoor(Block block) {
    super(block);
  }

  /**
   * Скопировано из ItemDoor.class
   */
  public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door,
                               boolean isRightHinge) {
    BlockPos posYClockwise = pos.offset(facing.rotateY());
    BlockPos posYAntiClockwise = pos.offset(facing.rotateYCCW());

    int i = (worldIn.getBlockState(posYAntiClockwise).isNormalCube() ? 1 : 0) +
            (worldIn.getBlockState(posYAntiClockwise.up()).isNormalCube() ? 1 : 0);

    int j = (worldIn.getBlockState(posYClockwise).isNormalCube() ? 1 : 0) +
            (worldIn.getBlockState(posYClockwise.up()).isNormalCube() ? 1 : 0);

    boolean flag = worldIn.getBlockState(posYAntiClockwise).getBlock() == door ||
                   worldIn.getBlockState(posYAntiClockwise.up()).getBlock() == door;

    boolean flag1 = worldIn.getBlockState(posYClockwise).getBlock() == door ||
                    worldIn.getBlockState(posYClockwise.up()).getBlock() == door;

    if ((!flag || flag1) && j <= i) {
      if (flag1 && !flag || j < i) {
        isRightHinge = false;
      }
    } else {
      isRightHinge = true;
    }

    BlockPos topDoorPos = pos.up();
    boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(topDoorPos);

    IBlockState doorState = door.getDefaultState()
      .withProperty(FACING, facing)
      .withProperty(HINGE,
        isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT)
      .withProperty(POWERED, flag2)
      .withProperty(OPEN, flag2);

    worldIn.setBlockState(pos, doorState.withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER),
      2);
    worldIn.setBlockState(topDoorPos,
      doorState.withProperty(HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
    worldIn.notifyNeighborsOfStateChange(pos, door, false);
    worldIn.notifyNeighborsOfStateChange(topDoorPos, door, false);
  }

  /**
   * Скопировано из ItemDoor.class
   */
  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (facing != EnumFacing.UP) {
      return EnumActionResult.FAIL;
    } else {
      IBlockState bottomDoorState = worldIn.getBlockState(pos);
      Block bottomDoorBlock = bottomDoorState.getBlock();

      if (!bottomDoorBlock.isReplaceable(worldIn, pos)) {
        pos = pos.offset(facing);
      }

      ItemStack itemstack = player.getHeldItem(hand);

      if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn,
        pos)) {
        EnumFacing enumfacing = EnumFacing.fromAngle(player.rotationYaw);
        int i = enumfacing.getXOffset();
        int j = enumfacing.getZOffset();
        boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F
                       || j > 0 && hitX < 0.5F;
        placeDoor(worldIn, pos, enumfacing.getOpposite(), this.block,
          flag); // only line that we change

        SoundType soundtype = bottomDoorBlock.getSoundType(bottomDoorState, worldIn, pos, player);

        worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
          (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
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
    return def$initCapabilities(stack, nbt);
  }
}
