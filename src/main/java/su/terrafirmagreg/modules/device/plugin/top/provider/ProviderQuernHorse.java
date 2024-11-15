package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.block.BlockQuernHorse;
import su.terrafirmagreg.modules.device.object.tile.TileQuernHorse;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.dries007.horsepower.util.Localization;

import java.util.ArrayList;
import java.util.List;

public class ProviderQuernHorse implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.quern.horse");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockQuernHorse) {
      var tile = TileUtils.getTile(world, pos, TileQuernHorse.class);

      List<String> currentTooltip = new ArrayList<>();

      double total = tile.map(TileQuernHorse::getTotalItemMillTime).orElse(0);
      double current = tile.map(TileQuernHorse::getCurrentItemMillTime).orElse(0);
      double progress = Math.round(((current / total) * 100D) * 100D) / 100D;
      currentTooltip.add(Localization.WAILA.GRINDSTONE_PROGRESS.translate(progress));

      info.progress((int) (current / total * 100L), 100L, new ProgressStyle().prefix(Localization.TOP.GRINDSTONE_PROGRESS.translate() + " ").suffix("%"));

//      for (String string : currentTooltip) {
//        info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(string);
//      }

    }
  }

}
