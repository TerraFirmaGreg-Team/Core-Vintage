package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;

import su.terrafirmagreg.modules.core.feature.calendar.Month;

import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BerryBushProvider implements IWailaBlock {

  @Nonnull
  @Override
  public List<String> getTooltip(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull NBTTagCompound nbt) {
    List<String> currentTooltip = new ArrayList<>();
    IBlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BlockBerryBush block) {
      if (block.getBush().isHarvestMonth(Calendar.CALENDAR_TIME.getMonthOfYear()) && !state.getValue(BlockBerryBush.FRUITING)) {
        float temp = Climate.getActualTemp(world, pos);
        float rainfall = ChunkDataTFC.getRainfall(world, pos);
        TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
        if (te != null && block.getBush().isValidForGrowth(temp, rainfall)) {
          long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
          // Don't show 100% since it still needs to check on randomTick to grow
          float perc = Math.min(0.99F, hours / (block.getBush().getGrowthTime() * (float) ConfigTFC.General.FOOD.berryBushGrowthTimeModifier)) * 100;
          String growth = String.format("%d%%", Math.round(perc));
          currentTooltip.add(new TextComponentTranslation("waila.tfc.crop.growth", growth).getFormattedText());
        } else {
          currentTooltip.add(new TextComponentTranslation("waila.tfc.crop.not_growing").getFormattedText());
        }
      } else {
        currentTooltip.add(new TextComponentTranslation("waila.tfc.agriculture.harvesting_months").getFormattedText());
        for (Month month : Month.values()) {
          if (block.getBush().isHarvestMonth(month)) {
            currentTooltip.add(TerraFirmaCraft.getProxy().getMonthName(month, true));
          }
        }
      }
    }
    return currentTooltip;
  }

  @Nonnull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(BlockBerryBush.class);
  }
}
