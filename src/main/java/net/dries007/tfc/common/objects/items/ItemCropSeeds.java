package net.dries007.tfc.common.objects.items;

import net.dries007.tfc.api.types.crop.ICropItem;
import net.dries007.tfc.api.types.crop.type.CropType;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.blocks.crop.BlockCropGrowing;
import net.dries007.tfc.common.objects.blocks.soil.BlockSoilFarmland;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dries007.tfc.api.types.crop.variant.block.CropBlockVariants.GROWING;

public class ItemCropSeeds extends Item implements IPlantable, ICropItem {
    private final CropType type;

    public ItemCropSeeds(CropType type) {
        this.type = type;

        setRegistryName(getRegistryLocation("seeds"));
        setTranslationKey(getTranslationName("seeds"));
        setCreativeTab(CreativeTabsTFC.FLORA);
    }

    @Nonnull
    @Override
    public CropType getType() {
        return type;
    }

    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) &&
                state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) &&
                worldIn.isAirBlock(pos.up()) && state.getBlock() instanceof BlockSoilFarmland) {

            worldIn.setBlockState(pos.up(), TFCBlocks.getCropBlock(GROWING, type).getDefaultState());

            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), itemstack);
            }

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        } else {

            return EnumActionResult.FAIL;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockCropGrowing && ((BlockCropGrowing) state.getBlock()).getType() == this.type) {
            return state;
        }
        return TFCBlocks.getCropBlock(GROWING, type).getDefaultState();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        type.addInfo(stack, worldIn, tooltip, flagIn);
    }
}
