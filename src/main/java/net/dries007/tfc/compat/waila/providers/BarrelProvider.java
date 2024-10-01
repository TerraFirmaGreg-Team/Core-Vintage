package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodBarrel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarrelProvider implements IWailaBlock {

  @NotNull
  @Override
  public List<String> getTooltip(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
    List<String> currentTooltip = new ArrayList<>();
    TileUtils.getTile(world, pos, TileWoodBarrel.class).ifPresent(tile -> {
      IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
      FluidStack fluid = fluidHandler != null ? fluidHandler.drain(Integer.MAX_VALUE, false) : null;

      if (tile.isSealed()) {
        currentTooltip.add(new TextComponentTranslation("waila.tfc.barrel.sealed", tile.getSealedDate()).getFormattedText());
        BarrelRecipe recipe = tile.getRecipe();
        if (recipe != null) {
          currentTooltip.add(new TextComponentTranslation("waila.tfc.barrel.recipe", recipe.getResultName()).getFormattedText());
        } else {
          currentTooltip.add(new TextComponentTranslation("waila.tfc.barrel.no_recipe").getFormattedText());
        }
      }
      if (fluid != null && fluid.amount > 0) {
        currentTooltip.add(
          new TextComponentTranslation("waila.tfc.barrel.contents", fluid.amount, fluid.getLocalizedName()).getFormattedText());
      }
    });
    return currentTooltip;
  }

  @NotNull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(TileWoodBarrel.class);
  }
}
