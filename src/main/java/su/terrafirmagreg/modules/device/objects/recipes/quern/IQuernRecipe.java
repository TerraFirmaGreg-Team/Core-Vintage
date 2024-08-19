package su.terrafirmagreg.modules.device.objects.recipes.quern;

import su.terrafirmagreg.api.base.recipe.IBaseRecipe;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

public interface IQuernRecipe extends IBaseRecipe {

    IIngredient<ItemStack> getInputItem();

    ItemStack getOutputItem();

    ItemStack getOutputItem(ItemStack stack);

    boolean isValidInput(ItemStack inputItem);

}
