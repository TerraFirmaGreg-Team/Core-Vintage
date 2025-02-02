package net.dries007.tfctech.compat.waila;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;

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
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfctech.objects.blocks.devices.BlockFridge;
import net.dries007.tfctech.objects.blocks.devices.BlockLatexExtractor;
import net.dries007.tfctech.objects.blocks.devices.BlockWireDrawBench;
import net.dries007.tfctech.objects.tileentities.TEFridge;
import net.dries007.tfctech.objects.tileentities.TELatexExtractor;
import net.dries007.tfctech.objects.tileentities.TEWireDrawBench;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

public final class TOPPlugin implements Function<ITheOneProbe, Void>, IProbeInfoProvider {

  @Override
  public String getID() {
    return TFCTECH + ":top_provider";
  }

  @Override
  public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData) {
    Block b = iBlockState.getBlock();
    BlockPos pos = iProbeHitData.getPos();
    if (b instanceof BlockWireDrawBench) {
      BlockPos TEPos = pos;
      if (!iBlockState.getValue(BlockWireDrawBench.UPPER)) {
        TEPos = TEPos.offset(iBlockState.getValue(BlockWireDrawBench.FACING));
      }
      TEWireDrawBench bench = Helpers.getTE(world, TEPos, TEWireDrawBench.class);
      if (bench != null) {
        if (bench.getProgress() > 0) {
          IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
          horizontalPane.text((new TextComponentTranslation("waila.tfctech.wiredraw.progress", bench.getProgress())).getFormattedText());
        }
      }
    }
    if (b instanceof BlockFridge) {
      BlockPos TEPos = pos;
      if (!iBlockState.getValue(BlockWireDrawBench.UPPER)) {
        TEPos = TEPos.up();
      }
      TEFridge fridge = Helpers.getTE(world, TEPos, TEFridge.class);
      if (fridge != null) {
        IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
        horizontalPane.text((new TextComponentTranslation("waila.tfctech.fridge.efficiency", (int) fridge.getEfficiency())).getFormattedText());
        if (fridge.isOpen()) {
          int slot = BlockFridge.getPlayerLookingItem(TEPos.down(), entityPlayer, iBlockState.getValue(BlockFridge.FACING));
          if (slot > -1) {
            ItemStack stack = fridge.getSlot(slot);
            if (!stack.isEmpty()) {
              iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                .item(stack)
                .vertical()
                .itemLabel(stack);
              ICapabilityFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
              List<String> list = new ArrayList<>();
              if (cap != null) {
                cap.addTooltipInfo(stack, list, entityPlayer);
              }
              for (String text : list) {
                iProbeInfo.text(text);
              }
            }
          }
        }
      }
    }
    if (b instanceof BlockLatexExtractor) {
      TELatexExtractor extractor = Helpers.getTE(world, pos, TELatexExtractor.class);
      if (extractor != null) {
        if (extractor.getFluidAmount() > 0) {
          IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
          horizontalPane.text((new TextComponentTranslation("waila.tfctech.latex.quantity", extractor.getFluidAmount())).getFormattedText());
        }
      }
    }
  }

  @Nullable
  @Override
  public Void apply(ITheOneProbe iTheOneProbe) {
    iTheOneProbe.registerProvider(this);
    return null;
  }
}
