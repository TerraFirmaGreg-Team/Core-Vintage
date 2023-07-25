/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.top.providers;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.objects.blocks.BlockTorchTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TorchProvider implements IProbeInfoProvider
{
    @Override
    public String getID() {
        return TerraFirmaCraft.MOD_ID + ":torch";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
        var te = Helpers.getTE(world, iProbeHitData.getPos(), TETickCounter.class);

        if (te != null && iBlockState.getBlock() instanceof BlockTorchTFC)
        {
            int secValue = (int) ((ConfigTFC.General.OVERRIDES.torchTime - te.getTicksSinceUpdate()) / 20);

            int minValue = secValue / 60;

            secValue = secValue % 60;

            int hourValue = minValue / 60;

            minValue = minValue % 60;

            probeInfo.text(String.format("%s %s", ConfigTFC.General.OVERRIDES.torchTime, te.getTicksSinceUpdate()));
            /*
            if (secValue >= 0)
                probeInfo.text(String.format(TextFormatting.GOLD + "Burn time left: %sh %sm %ss", hourValue, minValue, secValue));
            else
                probeInfo.text(TextFormatting.RED + "This torch has gone out!");*/
        }
    }
}
