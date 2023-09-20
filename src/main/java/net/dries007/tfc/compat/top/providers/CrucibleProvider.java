package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.module.core.common.objects.tileentities.TECrucible;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CrucibleProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return Tags.MOD_ID + ":crucible";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        var blockPos = iProbeHitData.getPos();
        var crucible = Helpers.getTE(world, blockPos, TECrucible.class);
        if (crucible != null) {
            if (crucible.getAlloy().getAmount() > 0) {
                var material = crucible.getAlloyResult();
                iProbeInfo.text(new TextComponentTranslation("top.tfc.metal.output", crucible.getAlloy().getAmount(), material.getLocalizedName()).getFormattedText());
            }
            float temperature = crucible.getTemperature();
            String heatTooltip = Heat.getTooltip(temperature);
            if (heatTooltip != null) {
                iProbeInfo.text(heatTooltip);
            }
        }
    }
}
