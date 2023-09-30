package net.dries007.tfc.module.wood.plugin.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.core.api.plugin.jei.RecipeCategoryBase;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.wood.plugin.jei.wrappers.JEIRecipeWrapperLoam;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class JEIRecipeCategoryLoom extends RecipeCategoryBase<JEIRecipeWrapperLoam> {

    public static final String UID = Tags.MOD_ID + ".loom";
    private static final ResourceLocation ICONS = Helpers.getID("textures/gui/icons/jei.png");

    private final IDrawableStatic slot;
    private final IDrawableStatic arrow;
    private final IDrawableAnimated arrowAnimated;

    public JEIRecipeCategoryLoom(IGuiHelper helper) {
        super(helper.createBlankDrawable(120, 38), UID);
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

    @ParametersAreNonnullByDefault
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEIRecipeWrapperLoam recipeWrapper, IIngredients ingredients) {
        super.setRecipe(recipeLayout, recipeWrapper, ingredients);
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.init(0, true, 20, 16);
        itemStackGroup.init(1, false, 84, 16);

        itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        itemStackGroup.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    @Override
    protected int getOutputSlotIndex() {
        return 1;
    }
}
