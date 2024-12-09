package su.terrafirmagreg.modules.device.plugin.jei.dryingmat;

import su.terrafirmagreg.api.plugin.jei.wrapper.BaseRecipeWrapper;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.device.object.recipe.dryingmat.IDryingMatRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

import java.util.ArrayList;
import java.util.List;

public class DryingMatRecipeWrapper extends BaseRecipeWrapper<IDryingMatRecipe> {


  public DryingMatRecipeWrapper(IDryingMatRecipe recipe) {
    super(recipe);
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    IDryingMatRecipe recipe = getRecipe();

    List<List<ItemStack>> allInputs = new ArrayList<>();
    allInputs.add(recipe.getInputItem().getValidIngredients());

    ingredients.setInputLists(VanillaTypes.ITEM, allInputs);

    List<List<ItemStack>> allOutputs = new ArrayList<>();
    allOutputs.add(NonNullList.withSize(1, recipe.getOutputItem()));

    ingredients.setOutputLists(VanillaTypes.ITEM, allOutputs);
  }

  @Override
  public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    String text;
    if (getRecipe().getDuration() > 0) {
      text = I18n.format("jei.tooltips.tfc.barrel.duration", getRecipe().getDuration() / ICalendar.TICKS_IN_HOUR);
    } else {
      text = I18n.format("jei.tooltips.tfc.barrel.instant");
    }
    float x = 61f - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
    float y = 4f;
    minecraft.fontRenderer.drawString(text, x, y, 0x000000, false);
  }
}
