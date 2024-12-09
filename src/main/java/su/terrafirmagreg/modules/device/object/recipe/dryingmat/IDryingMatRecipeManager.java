package su.terrafirmagreg.modules.device.object.recipe.dryingmat;

import su.terrafirmagreg.api.base.recipe.ICraftingProvider;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

public interface IDryingMatRecipeManager extends ICraftingProvider<IDryingMatRecipe> {

  void addRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem, int duration);
}
