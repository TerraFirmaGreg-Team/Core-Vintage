package su.terrafirmagreg.modules.metal.plugin.jei.welding;

import su.terrafirmagreg.api.plugin.jei.category.BaseRecipeCategory;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.init.ItemsDevice;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;


import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

public class WeldingRecipeCategory
        extends BaseRecipeCategory<WeldingRecipeWrapper> {

  public static final String UID = ModUtils.localize("welding");

  private final IDrawableStatic slot;
  private final IDrawableStatic arrow;
  private final IDrawableAnimated arrowAnimated;

  public WeldingRecipeCategory(IGuiHelper helper) {
    super(helper.createBlankDrawable(120, 38), UID);

    this.arrow = helper.createDrawable(ICONS, 0, 14, 22, 16);
    IDrawableStatic arrowAnimated = helper.createDrawable(ICONS, 22, 14, 22, 16);
    this.arrowAnimated = helper.createAnimatedDrawable(arrowAnimated, 80,
            IDrawableAnimated.StartDirection.LEFT, false);
    this.slot = helper.getSlotDrawable();
  }

  @Override
  public void drawExtras(Minecraft minecraft) {
    arrow.draw(minecraft, 50, 16);
    arrowAnimated.draw(minecraft, 50, 16);
    slot.draw(minecraft, 0, 16);
    slot.draw(minecraft, 20, 16);
    slot.draw(minecraft, 84, 16);
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, WeldingRecipeWrapper recipeWrapper,
          IIngredients ingredients) {
    IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
    itemStackGroup.init(0, true, 0, 16);
    itemStackGroup.init(1, true, 20, 16);
    itemStackGroup.init(2, false, 84, 16);

    itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
    itemStackGroup.set(1,
            new ItemStack(ItemsDevice.HANDSTONE)); // todo: use all ore dict entries with "handstone"
    itemStackGroup.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
  }

}
