package su.terrafirmagreg.modules.wood.plugin.top.provider;

import su.terrafirmagreg.api.plugin.top.provider.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSapling;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodSapling;

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

public final class ProviderWoodSapling extends BaseProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "wood.sapling");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockWoodSapling blockSapling) {
      TileUtils.getTile(world, pos, TileWoodSapling.class).ifPresent(tile -> {

        long days = tile.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
        float perc = Math.min(0.99F, days / blockSapling.getType().getMinGrowthTime()) * 100;
        String growth = String.format("%d%%", Math.round(perc));

        info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
            .text(new TextComponentTranslation(
              ModUtils.localize("top", "wood.crop.growth"),
              growth).getFormattedText());
      });
    }
  }
}
