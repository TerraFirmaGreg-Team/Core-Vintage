package net.dries007.tfc.module.devices.plugin.top.provider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.devices.common.blocks.BlockBlastFurnace;
import net.dries007.tfc.module.devices.common.tile.TEBlastFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlastFurnaceProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return Tags.MOD_ID + ":blast_furnace";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData probeData) {
        var blastFurnace = Helpers.getTE(world, probeData.getPos(), TEBlastFurnace.class);
        if (blastFurnace != null) {
            int chinmey = BlockBlastFurnace.getChimneyLevels(blastFurnace.getWorld(), blastFurnace.getPos());
            if (chinmey > 0) {
                int maxItems = chinmey * 4;
                int oreStacks = blastFurnace.getOreStacks().size();
                int fuelStacks = blastFurnace.getFuelStacks().size();
                float temperature = blastFurnace.getTemperature();
                String heatTooltip = Heat.getTooltip(temperature);
                probeInfo.text(new TextComponentTranslation("top.tfc.bloomery.ores", oreStacks, maxItems).getFormattedText());
                probeInfo.text(new TextComponentTranslation("top.tfc.bloomery.fuel", fuelStacks, maxItems).getFormattedText());
                if (heatTooltip != null) {
                    probeInfo.text(heatTooltip);
                }
            } else {
                probeInfo.text(new TextComponentTranslation("top.tfc.blast_furnace.not_formed").getFormattedText());
            }
        }

    }
}
