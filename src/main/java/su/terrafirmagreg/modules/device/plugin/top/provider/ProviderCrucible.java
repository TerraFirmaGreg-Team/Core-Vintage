package su.terrafirmagreg.modules.device.plugin.top.provider;

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
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.api.types.Metal;

import java.util.ArrayList;
import java.util.List;

public class ProviderCrucible implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.crucible");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world,
                           IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockCrucible) {
      var tile = TileUtils.getTile(world, pos, TileCrucible.class);
      if (tile == null) {
        return;
      }

      List<String> currentTooltip = new ArrayList<>();

      NBTTagCompound nbt = new NBTTagCompound();
      nbt = tile.writeToNBT(nbt);

      var amount = tile.getAlloy().getAmount();
      if (amount > 0) {
        Metal metal = tile.getAlloyResult();
        currentTooltip.add(
          new TextComponentTranslation(ModUtils.localize("top", "metal.output"), amount,
                                       new TextComponentTranslation(
                                         metal.getTranslationKey()).getFormattedText()).getFormattedText());
      }
      float temperature = nbt.getFloat("temp");
      String heatTooltip = Heat.getTooltip(temperature);
      if (heatTooltip != null) {
        currentTooltip.add(heatTooltip);
      }

      for (String string : currentTooltip) {
        info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
            .text(string);
      }

    }

  }

}
