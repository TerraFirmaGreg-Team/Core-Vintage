package su.terrafirmagreg.modules.rock.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;

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
import net.dries007.tfc.util.climate.ClimateTFC;

import java.util.ArrayList;
import java.util.List;

public class ProviderRockInfo implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "rock.info");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world,
                           IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof IRockBlock rockBlock) {

      List<String> currentTooltip = new ArrayList<>();

      int temperature = Math.round(ClimateTFC.getActualTemp(world, pos, 0));
      int rainfall = Math.round(ClimateTFC.getRainfall(world, pos));
      currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "temperature"), temperature).getFormattedText());
      currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "rainfall"), rainfall).getFormattedText());

      if (rockBlock.getType().isFlux()) {
        currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "flux.rock")).getFormattedText());
      }

      for (String string : currentTooltip) {
        info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(string);
      }
    }
  }
}
