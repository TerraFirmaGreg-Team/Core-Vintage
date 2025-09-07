package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.feature.calendar.spi.ICalendar;
import su.terrafirmagreg.modules.device.content.block.BlockLeafMat;
import su.terrafirmagreg.modules.device.content.tile.TileLeafMat;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.objects.recipes.DryingRecipe;

public class ProviderLeafMat implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.leaf_mat");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    var probeInfo = info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

    if (block instanceof BlockLeafMat) {
      TileUtils.getTile(world, pos, TileLeafMat.class).ifPresent(tile -> {
        ItemStack mainSlot = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
        DryingRecipe recipe = DryingRecipe.get(mainSlot);
        if (!mainSlot.isEmpty() && recipe != null) {
          long remainingTicks = tile.getTicksRemaining();
          switch (ConfigCore.MISC.TOOLTIP.timeMode) {
            case NONE:
              break;
            case TICKS:
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize("top", "devices.leaf_mat.remaining.ticks"), remainingTicks).getFormattedText());
            case MINECRAFT_HOURS:
              long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize("top", "devices.leaf_mat.remaining.hours"), remainingHours).getFormattedText());
              break;
            case REAL_MINUTES:
              long remainingMinutes = Math.round(remainingTicks / 1200.0f);
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize("top", "devices.leaf_mat.remaining.minutes"), remainingMinutes).getFormattedText());
              break;
          }
          probeInfo.text(new TextComponentTranslation(recipe.getOutputItem(mainSlot).getTranslationKey() + ".name").getFormattedText());
        }
      });
    }
  }
}
