package su.terrafirmagreg.api.base.object.recipe.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;


public interface IBaseRecipe {

//  IIngredient<ItemStack> getInputItem();

  ItemStack getInputItem();

  ItemStack getOutputItem();

  ResourceLocation getRecipeName();
}
