package tfctech.compat.jei.wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import tfctech.api.recipes.SmelteryRecipe;

import java.util.ArrayList;
import java.util.List;

public class SmelteryRecipeWrapper implements IRecipeWrapper {

    private final SmelteryRecipe recipe;

    public SmelteryRecipeWrapper(SmelteryRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> allInputs = new ArrayList<>();
        for (IIngredient<ItemStack> ingredient : recipe.getIngredients()) {
            allInputs.add(ingredient.getValidIngredients());
        }
        ingredients.setInputLists(VanillaTypes.ITEM, allInputs);

        List<List<FluidStack>> allOutputFluids = new ArrayList<>();
        allOutputFluids.add(NonNullList.withSize(1, recipe.getOutput()));
        ingredients.setOutputLists(VanillaTypes.FLUID, allOutputFluids);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        float x = 44f;
        float y = 3f;
        String text = Heat.getTooltip(recipe.getMeltTemp());
        //noinspection ConstantConditions
        x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
        minecraft.fontRenderer.drawString(text, x, y, 0xFFFFFF, false);
    }
}
