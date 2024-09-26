package net.dries007.tfc.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SmelteryRecipe extends IForgeRegistryEntry.Impl<SmelteryRecipe> {

  /**
   * -- GETTER -- For JEI only
   */
  @Getter
  private IIngredient<ItemStack>[] ingredients;
  private FluidStack outputFluid;
  @Getter
  private float meltTemp;

  private SmelteryRecipe() {
  }

  @Nullable
  public static SmelteryRecipe get(ItemStack... ingredients) {
    return TFCRegistries.SMELTERY.getValuesCollection()
                                 .stream()
                                 .filter(x -> x.isValidInput(ingredients))
                                 .findFirst()
                                 .orElse(null);
  }

  private boolean isValidInput(ItemStack... ingredients) {
    for (IIngredient<ItemStack> ingredient : this.ingredients) {
      boolean pass = false;
      for (ItemStack stack : ingredients) {
        if (ingredient.test(stack)) {
          pass = true;
          break;
        }
      }
      if (!pass) {
        return false;
      }
    }
    return true;
  }

  public FluidStack getOutput() {
    return this.outputFluid.copy();
  }

  public void consumeInputs(List<ItemStack> inputs) {
    for (IIngredient<ItemStack> ingredient : this.ingredients) {
      for (ItemStack stack : inputs) {
        if (ingredient.test(stack)) {
          stack.shrink(ingredient.getAmount());
          break;
        }
      }
    }
  }

  public static class Builder {

    private final SmelteryRecipe recipe;
    private final List<IIngredient<ItemStack>> listInput;

    public Builder() {
      recipe = new SmelteryRecipe();
      listInput = new ArrayList<>();
    }

    public Builder addInput(@NotNull IIngredient<ItemStack> ingredient) {
      if (this.listInput.size() < 8) {
        this.listInput.add(ingredient);
      } else {
        throw new IllegalStateException("Smeltery recipes must have at most 8 ingredients!");
      }
      return this;
    }

    public Builder setOutput(@NotNull FluidStack fluidStack, float meltTemp) {
      this.recipe.outputFluid = fluidStack;
      this.recipe.meltTemp = meltTemp;
      return this;
    }

    public SmelteryRecipe build() {
      if (this.listInput.isEmpty()) {
        throw new IllegalStateException("Smeltery recipes must have at least 1 ingredient!");
      } else if (this.recipe.outputFluid == null) {
        throw new IllegalStateException("Missing Smeltery recipe output!");
      } else {
        //noinspection unchecked
        this.recipe.ingredients = listInput.toArray(new IIngredient[0]);
        return this.recipe;
      }
    }
  }
}
