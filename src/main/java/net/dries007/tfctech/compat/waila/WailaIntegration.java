package net.dries007.tfctech.compat.waila;

import su.terrafirmagreg.modules.device.content.block.BlockFridge;
import su.terrafirmagreg.modules.device.content.block.BlockWireDrawBench;
import su.terrafirmagreg.modules.device.content.tile.TileLatexExtractor;
import su.terrafirmagreg.modules.device.content.tile.TileWireDrawBench;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;
import java.util.List;

@WailaPlugin
public final class WailaIntegration implements IWailaDataProvider, IWailaPlugin {

  @Override
  public void register(IWailaRegistrar registrar) {
    registrar.registerBodyProvider(this, BlockWireDrawBench.class);
    registrar.registerBodyProvider(this, BlockFridge.class);
    registrar.registerBodyProvider(this, TileLatexExtractor.class);
  }

  @Nonnull
  @Override
  public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    Block b = accessor.getBlock();
    if (b instanceof BlockWireDrawBench) {
      BlockPos TEPos = accessor.getPosition();
      if (!accessor.getBlockState().getValue(BlockWireDrawBench.UPPER)) {
        TEPos = TEPos.offset(accessor.getBlockState().getValue(BlockWireDrawBench.FACING));
      }
      TileWireDrawBench bench = Helpers.getTE(accessor.getWorld(), TEPos, TileWireDrawBench.class);
      if (bench != null) {
        if (bench.getProgress() > 0) {
          currenttip.add((new TextComponentTranslation("waila.tfctech.wiredraw.progress", bench.getProgress())).getFormattedText());
        }
      }
    }
    return currenttip;
  }
}
