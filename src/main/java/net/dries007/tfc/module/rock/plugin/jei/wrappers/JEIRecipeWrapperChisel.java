package net.dries007.tfc.module.rock.plugin.jei.wrappers;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.module.rock.api.recipes.RecipeRockChisel;
import net.dries007.tfc.compat.jei.JEIIntegration;
import net.dries007.tfc.module.core.api.plugin.jei.IRecipeWrapperBase;
import net.dries007.tfc.module.core.init.BlocksCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class JEIRecipeWrapperChisel implements IRecipeWrapperBase {
    private final RecipeRockChisel recipe;
    private final List<ItemStack> ingredients;
    private final ItemStack output;

    public JEIRecipeWrapperChisel(RecipeRockChisel recipe) {
        this.recipe = recipe;
        ingredients = new ArrayList<>();
        // Although this looks resource-intensive, it's done one time only
        JEIIntegration.getAllIngredients().stream()
                .filter(stack -> stack.getItem() instanceof ItemBlock)
                .forEach(stack ->
                {
                    Block block = ((ItemBlock) stack.getItem()).getBlock();
                    if (recipe.matches(block.getDefaultState())) {
                        ingredients.add(stack);
                    }
                });
        // Ideally we should use Block#getPickBlock but we can't have a World and EntityPlayer at this point
        ItemStack recipeOutput = new ItemStack(recipe.getOutputState().getBlock());
        if (recipeOutput.isEmpty()) {
            // Failed to grab the output block, using debug block
            recipeOutput = new ItemStack(BlocksCore.DEBUG);
        }
        this.output = recipeOutput;
    }


    @Override
    public void getIngredients(IIngredients recipeIngredients) {
        recipeIngredients.setInputs(VanillaTypes.ITEM, ingredients);
        recipeIngredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return this.recipe.getRegistryName();
    }
}
