package net.dries007.tfc.compat.gregtech.items.tools.behaviors;

import gregtech.api.items.toolitem.behavior.IToolBehavior;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PropickBehavior implements IToolBehavior {

    public static final PropickBehavior INSTANCE = new PropickBehavior();

    protected PropickBehavior() {/**/}

    @Override
    public void addBehaviorNBT(@Nonnull ItemStack stack, @Nonnull NBTTagCompound tag) {
        tag.setBoolean(TFGToolHelper.CHISEL_KEY, true);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flag) {
        tooltip.add(I18n.format("item.gt.tool.behavior.propick"));
    }
}
