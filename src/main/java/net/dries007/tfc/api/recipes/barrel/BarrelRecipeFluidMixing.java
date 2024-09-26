package net.dries007.tfc.api.recipes.barrel;

import su.terrafirmagreg.api.util.CollectionUtils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientFluidItem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarrelRecipeFluidMixing extends BarrelRecipe {

  public BarrelRecipeFluidMixing(@NotNull IIngredient<FluidStack> inputFluid, @NotNull IngredientFluidItem inputStack,
                                 @Nullable FluidStack outputFluid, int duration) {
    super(inputFluid, inputStack, outputFluid, ItemStack.EMPTY, duration);
  }

  @Override
  public boolean isValidInputInstant(ItemStack inputStack, @Nullable FluidStack inputFluid) {
    // Used on instant recipes, to verify that they only convert if there exists enough items to fully convert the fluid
    FluidStack inputStackFluid = FluidUtil.getFluidContained(inputStack);
    if (inputFluid != null && inputStackFluid != null) {
      return inputFluid.amount / this.inputFluid.getAmount() <= inputStackFluid.amount / this.inputStack.getAmount();
    }
    return false;
  }

  @Nullable
  @Override
  public FluidStack getOutputFluid(FluidStack inputFluid, ItemStack inputStack) {
    return super.getOutputFluid(inputFluid, inputStack);
  }

  @Override
  protected int getMultiplier(FluidStack inputFluid, ItemStack inputStack) {
    if (isValidInput(inputFluid, inputStack)) {
      FluidStack inputStackFluid = FluidUtil.getFluidContained(inputStack);
      if (inputStackFluid != null) {
        return Math.min(inputFluid.amount / this.inputFluid.getAmount(), inputStackFluid.amount / this.inputStack.getAmount());
      }
    }
    return 0;
  }

  @NotNull
  @Override
  public List<ItemStack> getOutputItem(FluidStack inputFluid, ItemStack inputStack) {
    return CollectionUtils.listOf(inputStack.getItem().getContainerItem(inputStack));
  }
}
