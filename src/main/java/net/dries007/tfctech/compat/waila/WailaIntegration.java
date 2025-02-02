package net.dries007.tfctech.compat.waila;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfctech.objects.blocks.devices.BlockFridge;
import net.dries007.tfctech.objects.blocks.devices.BlockLatexExtractor;
import net.dries007.tfctech.objects.blocks.devices.BlockWireDrawBench;
import net.dries007.tfctech.objects.tileentities.TEFridge;
import net.dries007.tfctech.objects.tileentities.TELatexExtractor;
import net.dries007.tfctech.objects.tileentities.TEWireDrawBench;

import javax.annotation.Nonnull;
import java.util.List;

@WailaPlugin
public final class WailaIntegration implements IWailaDataProvider, IWailaPlugin {

  @Override
  public void register(IWailaRegistrar registrar) {
    registrar.registerBodyProvider(this, BlockWireDrawBench.class);
    registrar.registerBodyProvider(this, BlockFridge.class);
    registrar.registerBodyProvider(this, TELatexExtractor.class);
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
      TEWireDrawBench bench = Helpers.getTE(accessor.getWorld(), TEPos, TEWireDrawBench.class);
      if (bench != null) {
        if (bench.getProgress() > 0) {
          currenttip.add((new TextComponentTranslation("waila.tfctech.wiredraw.progress", bench.getProgress())).getFormattedText());
        }
      }
    }
    if (b instanceof BlockFridge) {
      BlockPos TEPos = accessor.getPosition();
      if (!accessor.getBlockState().getValue(BlockWireDrawBench.UPPER)) {
        TEPos = TEPos.up();
      }
      TEFridge fridge = Helpers.getTE(accessor.getWorld(), TEPos, TEFridge.class);
      if (fridge != null) {
        currenttip.add((new TextComponentTranslation("waila.tfctech.fridge.efficiency", (int) fridge.getEfficiency())).getFormattedText());
        if (fridge.isOpen()) {
          int slot = BlockFridge.getPlayerLookingItem(TEPos.down(), accessor.getPlayer(), accessor.getBlockState().getValue(BlockFridge.FACING));
          if (slot > -1) {
            ItemStack stack = fridge.getSlot(slot);
            if (!stack.isEmpty()) {
              currenttip.add(TextFormatting.WHITE + stack.getDisplayName());
              ICapabilityFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
              if (cap != null) {
                cap.addTooltipInfo(stack, currenttip, accessor.getPlayer());
              }
            }
          }
        }
      }
    }
    if (b instanceof BlockLatexExtractor) {
      BlockPos TEPos = accessor.getPosition();
      TELatexExtractor extractor = Helpers.getTE(accessor.getWorld(), TEPos, TELatexExtractor.class);
      if (extractor != null) {
        currenttip.add((new TextComponentTranslation("waila.tfctech.latex.quantity", extractor.getFluidAmount())).getFormattedText());
      }
    }
    return currenttip;
  }
}
