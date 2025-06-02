package su.terrafirmagreg.modules.device.object.recipe.dryingmat;

import su.terrafirmagreg.api.base.object.recipe.api.IBaseRecipeManager;
import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;

import net.minecraft.item.ItemStack;


public interface IDryingMatRecipeManager extends IBaseRecipeManager<IDryingMatRecipe> {

  void addRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem, int duration);
}
