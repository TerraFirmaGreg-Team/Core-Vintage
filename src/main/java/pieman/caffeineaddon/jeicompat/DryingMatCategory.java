package pieman.caffeineaddon.jeicompat;

import su.terrafirmagreg.api.lib.Constants;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;


import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.compat.jei.BaseRecipeCategory;

public class DryingMatCategory extends BaseRecipeCategory<DryingMatRecipeWrapper> {

    private static final ResourceLocation ICONS = new ResourceLocation(Constants.MODID_TFC, "textures/gui/icons/jei.png");
    private final IDrawableStatic slot;
    private final IDrawableStatic arrow;
    private final IDrawableAnimated arrowAnimated;

    public DryingMatCategory(IGuiHelper helper, String Uid) {
        super(helper.createBlankDrawable(120, 38), Uid);
        arrow = helper.createDrawable(ICONS, 0, 14, 22, 16);
        IDrawableStatic arrowAnimated = helper.createDrawable(ICONS, 22, 14, 22, 16);
        this.arrowAnimated = helper.createAnimatedDrawable(arrowAnimated, 80, IDrawableAnimated.StartDirection.LEFT, false);
        this.slot = helper.getSlotDrawable();
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 50, 16);
        arrowAnimated.draw(minecraft, 50, 16);
        slot.draw(minecraft, 20, 16);
        slot.draw(minecraft, 84, 16);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, DryingMatRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.init(0, true, 20, 16);
        itemStackGroup.init(1, false, 84, 16);

        itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        itemStackGroup.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));

    }

}
