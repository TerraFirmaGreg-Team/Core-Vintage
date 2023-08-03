package net.dries007.tfc.api.recipes.heat;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ceramics.ItemSmallVessel;
import net.minecraft.item.ItemStack;

@ParametersAreNonnullByDefault
public class HeatRecipeVessel extends HeatRecipe {
    public HeatRecipeVessel(IIngredient<ItemStack> ingredient, float transformTemp, int minTier) {
        super(ingredient, transformTemp, minTier);
    }

    @Override
    @Nonnull
    public ItemStack getOutputStack(ItemStack input) {
        ItemStack output = input.copy();
        if (output.getItem() instanceof ItemSmallVessel) {
            return ((ItemSmallVessel) output.getItem()).getFiringResult(output);
        }
        return output;
    }
}
