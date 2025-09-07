package su.terrafirmagreg.framework.manager.plugin.spi.jei.wrapper.spi;


import su.terrafirmagreg.framework.manager.content.base.recipe.api.IBaseRecipe;

import mezz.jei.api.recipe.IRecipeWrapper;

import lombok.Getter;

@Getter
public abstract class BaseRecipeWrapper<R extends IBaseRecipe> implements IRecipeWrapper {

  private final R recipe;

  public BaseRecipeWrapper(R recipe) {

    this.recipe = recipe;
  }

}
