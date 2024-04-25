package tfctech.objects.items.itemblocks;

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
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import tfctech.objects.blocks.devices.BlockWireDrawBench;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ItemBlockWireDrawBench extends ItemBlockTFC {

    public ItemBlockWireDrawBench(Block block) {
        super(block);
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return Size.HUGE;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.MEDIUM;
    }

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return false;
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
                                      float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        } else {
            if (!worldIn.getBlockState(pos)
                    .getMaterial()
                    .isReplaceable() || !worldIn.getBlockState(pos.offset(player.getHorizontalFacing()))
                    .getMaterial()
                    .isReplaceable()) {
                pos = pos.up(); //try the above
            }
            if (!worldIn.getBlockState(pos)
                    .getMaterial()
                    .isReplaceable() || !worldIn.getBlockState(pos.offset(player.getHorizontalFacing()))
                    .getMaterial()
                    .isReplaceable()) {
                return EnumActionResult.PASS;
            }
            ItemStack stack = player.getHeldItem(hand);
            BlockPos upperPos = pos.offset(player.getHorizontalFacing());
            if (player.canPlayerEdit(upperPos, facing, stack) && player.canPlayerEdit(pos, facing, stack)) {
                if (!worldIn.isRemote) {
                    stack.shrink(1);
                    IBlockState lowerState = this.block.getDefaultState()
                            .withProperty(BlockWireDrawBench.FACING, player.getHorizontalFacing())
                            .withProperty(BlockWireDrawBench.UPPER, false);
                    IBlockState upperState = this.block.getDefaultState()
                            .withProperty(BlockWireDrawBench.FACING, player.getHorizontalFacing())
                            .withProperty(BlockWireDrawBench.UPPER, true);
                    worldIn.setBlockState(pos, lowerState);
                    worldIn.setBlockState(upperPos, upperState);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }
}
