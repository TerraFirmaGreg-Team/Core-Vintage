package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoProvider implements IWailaBlock {

  @Nonnull
  @Override
  public List<String> getTooltip(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull NBTTagCompound nbt) {
    List<String> currentTooltip = new ArrayList<>();
    int temperature = Math.round(Climate.getActualTemp(world, pos, 0));
    int rainfall = Math.round(Climate.getRainfall(world, pos));
    currentTooltip.add(new TextComponentTranslation("waila.tfc.temperature", temperature).getFormattedText());
    currentTooltip.add(new TextComponentTranslation("waila.tfc.rainfall", rainfall).getFormattedText());
    return currentTooltip;
  }

  @Nonnull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(BlockRockVariant.class);
  }
}
