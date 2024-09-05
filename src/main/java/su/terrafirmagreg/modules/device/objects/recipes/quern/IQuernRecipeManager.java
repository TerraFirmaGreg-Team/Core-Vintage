package su.terrafirmagreg.modules.device.objects.recipes.quern;

import su.terrafirmagreg.api.base.recipe.ICraftingProvider;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

public interface IQuernRecipeManager extends ICraftingProvider<IQuernRecipe> {

  void addRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem);
}
