package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.objects.tileentities.TEQuern;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import static net.dries007.tfc.common.objects.tileentities.TEQuern.SLOT_HANDSTONE;

public class QuernProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return TerraFirmaCraft.MOD_ID + ":quern";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {

        var quern = Helpers.getTE(world, iProbeHitData.getPos(), TEQuern.class);
        IItemHandler handler;
        ItemStack handstone;

        if (quern != null && quern.hasHandstone() && (handler = quern.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) != null && !(handstone = handler.getStackInSlot(SLOT_HANDSTONE)).isEmpty()) {
            iProbeInfo.text(new TextComponentTranslation("waila.tfc.quern.handstone_durability", handstone.getItemDamage(), handstone.getMaxDamage()).getFormattedText());
        }
    }
}
