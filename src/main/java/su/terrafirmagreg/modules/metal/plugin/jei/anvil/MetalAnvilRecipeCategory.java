package su.terrafirmagreg.modules.metal.plugin.jei.anvil;

import su.terrafirmagreg.api.plugin.jei.category.BaseRecipeCategory;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.metal.client.gui.GuiMetalAnvil;


import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

public class MetalAnvilRecipeCategory
        extends BaseRecipeCategory<MetalAnvilRecipeWrapper> {

  public static final String UID = ModUtils.localize("metal.anvil");

  public MetalAnvilRecipeCategory(IGuiHelper helper) {
    super(helper.createDrawable(GuiMetalAnvil.BACKGROUND, 11, 7, 154, 80), UID);
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, MetalAnvilRecipeWrapper recipeWrapper,
          IIngredients ingredients) {
    IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
    itemStackGroup.init(0, true, 19, 60);
    itemStackGroup.init(1, true, 117, 60);
    itemStackGroup.init(2, false, 19, 42);

    itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
    itemStackGroup.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));
    itemStackGroup.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
  }

}
