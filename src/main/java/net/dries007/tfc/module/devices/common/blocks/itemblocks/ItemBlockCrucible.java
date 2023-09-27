package net.dries007.tfc.module.devices.common.blocks.itemblocks;

import net.dries007.tfc.Tags;
import net.dries007.tfc.module.api.common.block.itemblocks.ItemBlockBase;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.util.Alloy;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class ItemBlockCrucible extends ItemBlockBase {
    public ItemBlockCrucible(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        var nbt = stack.getTagCompound();

        if (nbt != null) {
            var alloy = new Alloy(ConfigTFC.Devices.CRUCIBLE.tank);

            alloy.deserializeNBT(nbt.getCompoundTag("alloy"));
            tooltip.add(I18n.format(Tags.MOD_ID + ".tooltip.crucible_alloy", alloy.getAmount(), alloy.getResult().getLocalizedName()));
        }
    }
}
