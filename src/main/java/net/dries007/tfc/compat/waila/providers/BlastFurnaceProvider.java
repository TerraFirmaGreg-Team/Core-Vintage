package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;
import su.terrafirmagreg.modules.device.object.block.BlockBlastFurnace;
import su.terrafirmagreg.modules.device.object.tile.TileBlastFurnace;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlastFurnaceProvider implements IWailaBlock {

  @Nonnull
  @Override
  public List<String> getTooltip(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull NBTTagCompound nbt) {
    List<String> currentTooltip = new ArrayList<>();
    TileBlastFurnace blastFurnace = Helpers.getTE(world, pos, TileBlastFurnace.class);
    if (blastFurnace != null) {
      int chinmey = BlockBlastFurnace.getChimneyLevels(blastFurnace.getWorld(), blastFurnace.getPos());
      if (chinmey > 0) {
        int maxItems = chinmey * 4;
        int oreStacks = blastFurnace.getOreStacks().size();
        int fuelStacks = blastFurnace.getFuelStacks().size();
        float temperature = nbt.getFloat("temperature");
        String heatTooltip = Heat.getTooltip(temperature);
        currentTooltip.add(new TextComponentTranslation("waila.tfc.bloomery.ores", oreStacks, maxItems).getFormattedText());
        currentTooltip.add(new TextComponentTranslation("waila.tfc.bloomery.fuel", fuelStacks, maxItems).getFormattedText());
        if (heatTooltip != null) {
          currentTooltip.add(heatTooltip);
        }
      } else {
        currentTooltip.add(new TextComponentTranslation("waila.tfc.blast_furnace.not_formed").getFormattedText());
      }
    }
    return currentTooltip;
  }

  @Nonnull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(TileBlastFurnace.class);
  }
}
