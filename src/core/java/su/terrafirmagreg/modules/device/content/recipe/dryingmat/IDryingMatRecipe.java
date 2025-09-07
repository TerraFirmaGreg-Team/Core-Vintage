package su.terrafirmagreg.modules.device.content.recipe.dryingmat;

import su.terrafirmagreg.framework.manager.content.base.recipe.api.IBaseRecipe;

import net.minecraft.item.ItemStack;

public interface IDryingMatRecipe extends IBaseRecipe {

  int getDuration();


  ItemStack getOutputItem(ItemStack stack);

  boolean isValidInput(ItemStack inputItem);


}
