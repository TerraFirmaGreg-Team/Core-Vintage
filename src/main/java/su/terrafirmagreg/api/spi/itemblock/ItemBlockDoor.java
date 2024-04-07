package su.terrafirmagreg.api.spi.itemblock;

import su.terrafirmagreg.api.spi.block.BlockDoorBase;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;

public class ItemBlockDoor extends ItemBlockBase {

    public ItemBlockDoor(BlockDoorBase blockDoor) {
        super(blockDoor);

    }

    /**
     * Скопировано из ItemDoor.class
     */
    public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
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
        boolean flag2 = worldIn.isBlockPowered(pos) ||
                worldIn.isBlockPowered(topDoorPos);

        IBlockState doorState = door.getDefaultState()
                .withProperty(BlockDoor.FACING, facing)
                .withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT)
                .withProperty(BlockDoor.POWERED, flag2)
                .withProperty(BlockDoor.OPEN, flag2);

        worldIn.setBlockState(pos, doorState.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
        worldIn.setBlockState(topDoorPos, doorState.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
        worldIn.notifyNeighborsOfStateChange(pos, door, false);
        worldIn.notifyNeighborsOfStateChange(topDoorPos, door, false);
    }

    @NotNull
    @Override
    public Size getSize(@NotNull ItemStack stack) {
        return Size.VERY_LARGE;
    }

    @NotNull
    @Override
    public Weight getWeight(@NotNull ItemStack stack) {
        return Weight.HEAVY;
    }

    /**
     * Скопировано из ItemDoor.class
     */
    @Override
    public @NotNull EnumActionResult onItemUse(@NotNull EntityPlayer player, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumHand hand,
                                               @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) return EnumActionResult.FAIL;
        else {
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
                placeDoor(worldIn, pos, enumfacing, this.block, flag);

                SoundType soundtype = bottomDoorBlock.getSoundType(bottomDoorState, worldIn, pos, player);

                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F,
                        soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            } else {
                return EnumActionResult.FAIL;
            }
        }
    }
}
