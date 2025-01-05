package su.terrafirmagreg.api.base.plugin.jei.wrapper.spi;


import su.terrafirmagreg.api.base.object.recipe.api.IBaseRecipe;

import mezz.jei.api.recipe.IRecipeWrapper;

import lombok.Getter;

@Getter
public abstract class BaseRecipeWrapper<R extends IBaseRecipe> implements IRecipeWrapper {

  private final R recipe;

  public BaseRecipeWrapper(R recipe) {

    this.recipe = recipe;
  }

}
