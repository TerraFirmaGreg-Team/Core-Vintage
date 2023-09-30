package net.dries007.tfc.module.wood.plugin.jei.wrappers;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.api.recipes.LoomRecipe;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.module.core.api.plugin.jei.IRecipeWrapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class JEIRecipeWrapperLoam implements IRecipeWrapperBase {
    private final LoomRecipe recipe;

    public JEIRecipeWrapperLoam(LoomRecipe recipe) {
        this.recipe = recipe;

    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> allInputs = new ArrayList<>();
        List<IIngredient<ItemStack>> listInputs = recipe.getIngredients();
        for (IIngredient<ItemStack> input : listInputs) {
            allInputs.add(input.getValidIngredients());
        }
        ingredients.setInputLists(VanillaTypes.ITEM, allInputs);

        List<List<ItemStack>> allOutputs = new ArrayList<>();
        List<ItemStack> listOutputs = recipe.getOutputs();
        for (ItemStack stack : listOutputs) {
            allOutputs.add(NonNullList.withSize(1, stack));
        }
        ingredients.setOutputLists(VanillaTypes.ITEM, allOutputs);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.recipe.getRegistryName();
    }
}
