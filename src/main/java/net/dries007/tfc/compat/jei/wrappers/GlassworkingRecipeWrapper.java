package net.dries007.tfc.compat.jei.wrappers;

import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.recipes.GlassworkingRecipe;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

public class GlassworkingRecipeWrapper implements IRecipeWrapper {

  private static final ResourceLocation GLASS_TEXTURE = new ResourceLocation(TFCTECH, "textures/gui/glassworking/button.png");
  private static final ResourceLocation GLASS_DISABLED_TEXTURE = new ResourceLocation(TFCTECH, "textures/gui/glassworking/disabled.png");

  protected final GlassworkingRecipe recipe;
  private final IDrawable squareHigh, squareLow;

  public GlassworkingRecipeWrapper(GlassworkingRecipe recipe, IGuiHelper helper) {
    this.recipe = recipe;

    this.squareHigh = helper.drawableBuilder(GLASS_TEXTURE, 0, 0, 16, 16).setTextureSize(16, 16).build();
    this.squareLow = helper.drawableBuilder(GLASS_DISABLED_TEXTURE, 0, 0, 16, 16).setTextureSize(16, 16).build();
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    ItemStack output = recipe.getOutput();
    ingredients.setOutput(VanillaTypes.ITEM, output);

    ingredients.setInput(VanillaTypes.FLUID, new FluidStack(FluidsCore.GLASS.get(), 250));
  }

  @Override
  public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    for (int y = 0; y < recipe.getMatrix().getHeight(); y++) {
      for (int x = 0; x < recipe.getMatrix().getWidth(); x++) {
        if (recipe.getMatrix().get(x, y) && squareHigh != null) {
          squareHigh.draw(minecraft, 1 + x * 16, 1 + y * 16);
        } else if (squareLow != null) {
          squareLow.draw(minecraft, 1 + x * 16, 1 + y * 16);
        }
      }
    }
  }
}
