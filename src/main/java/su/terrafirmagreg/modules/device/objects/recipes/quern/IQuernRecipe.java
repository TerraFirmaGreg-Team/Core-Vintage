package su.terrafirmagreg.modules.device.objects.recipes.quern;

import su.terrafirmagreg.api.base.recipe.IBaseRecipe;

import net.minecraft.item.ItemStack;

public interface IQuernRecipe extends IBaseRecipe {

  ItemStack getOutputItem(ItemStack stack);

  boolean isValidInput(ItemStack inputItem);

}
