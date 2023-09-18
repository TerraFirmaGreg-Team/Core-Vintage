package net.dries007.tfc.module.plant.common.blocks.itemblocks;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.module.plant.api.type.PlantType;
import net.dries007.tfc.module.plant.common.blocks.BlockPlantPot;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemBlockPlant extends ItemBlockTFC {
    private final PlantType plant;

    public ItemBlockPlant(Block block, PlantType plant) {
        super(block);
        this.plant = plant;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && world.getBlockState(pos).getBlock() instanceof BlockFlowerPot) {
            TileEntityFlowerPot te = Helpers.getTE(world, pos, TileEntityFlowerPot.class);
            if (te == null || te.getFlowerItemStack().isEmpty()) {
                world.setBlockState(pos, BlockPlantPot.get(plant).getDefaultState(), 3);
                player.getHeldItem(hand).shrink(1);
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
        return worldIn.getBlockState(pos).getBlock() instanceof BlockFlowerPot || super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
    }
}
