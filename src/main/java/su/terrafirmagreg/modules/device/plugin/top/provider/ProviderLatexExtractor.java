package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.plugin.top.provider.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.block.BlockLatexExtractor;
import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

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

public final class ProviderLatexExtractor extends BaseProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.latex_extractor");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockLatexExtractor) {
      TileUtils.getTile(world, pos, TileLatexExtractor.class).ifPresent(tile -> {
        if (tile.getFluidAmount() > 0) {
          IProbeInfo horizontalPane = info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
          horizontalPane.text(new TextComponentTranslation(ModUtils.localize("top", "device.latex.quantity"), tile.getFluidAmount()).getFormattedText());
        }
      });
    }
  }
}
