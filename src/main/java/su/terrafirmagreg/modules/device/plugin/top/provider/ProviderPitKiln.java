package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.object.block.BlockPitKiln;
import su.terrafirmagreg.modules.device.object.tile.TilePitKiln;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;

import java.util.ArrayList;
import java.util.List;

public class ProviderPitKiln implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.id("device.pit_kiln");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockPitKiln) {
      TileUtils.getTile(world, pos, TilePitKiln.class).ifPresent(tile -> {

        List<String> currentTooltip = new ArrayList<>();

        if (tile.isLit()) {
          long remainingTicks = ConfigDevice.BLOCK.PIT_KILN.ticks - (Calendar.PLAYER_TIME.getTicks()
                                                                     - tile.getLitTick());
          long remainingMinutes = Math.round(remainingTicks / 1200.0f);
          long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
          switch (ConfigTFC.Client.TOOLTIP.timeTooltipMode) {
            case NONE:
              break;
            case TICKS:
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.ticks_remaining"),
                                             remainingTicks).getFormattedText());
              break;
            case MINECRAFT_HOURS:
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.hours_remaining"),
                                             remainingHours).getFormattedText());
              break;
            case REAL_MINUTES:
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.minutes_remaining"),
                                             remainingMinutes).getFormattedText());
              break;
          }
        } else {
          int straw = tile.getStrawCount();
          int logs = tile.getLogCount();
          if (straw == 8 && logs == 8) {
            currentTooltip.add(new TextComponentTranslation(
              ModUtils.localize("top", "devices.pitkiln.unlit")).getFormattedText());
          } else {
            if (straw < 8) {
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.pitkiln.straw"),
                                             8 - straw).getFormattedText());
            }
            if (logs < 8) {
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.pitkiln.logs"),
                                             8 - logs).getFormattedText());
            }
          }
        }

        for (String string : currentTooltip) {
          info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
              .text(string);
        }
      });
    }
  }
}
