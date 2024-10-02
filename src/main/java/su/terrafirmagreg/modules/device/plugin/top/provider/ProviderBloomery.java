package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.block.BlockBloomery;
import su.terrafirmagreg.modules.device.object.tile.TileBloomery;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.recipes.BloomeryRecipe;
import net.dries007.tfc.util.calendar.ICalendar;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.data.Properties.BoolProp.LIT;

public class ProviderBloomery implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.bloomery");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world,
                           IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockBloomery) {

      TileUtils.getTile(world, pos, TileBloomery.class).ifPresent(tile -> {

        List<String> currentTooltip = new ArrayList<>();

        if (state.getValue(LIT)) {
          List<ItemStack> oreStacks = tile.getOreStacks();
          BloomeryRecipe recipe = !oreStacks.isEmpty() ? BloomeryRecipe.get(oreStacks.get(0)) : null;
          long remainingTicks = tile.getRemainingTicks();
          switch (ConfigTFC.Client.TOOLTIP.timeTooltipMode) {
            case NONE:
              break;
            case TICKS:
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.ticks.remaining"), remainingTicks).getFormattedText());
              break;
            case MINECRAFT_HOURS:
              long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.hours.remaining"), remainingHours).getFormattedText());
              break;
            case REAL_MINUTES:
              long remainingMinutes = Math.round(remainingTicks / 1200.0f);
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.minutes.remaining"), remainingMinutes).getFormattedText());
              break;
          }
          if (recipe != null) {
            ItemStack output = recipe.getOutput(oreStacks);
            IForgeable cap = output.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
            if (cap instanceof IForgeableMeasurableMetal forgeCap) {
              currentTooltip.add(
                new TextComponentTranslation(ModUtils.localize("top", "devices.bloomery.output"), forgeCap.getMetalAmount(),
                                             new TextComponentTranslation(forgeCap.getMetal().getTranslationKey()).getFormattedText()).getFormattedText());
            }
          }
        } else {
          int ores = tile.getOreStacks().size();
          int fuel = tile.getFuelStacks().size();
          int max = BlockBloomery.getChimneyLevels(world, tile.getInternalBlock()) * 8;
          currentTooltip.add(
            new TextComponentTranslation(ModUtils.localize("top", "devices.bloomery.ores"), ores, max).getFormattedText());
          currentTooltip.add(
            new TextComponentTranslation(ModUtils.localize("top", "devices.bloomery.fuel"), fuel, max).getFormattedText());
        }

        for (String string : currentTooltip) {
          info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER)).text(string);
        }
      });

    }
  }
}
