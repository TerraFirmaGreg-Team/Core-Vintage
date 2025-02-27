package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.base.plugin.top.provider.spi.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;
import su.terrafirmagreg.modules.device.object.block.BlockCrucible;
import su.terrafirmagreg.modules.device.object.tile.TileCrucible;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.api.types.Metal;

public class ProviderCrucible extends BaseProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.crucible");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockCrucible) {
      TileUtils.getTile(world, pos, TileCrucible.class).ifPresent(tile -> {
        var probeInfo = info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

        NBTTagCompound nbt = new NBTTagCompound();
        nbt = tile.writeToNBT(nbt);

        var amount = tile.getAlloy().getAmount();
        if (amount > 0) {
          Metal metal = tile.getAlloyResult();
          probeInfo.text(
            new TextComponentTranslation(ModUtils.localize("top", "metal.output"), amount, new TextComponentTranslation(metal.getTranslationKey()).getFormattedText()).getFormattedText());
        }
        float temperature = nbt.getFloat("temp");
        String heatTooltip = Heat.getTooltip(temperature);
        if (heatTooltip != null) {
          probeInfo.text(heatTooltip);
        }
      });
    }
  }
}
