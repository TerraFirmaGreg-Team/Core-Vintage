package net.dries007.tfc.objects.items.groundcover;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.objects.blocks.groundcover.BlockSurfaceRock;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ItemRockTFCF extends ItemBlockTFC {

    public ItemRockTFCF(BlockSurfaceRock block) {
        super(block);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.LIGHT;
    }

    @NotNull
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @NotNull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && !player.isSneaking() && stack.getCount() > 1) {
            TFCGuiHandler.openGui(world, player.getPosition(), player, TFCGuiHandler.Type.KNAPPING_STONE);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
