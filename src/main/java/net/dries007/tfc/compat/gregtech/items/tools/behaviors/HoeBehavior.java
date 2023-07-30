package net.dries007.tfc.compat.gregtech.items.tools.behaviors;

import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.items.toolitem.behavior.IToolBehavior;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.types2.soil.util.ISoilTypeBlock;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class HoeBehavior implements IToolBehavior {

    public static final HoeBehavior INSTANCE = new HoeBehavior();

    protected HoeBehavior() {/**/}

    @Override
    @Nonnull
    public EnumActionResult onItemUse(@Nonnull EntityPlayer player, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        var hitBlock = worldIn.getBlockState(pos);

        if (hitBlock.getBlock() instanceof ISoilTypeBlock soilTypeBlock) {
            if (soilTypeBlock.getSoilVariant() == SoilVariant.DIRT || soilTypeBlock.getSoilVariant() == SoilVariant.GRASS) {
                worldIn.setBlockState(pos, TFCStorage.getSoilBlock(SoilVariant.FARMLAND, soilTypeBlock.getSoilType()).getDefaultState());
                ToolHelper.onActionDone(player, worldIn, hand);
                return EnumActionResult.SUCCESS;
            }

        }

        return EnumActionResult.FAIL;
    }

    @Override
    public void addBehaviorNBT(@Nonnull ItemStack stack, @Nonnull NBTTagCompound tag) {
        tag.setBoolean(TFGToolHelper.HOE_KEY, true);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flag) {
        tooltip.add(I18n.format("item.gt.tool.behavior.hoe"));
    }
}
