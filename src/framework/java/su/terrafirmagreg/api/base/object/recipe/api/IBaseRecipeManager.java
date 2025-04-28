package su.terrafirmagreg.api.base.object.recipe.api;

import java.util.Collection;

public interface IBaseRecipeManager<T extends IBaseRecipe> {

  void addRecipe(T recipe);

  boolean removeRecipe(T recipe);

  /**
   * @return an unmodifiable collection of all recipes registered to the crafting provider.
   */
  Collection<T> recipes();
}
