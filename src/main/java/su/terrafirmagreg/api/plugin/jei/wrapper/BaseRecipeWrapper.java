package su.terrafirmagreg.api.plugin.jei.wrapper;

import su.terrafirmagreg.api.base.recipe.IBaseRecipe;


import mezz.jei.api.recipe.IRecipeWrapper;

import lombok.Getter;

@Getter
public abstract class BaseRecipeWrapper<R extends IBaseRecipe> implements IRecipeWrapper {

  private final R recipe;

  public BaseRecipeWrapper(R recipe) {
    this.recipe = recipe;
  }

}
