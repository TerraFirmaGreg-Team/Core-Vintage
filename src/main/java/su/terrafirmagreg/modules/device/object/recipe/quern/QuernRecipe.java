package su.terrafirmagreg.modules.device.object.recipe.quern;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import lombok.Getter;

@Getter
public class QuernRecipe implements IQuernRecipe {

  protected final ResourceLocation recipeName;
  protected IIngredient<ItemStack> inputItem;
  protected ItemStack outputItem;

  public QuernRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem) {
    this.inputItem = inputItem;
    this.outputItem = outputItem;
    this.recipeName = null;

    if (inputItem == null || outputItem == null) {
      throw new IllegalArgumentException("Input and output are not allowed to be empty");
    }
  }

  public ItemStack getOutputItem(ItemStack stack) {
    return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());

  }

  public boolean isValidInput(ItemStack inputItem) {
    return this.inputItem.test(inputItem);
  }

  @Override
  public ResourceLocation getRecipeName() {
    return null;
  }
}
