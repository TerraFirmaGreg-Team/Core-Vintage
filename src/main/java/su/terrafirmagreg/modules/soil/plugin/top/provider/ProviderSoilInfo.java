package su.terrafirmagreg.modules.soil.plugin.top.provider;

import su.terrafirmagreg.api.plugin.top.provider.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;

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

import java.util.ArrayList;
import java.util.List;

public class ProviderSoilInfo extends BaseProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "soil.info");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world,
                           IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof ISoilBlock rockBlock) {

      List<String> currentTooltip = new ArrayList<>();

      int temperature = Math.round(Climate.getActualTemp(world, pos, 0));
      int rainfall = Math.round(Climate.getRainfall(world, pos));
      currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "temperature"), temperature).getFormattedText());
      currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "rainfall"), rainfall).getFormattedText());

      for (String string : currentTooltip) {
        info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(string);
      }
    }
  }
}
