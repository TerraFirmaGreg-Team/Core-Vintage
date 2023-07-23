/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.top.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.TerraFirmaCraft;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.compat.top.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.devices.BlockBlastFurnace;
import net.dries007.tfc.objects.te.TEBlastFurnace;
import net.dries007.tfc.util.Helpers;

public class BlastFurnaceProvider implements IProbeInfoProvider
{
    @Override
    public String getID() {
        return TerraFirmaCraft.MOD_ID + ":blast_furnace";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData probeData) {
        var blastFurnace = Helpers.getTE(world, probeData.getPos(), TEBlastFurnace.class);
        if (blastFurnace != null)
        {
            int chinmey = BlockBlastFurnace.getChimneyLevels(blastFurnace.getWorld(), blastFurnace.getPos());
            if (chinmey > 0)
            {
                int maxItems = chinmey * 4;
                int oreStacks = blastFurnace.getOreStacks().size();
                int fuelStacks = blastFurnace.getFuelStacks().size();
                float temperature = blastFurnace.getTemperature();
                String heatTooltip = Heat.getTooltip(temperature);
                probeInfo.text(new TextComponentTranslation("waila.tfc.bloomery.ores", oreStacks, maxItems).getFormattedText());
                probeInfo.text(new TextComponentTranslation("waila.tfc.bloomery.fuel", fuelStacks, maxItems).getFormattedText());
                if (heatTooltip != null)
                {
                    probeInfo.text(heatTooltip);
                }
            }
            else
            {
                probeInfo.text(new TextComponentTranslation("waila.tfc.blast_furnace.not_formed").getFormattedText());
            }
        }

    }
}
