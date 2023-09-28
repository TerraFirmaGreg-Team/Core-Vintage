package net.dries007.tfc.module.devices.plugin.top.provider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.Tags;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.devices.common.tile.TEPitKiln;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class PitKilnProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return Tags.MOD_ID + ":pit_klin";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {

        var te = Helpers.getTE(world, iProbeHitData.getPos(), TEPitKiln.class);
        if (te != null) {
            boolean isLit = te.isLit();

            if (isLit) {
                long remainingTicks = ConfigTFC.Devices.PIT_KILN.ticks - (CalendarTFC.PLAYER_TIME.getTicks() - te.getLitTick());
                switch (ConfigTFC.Client.TOOLTIP.timeTooltipMode) {
                    case NONE:
                        break;
                    case TICKS:
                        iProbeInfo.text(new TextComponentTranslation("top.tfc.devices.ticks_remaining", remainingTicks).getFormattedText());
                        break;
                    case MINECRAFT_HOURS:
                        long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
                        iProbeInfo.text(new TextComponentTranslation("top.tfc.devices.hours_remaining", remainingHours).getFormattedText());
                        break;
                    case REAL_MINUTES:
                        long remainingMinutes = Math.round(remainingTicks / 1200.0f);
                        iProbeInfo.text(new TextComponentTranslation("top.tfc.devices.minutes_remaining", remainingMinutes).getFormattedText());
                        break;
                }
            } else {
                int straw = te.getStrawCount();
                int logs = te.getLogCount();
                if (straw == 8 && logs == 8) {
                    iProbeInfo.text(new TextComponentTranslation("top.tfc.pitkiln.unlit").getFormattedText());
                } else {
                    if (straw < 8) {
                        iProbeInfo.text(new TextComponentTranslation("top.tfc.pitkiln.straw", 8 - straw).getFormattedText());
                    }
                    if (logs < 8) {
                        iProbeInfo.text(new TextComponentTranslation("top.tfc.pitkiln.logs", 8 - logs).getFormattedText());
                    }
                }
            }
        }
    }
}
