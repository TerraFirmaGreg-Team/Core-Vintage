package su.terrafirmagreg.modules.wood.objects.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodDoor;

public class ItemBlockWoodDoor extends ItemBlock implements IItemSize {

    public ItemBlockWoodDoor(BlockWoodDoor blockWoodDoor) {
        super(blockWoodDoor);

        //OreDictionaryHelper.register(this, "door", "wood");
        //OreDictionaryHelper.register(this, "door", "wood", blockWoodDoor.getType().toString());
    }

    /**
     * Скопировано из ItemDoor.class
     */
    public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
        BlockPos blockpos = pos.offset(facing.rotateY());
        BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
        int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up())
                .isNormalCube() ? 1 : 0);
        int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up())
                .isNormalCube() ? 1 : 0);
        boolean flag = worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up())
                .getBlock() == door;
        boolean flag1 = worldIn.getBlockState(blockpos).getBlock() == door || worldIn.getBlockState(blockpos.up())
                .getBlock() == door;

        if ((!flag || flag1) && j <= i) {
            if (flag1 && !flag || j < i) {
                isRightHinge = false;
            }
        } else {
            isRightHinge = true;
        }

        BlockPos blockpos2 = pos.up();
        boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos2);
        IBlockState iblockstate = door.getDefaultState()
                .withProperty(BlockDoor.FACING, facing)
                .withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT)
                .withProperty(BlockDoor.POWERED, Boolean.valueOf(flag2))
                .withProperty(BlockDoor.OPEN, Boolean.valueOf(flag2));
        worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
        worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
        worldIn.notifyNeighborsOfStateChange(pos, door, false);
        worldIn.notifyNeighborsOfStateChange(blockpos2, door, false);
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
    @NotNull
    @Override
    public EnumActionResult onItemUse(@NotNull EntityPlayer player, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos)) {
                pos = pos.offset(facing);
            }

            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn, pos)) {
                EnumFacing enumfacing = EnumFacing.fromAngle(player.rotationYaw);
                int i = enumfacing.getXOffset();
                int j = enumfacing.getZOffset();
                boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
                placeDoor(worldIn, pos, enumfacing, this.block, flag);
                SoundType soundtype = worldIn.getBlockState(pos)
                        .getBlock()
                        .getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            } else {
                return EnumActionResult.FAIL;
            }
        }
    }
}
