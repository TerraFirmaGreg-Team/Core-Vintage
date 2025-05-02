package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.base.plugin.top.provider.spi.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
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
import mcjty.theoneprobe.api.ProbeMode;

public class ProviderPitKiln extends BaseProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.pit_kiln");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockPitKiln) {
      TileUtils.getTile(world, pos, TilePitKiln.class).ifPresent(tile -> {
        var probeInfo = info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

        if (tile.isLit()) {
          long remainingTicks = ConfigDevice.BLOCK.PIT_KILN.ticks - (Calendar.PLAYER_TIME.getTicks() - tile.getLitTick());
          long remainingMinutes = Math.round(remainingTicks / 1200.0f);
          long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
          switch (ConfigDevice.BLOCK.PIT_KILN.timeTooltipMode) {
            case NONE:
              break;
            case TICKS:
              probeInfo.text(
                new TextComponentTranslation(
                  ModUtils.localize("top", "devices.pit_kiln.remaining.ticks"), remainingTicks).getFormattedText());
              break;
            case MINECRAFT_HOURS:
              probeInfo.text(
                new TextComponentTranslation(
                  ModUtils.localize("top", "devices.pit_kiln.remaining.hours"), remainingHours).getFormattedText());
              break;
            case REAL_MINUTES:
              probeInfo.text(
                new TextComponentTranslation(
                  ModUtils.localize("top", "devices.pit_kiln.remaining.minutes"), remainingMinutes).getFormattedText());
              break;
          }
        } else {
          int straw = tile.getStrawCount();
          int logs = tile.getLogCount();
          if (straw == 8 && logs == 8) {
            probeInfo.text(new TextComponentTranslation(
              ModUtils.localize("top", "devices.pit_kiln.unlit")).getFormattedText());
          } else {
            if (straw < 8) {
              probeInfo.text(
                new TextComponentTranslation(
                  ModUtils.localize("top", "devices.pit_kiln.straw"), 8 - straw).getFormattedText());
            }
            if (logs < 8) {
              probeInfo.text(
                new TextComponentTranslation(
                  ModUtils.localize("top", "devices.pit_kiln.logs"), 8 - logs).getFormattedText());
            }
          }
        }
      });
    }
  }
}
