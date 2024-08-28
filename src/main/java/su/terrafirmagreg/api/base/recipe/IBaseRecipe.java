package su.terrafirmagreg.api.base.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

public interface IBaseRecipe {

    IIngredient<ItemStack> getInputItem();

    ItemStack getOutputItem();

    ResourceLocation getRecipeName();
}
