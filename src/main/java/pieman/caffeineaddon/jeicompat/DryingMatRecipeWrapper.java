package pieman.caffeineaddon.jeicompat;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import pieman.caffeineaddon.recipes.DryingMatRecipe;

import java.util.Collections;

public class DryingMatRecipeWrapper implements IRecipeWrapper {
	private DryingMatRecipe recipe;

	public DryingMatRecipeWrapper(DryingMatRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM,
				Collections.singletonList(recipe.getItemIngredient().getValidIngredients()));
		if (recipe.getOutputStack() != ItemStack.EMPTY) {
			ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutputStack());
		}
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		String text;
		if (recipe.getDuration() > 0) {
			text = I18n.format("jei.tooltips.tfc.barrel.duration", recipe.getDuration() / ICalendar.TICKS_IN_HOUR);
		} else {
			text = I18n.format("jei.tooltips.tfc.barrel.instant");
		}
		float x = 61f - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
		float y = 4f;
		minecraft.fontRenderer.drawString(text, x, y, 0x000000, false);
	}
}
