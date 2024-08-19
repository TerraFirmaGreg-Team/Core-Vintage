package su.terrafirmagreg.modules.device.objects.itemblocks;

import su.terrafirmagreg.api.base.item.BaseItemBlock;
import su.terrafirmagreg.modules.device.client.render.TEISRFridge;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import static su.terrafirmagreg.api.data.Blockstates.HORIZONTAL;
import static su.terrafirmagreg.api.data.Blockstates.UPPER;

public class ItemBlockFridge extends BaseItemBlock {

    public ItemBlockFridge(Block block) {
        super(block);

        setTileEntityItemStackRenderer(new TEISRFridge());
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
                IBlockState lowerState = this.block.getDefaultState()
                        .withProperty(HORIZONTAL, player.getHorizontalFacing().getOpposite())
                        .withProperty(UPPER, false);
                IBlockState upperState = this.block.getDefaultState()
                        .withProperty(HORIZONTAL, player.getHorizontalFacing().getOpposite())
                        .withProperty(UPPER, true);
                worldIn.setBlockState(pos, lowerState);
                worldIn.setBlockState(pos.up(), upperState);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
