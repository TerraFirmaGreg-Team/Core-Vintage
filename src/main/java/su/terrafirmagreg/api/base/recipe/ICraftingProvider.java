package su.terrafirmagreg.api.base.recipe;

import java.util.Collection;

public interface ICraftingProvider<T extends IBaseRecipe> {

    boolean addRecipe(T recipe);

    boolean removeRecipe(T recipe);

    /**
     * @return an unmodifiable collection of all recipes registered to the crafting provider.
     */
    Collection<T> recipes();
}
