package net.dries007.tfc.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.types.TechRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SmelteryRecipe extends IForgeRegistryEntry.Impl<SmelteryRecipe> {

  private IIngredient<ItemStack>[] ingredients;
  private FluidStack outputFluid;
  private float meltTemp;

  private SmelteryRecipe() {
  }

  @Nullable
  public static SmelteryRecipe get(ItemStack... ingredients) {
    return TechRegistries.SMELTERY.getValuesCollection().stream().filter(x -> x.isValidInput(ingredients)).findFirst().orElse(null);
  }

  /**
   * For JEI only
   */
  public IIngredient<ItemStack>[] getIngredients() {
    return ingredients;
  }

  public FluidStack getOutput() {
    return this.outputFluid.copy();
  }

  public float getMeltTemp() {
    return meltTemp;
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

  public static class Builder {

    private final SmelteryRecipe recipe;
    private final List<IIngredient<ItemStack>> listInput;

    public Builder() {
      recipe = new SmelteryRecipe();
      listInput = new ArrayList<>();
    }

    public Builder addInput(@Nonnull IIngredient<ItemStack> ingredient) {
      if (this.listInput.size() < 8) {
        this.listInput.add(ingredient);
      } else {
        throw new IllegalStateException("Smeltery recipes must have at most 8 ingredients!");
      }
      return this;
    }

    public Builder setOutput(@Nonnull FluidStack fluidStack, float meltTemp) {
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
