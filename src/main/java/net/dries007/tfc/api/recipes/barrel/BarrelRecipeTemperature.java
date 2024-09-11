package net.dries007.tfc.api.recipes.barrel;

import su.terrafirmagreg.api.util.CollectionUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarrelRecipeTemperature extends BarrelRecipe {

  private final int coolAmount;

  public BarrelRecipeTemperature(IIngredient<FluidStack> fluidInput, int coolAmount) {
    super(fluidInput, IIngredient.empty(), null, ItemStack.EMPTY, 0);
    this.coolAmount = coolAmount;
  }

  @Override
  public boolean isValidInput(FluidStack inputFluid, ItemStack inputStack) {
    var cap = CapabilityHeat.get(inputStack);
    if (cap != null) {
      return cap.getTemperature() > 0 && this.inputFluid.testIgnoreCount(inputFluid);
    }
    return false;
  }

  @Override
  public boolean isValidInputInstant(ItemStack inputStack, FluidStack inputFluid) {
    return true;
  }

  @Override
  @Nullable
  public FluidStack getOutputFluid(FluidStack inputFluid, ItemStack inputStack) {
    int multiplier = inputStack.getCount();
    int retainAmount = inputFluid.amount - multiplier;
    if (retainAmount > 0) {
      return new FluidStack(inputFluid.getFluid(), retainAmount);
    }
    return null;
  }

  @Override
  @NotNull
  public List<ItemStack> getOutputItem(FluidStack inputFluid, ItemStack inputStack) {
    var cap = CapabilityHeat.get(inputStack);
    if (cap != null) {
      cap.setTemperature(cap.getTemperature() - coolAmount);
    }
    return CollectionUtils.listOf(inputStack);
  }

  @Override
  public void onRecipeComplete(World world, BlockPos pos) {
    if (world.getTotalWorldTime() % 4 == 0) {
      world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.8f, 0.8f + MathConstants.RNG.nextFloat() * 0.4f);
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public String getResultName() {
    return I18n.format("tfc.tooltip.barrel_cooling");
  }
}
