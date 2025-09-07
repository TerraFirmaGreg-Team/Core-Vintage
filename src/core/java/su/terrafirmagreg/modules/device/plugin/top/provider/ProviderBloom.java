package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.forge.IForgeableMeasurableMetal;
import su.terrafirmagreg.modules.device.content.block.BlockBloom;
import su.terrafirmagreg.modules.device.content.tile.TileBloom;

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

public class ProviderBloom implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.bloom");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world, IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockBloom) {

      TileUtils.getTile(world, pos, TileBloom.class).ifPresent(tile -> {
        var probeInfo = info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));

        CapabilityUtils.getOptional(tile, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(capItem -> {
          ItemStack bloomStack = capItem.getStackInSlot(0);
          CapabilityUtils.getOptional(bloomStack, CapabilityForgeable.CAPABILITY).ifPresent(capForge -> {
            if (capForge instanceof IForgeableMeasurableMetal bloomCap) {
              probeInfo.text(new TextComponentTranslation(
                ModUtils.localize(LocalizeKeys.TOP, "device.bloom.metal.output"), bloomCap.getMetalAmount(),
                new TextComponentTranslation(bloomCap.getMetal().getTranslationKey()).getFormattedText()).getFormattedText());
            }
          });

        });
      });
    }
  }
}
