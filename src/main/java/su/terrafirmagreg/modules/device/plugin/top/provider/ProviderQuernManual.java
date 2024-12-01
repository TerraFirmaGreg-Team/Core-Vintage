package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.plugin.top.provider.BaseProvider;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.block.BlockQuernManual;
import su.terrafirmagreg.modules.device.object.tile.TileQuernManual;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;

import static su.terrafirmagreg.modules.device.object.tile.TileQuernManual.SLOT_HANDSTONE;

public class ProviderQuernManual extends BaseProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.quern.manual");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockQuernManual) {
      TileUtils.getTile(world, pos, TileQuernManual.class).ifPresent(tile -> {

        IItemHandler handler;
        ItemStack handstone;

        if (!tile.hasHandstone()) {
          return;
        }

        handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (handler == null) {
          return;
        }
        handstone = handler.getStackInSlot(SLOT_HANDSTONE);

        if (!handstone.isEmpty()) {
          info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
              .text(new TextComponentTranslation(
                ModUtils.localize("top", "device.quern.handstone.durability"),
                handstone.getItemDamage(),
                handstone.getMaxDamage()).getFormattedText());
        }
      });
    }
  }
}
