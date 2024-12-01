package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.plugin.top.provider.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.object.block.BlockOven;
import su.terrafirmagreg.modules.device.object.tile.TileOven;

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
import net.dries007.eerussianguy.firmalife.recipe.OvenRecipe;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.modules.device.object.tile.TileOven.SLOT_MAIN;

public class ProviderOven extends BaseProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.oven");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockOven) {

      var probeInfo = info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

      TileUtils.getTile(world, pos, TileOven.class).ifPresent(tile -> {
        ItemStack mainSlot = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(SLOT_MAIN);
        OvenRecipe recipe = OvenRecipe.get(mainSlot);
        if (state.getValue(LIT) && recipe != null) {
          long remainingTicks = tile.getTicksRemaining();
          switch (ConfigDevice.BLOCK.OVEN.timeTooltipMode) {
            case NONE:
              break;
            case TICKS:
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize("top", "devices.ticks_remaining"), remainingTicks).getFormattedText());
            case MINECRAFT_HOURS:
              long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize("top", "devices.hours_remaining"), remainingHours).getFormattedText());
              break;
            case REAL_MINUTES:
              long remainingMinutes = Math.round(remainingTicks / 1200.0f);
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize("top", "devices.minutes_remaining"), remainingMinutes).getFormattedText());
              break;
          }
          probeInfo.text(new TextComponentTranslation(recipe.getOutputItem(mainSlot).getDisplayName()).getFormattedText());
          if (tile.isCuringRecipe()) {
            probeInfo.text("Curing");
          }
        }
      });
    }
  }
}
