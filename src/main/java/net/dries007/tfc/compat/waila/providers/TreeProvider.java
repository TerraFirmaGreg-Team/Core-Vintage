package net.dries007.tfc.compat.waila.providers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;

import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeProvider implements IWailaBlock {

  @Nonnull
  @Override
  public List<String> getTooltip(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull NBTTagCompound nbt) {
    List<String> currentTooltip = new ArrayList<>();
    IBlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BlockSaplingTFC) {
      BlockSaplingTFC block = ((BlockSaplingTFC) state.getBlock());
      Tree wood = block.getWood();
      TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
      if (te != null) {
        long days = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
        float perc = Math.min(0.99F, days / wood.getMinGrowthTime()) * 100;
        String growth = String.format("%d%%", Math.round(perc));
        currentTooltip.add(new TextComponentTranslation("waila.tfc.crop.growth", growth).getFormattedText());
      }

    }
    return currentTooltip;
  }

  @Nonnull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(BlockSaplingTFC.class);
  }
}
