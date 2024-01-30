package su.terrafirmagreg.core.mixin.tfc.objects.blocks;

import gregtech.api.items.toolitem.ToolHelper;
import gregtech.common.items.ToolItems;
import net.dries007.tfc.objects.blocks.BlockPlacedHide;
import net.dries007.tfc.objects.te.TEPlacedHide;
import net.dries007.tfc.util.Helpers;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import su.terrafirmagreg.core.mixin.tfc.ICalculatePointInvoker;

@Mixin(value = BlockPlacedHide.class, remap = false)
public class BlockPlacedHideMixin extends Block {

    public BlockPlacedHideMixin(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    /**
     * @author SpeeeDCraft
     * @reason Allow to use GT knife on TFC skins
     */
    @Overwrite
    public boolean func_180639_a(@NotNull World worldIn, @NotNull BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() == ToolItems.KNIFE) {
            if (!worldIn.isRemote) {
                // Account for the distance between the hitbox and where the hide is rendered
                Vec3d point = ICalculatePointInvoker.invokeCalculatePoint(playerIn.getLookVec(), new Vec3d(hitX, hitY, hitZ));
                ToolHelper.damageItem(stack, playerIn);
                TEPlacedHide tile = Helpers.getTE(worldIn, pos, TEPlacedHide.class);
                if (tile != null) {
                    tile.onClicked((float) point.x, (float) point.z);
                }
            }
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
