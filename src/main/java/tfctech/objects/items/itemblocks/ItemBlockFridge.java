package tfctech.objects.items.itemblocks;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tfctech.objects.blocks.devices.BlockFridge;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemBlockFridge extends ItemBlockTFC {
    public ItemBlockFridge(Block block) {
        super(block);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.HUGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.MEDIUM;
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack) {
        return false;
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.getBlockState(pos).getMaterial().isReplaceable()) {
            pos = pos.offset(facing);
        }
        if (!worldIn.getBlockState(pos).getMaterial().isReplaceable() || !worldIn.getBlockState(pos.up()).getMaterial().isReplaceable()) {
            return EnumActionResult.PASS;
        }
        ItemStack stack = player.getHeldItem(hand);
        if (player.canPlayerEdit(pos.up(), facing, stack) && player.canPlayerEdit(pos, facing, stack)) {
            if (!worldIn.isRemote) {
                stack.shrink(1);
                IBlockState lowerState = this.block.getDefaultState().withProperty(BlockFridge.FACING, player.getHorizontalFacing().getOpposite()).withProperty(BlockFridge.UPPER, false);
                IBlockState upperState = this.block.getDefaultState().withProperty(BlockFridge.FACING, player.getHorizontalFacing().getOpposite()).withProperty(BlockFridge.UPPER, true);
                worldIn.setBlockState(pos, lowerState);
                worldIn.setBlockState(pos.up(), upperState);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
