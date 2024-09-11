package su.terrafirmagreg.modules.device.plugin.top.provider;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.objects.blocks.BlockBloom;
import su.terrafirmagreg.modules.device.objects.tiles.TileBloom;

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
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;

import java.util.ArrayList;
import java.util.List;

public class ProviderBloom implements IProbeInfoProvider {

  @Override
  public String getID() {
    return ModUtils.localize("top", "device.bloom");
  }

  @Override
  public void addProbeInfo(ProbeMode mode, IProbeInfo info, EntityPlayer player, World world,
          IBlockState state, IProbeHitData hitData) {
    Block block = state.getBlock();
    BlockPos pos = hitData.getPos();

    if (block instanceof BlockBloom) {

      var tile = TileUtils.getTile(world, pos, TileBloom.class);
      if (tile == null) {
        return;
      }

      List<String> currentTooltip = new ArrayList<>();

      IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      if (cap != null) {
        ItemStack bloomStack = cap.getStackInSlot(0);
        IForgeable forgeCap = bloomStack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY,
                null);
        if (forgeCap instanceof IForgeableMeasurableMetal bloomCap) {
          currentTooltip.add(new TextComponentTranslation(ModUtils.localize("top", "metal.output"),
                  bloomCap.getMetalAmount(),
                  new TextComponentTranslation(bloomCap.getMetal()
                          .getTranslationKey()).getFormattedText()).getFormattedText());
        }
      }

      for (String string : currentTooltip) {
        info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                .text(string);
      }
    }
  }
}
