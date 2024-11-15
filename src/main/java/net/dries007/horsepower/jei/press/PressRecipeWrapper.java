package net.dries007.horsepower.jei.press;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.horsepower.Configs;
import net.dries007.horsepower.jei.HorsePowerCategory;
import net.dries007.horsepower.jei.HorsePowerPlugin;
import net.dries007.horsepower.recipes.PressRecipe;
import net.dries007.horsepower.util.Localization;
import net.dries007.horsepower.util.color.Colors;

import java.util.Collections;
import java.util.List;

public class PressRecipeWrapper implements IRecipeWrapper {

  public final boolean isFluid;
  private final List<List<ItemStack>> inputs;
  private final ItemStack output;
  private final FluidStack fluidOutput;
  private final double printLaps;
  private final IDrawableAnimated arrow;

  public PressRecipeWrapper(PressRecipe recipe) {
    this(Collections.singletonList(recipe.getInput()), recipe.getOutput(), recipe.getOutputFluid());
  }

  public PressRecipeWrapper(List<ItemStack> inputs, ItemStack output, FluidStack fluidOutput) {
    this.inputs = Collections.singletonList(inputs);
    this.output = output;
    this.fluidOutput = fluidOutput;
    this.isFluid = fluidOutput != null;

    IGuiHelper guiHelper = HorsePowerPlugin.guiHelper;
    IDrawableStatic arrowDrawable = guiHelper.createDrawable(HorsePowerCategory.COMPONENTS, 60, 0, 24, 17);
    double time = Configs.general.pointsForPress > 0 ? Configs.general.pointsForPress : 1;
    int laps = (int) ((time / 8D) * 100);
    printLaps = (double) Math.round((time / 8D) * 100.0D) / 100.0D;
    arrow = guiHelper.createAnimatedDrawable(arrowDrawable, laps, IDrawableAnimated.StartDirection.LEFT, false);
  }

  @Override
  public void getIngredients(IIngredients ingredients) {
    ingredients.setInputLists(VanillaTypes.ITEM, inputs);
    if (isFluid) {
      ingredients.setOutput(VanillaTypes.FLUID, fluidOutput);
    } else {
      ingredients.setOutput(VanillaTypes.ITEM, output);
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    arrow.draw(minecraft, isFluid ? 61 : 57, 32);
    minecraft.fontRenderer.drawStringWithShadow("x" + printLaps, 58, 23, Colors.WHITE.getRGB());
  }

  @Override
  public List<String> getTooltipStrings(int mouseX, int mouseY) {
    List<String> tooltip = Lists.newArrayList();
    if (mouseX >= 55 && mouseY >= 21 && mouseX < 80 && mouseY < 33) {
      tooltip.add(Localization.GUI.JEI.PRESSING.translate(printLaps, printLaps >= 2D));
    }
    return tooltip;
  }

  @Override
  public int hashCode() {
    int result = inputs.hashCode();
    result = 31 * result + output.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PressRecipeWrapper that)) {
      return false;
    }

    boolean flag = true;
    for (ItemStack stack : inputs.get(0)) {
      for (ItemStack stack1 : that.inputs.get(0)) {
        if (stack1.getMetadata() == OreDictionary.WILDCARD_VALUE && !OreDictionary.itemMatches(stack, stack1, false)) {
          flag = false;
        }
      }
    }

    return flag && (output != null && that.output != null && output.equals(that.output) ||
                    (fluidOutput != null && that.fluidOutput != null && fluidOutput.equals(that.fluidOutput)));
  }
}
