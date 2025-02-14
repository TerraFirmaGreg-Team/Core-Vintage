package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.base.plugin.top.provider.spi.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;
import su.terrafirmagreg.modules.device.object.block.BlockBlastFurnace;
import su.terrafirmagreg.modules.device.object.tile.TileBlastFurnace;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProviderBlastFurnace extends BaseProvider {

  @Override
  public @NotNull String getID() {
    return ModUtils.localize("top", "device.blast_furnace");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockBlastFurnace) {

      TileUtils.getTile(world, pos, TileBlastFurnace.class).ifPresent(tile -> {

        List<String> currentTooltip = new ArrayList<>();

        NBTTagCompound nbt = new NBTTagCompound();
        nbt = tile.writeToNBT(nbt);

        int chinmey = BlockBlastFurnace.getChimneyLevels(tile.getWorld(), tile.getPos());
        if (chinmey > 0) {
          int maxItems = chinmey * 4;
          int oreStacks = tile.getOreStacks().size();
          int fuelStacks = tile.getFuelStacks().size();
          float temperature = nbt.getFloat("temperature");
          String heatTooltip = Heat.getTooltip(temperature);
          currentTooltip.add(
            new TextComponentTranslation(ModUtils.localize("top", "device.bloomery.ores"),
              oreStacks, maxItems).getFormattedText());
          currentTooltip.add(
            new TextComponentTranslation(ModUtils.localize("top", "device.bloomery.fuel"),
              fuelStacks, maxItems).getFormattedText());
          if (heatTooltip != null) {
            currentTooltip.add(heatTooltip);
          }
        } else {
          currentTooltip.add(new TextComponentTranslation(
            ModUtils.localize("top", "device.blast_furnace.not_formed")).getFormattedText());
        }

        for (String string : currentTooltip) {
          info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(string);
        }
      });
    }
  }
}
