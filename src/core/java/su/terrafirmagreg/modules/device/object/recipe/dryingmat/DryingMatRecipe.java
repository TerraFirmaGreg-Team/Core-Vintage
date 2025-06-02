package su.terrafirmagreg.modules.device.object.recipe.dryingmat;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public class DryingMatRecipe implements IDryingMatRecipe {

  protected final ResourceLocation recipeName;
  protected final IIngredient<ItemStack> inputItem;
  protected final ItemStack outputItem;
  protected final int duration;

  public DryingMatRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem, int duration) {
    this.inputItem = inputItem;
    this.outputItem = outputItem;
    this.duration = duration;

    this.recipeName = null;

    if (inputItem == null || outputItem == null) {
      throw new IllegalArgumentException("Input and output are not allowed to be empty");
    }
  }

  @NotNull
  @Override
  public ItemStack getOutputItem(ItemStack stack) {
    return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());
  }

  @Override
  public boolean isValidInput(ItemStack inputItem) {
    return this.inputItem.testIgnoreCount(inputItem);
  }


}
