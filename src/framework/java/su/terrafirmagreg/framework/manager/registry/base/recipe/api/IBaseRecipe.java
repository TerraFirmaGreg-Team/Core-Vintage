package su.terrafirmagreg.framework.manager.registry.base.recipe.api;

import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;


public interface IBaseRecipe {

  IIngredient<ItemStack> getInputItem();

//  ItemStack getInputItem();

  ItemStack getOutputItem();

  ResourceLocation getRecipeName();
}
