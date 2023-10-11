package net.dries007.tfc.compat.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.compat.jei.util.BaseRecipeCategory;
import net.dries007.tfc.compat.jei.wrappers.UnmoldRecipeWrapper;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class UnmoldCategory extends BaseRecipeCategory<UnmoldRecipeWrapper> {

    private static final ResourceLocation ICONS = Helpers.getID("textures/gui/icons/jei.png");

    private final IDrawableStatic slot;
    private final IDrawableStatic arrow;
    private final IDrawableAnimated arrowAnimated;

    public UnmoldCategory(IGuiHelper helper, String Uid) {
        super(helper.createBlankDrawable(122, 62), Uid);

        var arrowAnimatedDrawable = helper.createDrawable(ICONS, 22, 14, 22, 16);

        arrow = helper.createDrawable(ICONS, 0, 14, 22, 16);
        arrowAnimated = helper.createAnimatedDrawable(arrowAnimatedDrawable, 80, IDrawableAnimated.StartDirection.LEFT, false);

        slot = helper.getSlotDrawable();
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        // Input
        slot.draw(minecraft, 25, 22);

        arrow.draw(minecraft, 50, 22);
        arrowAnimated.draw(minecraft, 50, 22);

        // Output
        slot.draw(minecraft, 79, 22);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, UnmoldRecipeWrapper unmoldRecipeWrapper, IIngredients iIngredients) {
        var itemStackGroup = iRecipeLayout.getItemStacks();

        itemStackGroup.init(0, true, 25, 22);
        itemStackGroup.set(0, iIngredients.getInputs(VanillaTypes.ITEM).get(0));

        itemStackGroup.init(1, false, 79, 22);
        itemStackGroup.set(1, iIngredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
