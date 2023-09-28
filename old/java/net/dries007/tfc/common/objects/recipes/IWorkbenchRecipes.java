package net.dries007.tfc.common.objects.recipes;

import gregtech.api.recipes.ModHandler;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IWorkbenchRecipes extends IRecipes {
    default void registerShaped(String recipeName, @Nonnull ItemStack output, Object... recipePattern) {
        ModHandler.addShapedRecipe(recipeName, output, recipePattern);
    }

    default void registerShapeless(String recipeName, @Nonnull ItemStack output, Object... recipePattern) {
        ModHandler.addShapelessRecipe(recipeName, output, recipePattern);
    }
}
