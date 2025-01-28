package net.dries007.tfc.compat.jei.wrappers;

import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;

import net.minecraft.client.Minecraft;

import net.dries007.tfc.api.recipes.heat.HeatRecipe;

public class HeatRecipeWrapper extends SimpleRecipeWrapper {

  private final HeatRecipe recipe;

  public HeatRecipeWrapper(HeatRecipe recipeWrapper) {
    super(recipeWrapper);
    this.recipe = recipeWrapper;
  }

  @Override
  public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    float x = 60f;
    float y = 4f;
    String text = Heat.getTooltipAlternate(recipe.getTransformTemp());
    x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
    minecraft.fontRenderer.drawString(text, x, y, 0xFFFFFF, false);
  }
}
