package su.terrafirmagreg.api.plugin.jei.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;

import lombok.Getter;

@Getter
public abstract class BaseRecipeWrapper<R> implements IRecipeWrapper {

    private final R recipe;

    public BaseRecipeWrapper(R recipe) {
        this.recipe = recipe;
    }

}
