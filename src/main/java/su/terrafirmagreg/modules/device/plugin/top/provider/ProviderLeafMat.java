package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.plugin.top.provider.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.device.object.block.BlockLeafMat;
import su.terrafirmagreg.modules.device.object.tile.TileLeafMat;

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
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.eerussianguy.firmalife.recipe.DryingRecipe;

public class ProviderLeafMat extends BaseProvider {

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
                ModUtils.localize("top", "devices.leaf_mat.ticks_remaining"), remainingTicks).getFormattedText());
            case MINECRAFT_HOURS:
              long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize("top", "devices.leaf_mat.hours_remaining"), remainingHours).getFormattedText());
              break;
            case REAL_MINUTES:
              long remainingMinutes = Math.round(remainingTicks / 1200.0f);
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize("top", "devices.leaf_mat.minutes_remaining"), remainingMinutes).getFormattedText());
              break;
          }
          probeInfo.text(new TextComponentTranslation(recipe.getOutputItem(mainSlot).getTranslationKey() + ".name").getFormattedText());
        }
      });
    }
  }
}
