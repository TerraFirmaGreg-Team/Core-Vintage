package net.dries007.tfc.module.agriculture.plugin.top.provider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.core.objects.tiles.TETickCounter;
import net.dries007.tfc.module.agriculture.common.blocks.BlockBerryBush;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BerryBushProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return Tags.MOD_ID + ":berry_bush";
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData probeHitData) {
        var blockPos = probeHitData.getPos();
        var state = world.getBlockState(blockPos);

        if (state.getBlock() instanceof BlockBerryBush block) {
            if (block.getType().isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear()) && !state.getValue(BlockBerryBush.FRUITING)) {
                var temp = ClimateTFC.getActualTemp(world, blockPos);
                var rainfall = ChunkDataTFC.getRainfall(world, blockPos);

                var te = Helpers.getTE(world, blockPos, TETickCounter.class);

                if (te != null && block.getType().isValidForGrowth(temp, rainfall)) {
                    long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
                    // Don't show 100% since it still needs to check on randomTick to grow
                    float perc = Math.min(0.99F, hours / (block.getType().getGrowthTime() * (float) ConfigTFC.General.FOOD.berryBushGrowthTimeModifier)) * 100;
                    String growth = String.format("%d%%", Math.round(perc));
                    probeInfo.text(new TextComponentTranslation("top.tfc.crop.growth", growth).getFormattedText());
                } else {
                    probeInfo.text(new TextComponentTranslation("top.tfc.crop.not_growing").getFormattedText());
                }
            } else {
                probeInfo.text(new TextComponentTranslation("top.tfc.agriculture.harvesting_months").getFormattedText());
                for (Month month : Month.values()) {
                    if (block.getType().isHarvestMonth(month)) {
                        probeInfo.text(TerraFirmaCraft.getProxy().getMonthName(month, true));
                    }
                }
            }
        }
    }
}
