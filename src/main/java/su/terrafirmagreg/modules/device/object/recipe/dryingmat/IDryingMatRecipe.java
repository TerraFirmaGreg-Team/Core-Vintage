package su.terrafirmagreg.modules.device.object.recipe.dryingmat;

import su.terrafirmagreg.api.base.recipe.IBaseRecipe;

import net.minecraft.item.ItemStack;

public interface IDryingMatRecipe extends IBaseRecipe {

  int getDuration();


  ItemStack getOutputItem(ItemStack stack);

  boolean isValidInput(ItemStack inputItem);


}
