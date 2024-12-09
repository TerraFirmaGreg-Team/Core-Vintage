package su.terrafirmagreg.modules.device.object.recipe.dryingmat;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

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
