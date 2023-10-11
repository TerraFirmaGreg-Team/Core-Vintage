package net.dries007.tfc.module.rock.plugin.jei.categories;

import mcp.MethodsReturnNonnullByDefault;
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
import net.dries007.tfc.module.rock.plugin.jei.wrappers.JEIRecipeWrapperChisel;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class JEIRecipeCategoryChisel extends RecipeCategoryBase<JEIRecipeWrapperChisel> {

    public static final String UID = Tags.MOD_ID + ".chisel";
    private static final ResourceLocation ICONS = Helpers.getID("textures/gui/icons/jei.png");

    private final IDrawableStatic slot;
    private final IDrawableStatic arrow;
    private final IDrawableAnimated arrowAnimated;

    public JEIRecipeCategoryChisel(IGuiHelper helper) {
        super(helper.createBlankDrawable(90, 38), UID);
        arrow = helper.createDrawable(ICONS, 0, 14, 22, 16);
        IDrawableStatic arrowAnimated = helper.createDrawable(ICONS, 22, 14, 22, 16);
        this.arrowAnimated = helper.createAnimatedDrawable(arrowAnimated, 80, IDrawableAnimated.StartDirection.LEFT, false);
        this.slot = helper.getSlotDrawable();
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 35, 16);
        arrowAnimated.draw(minecraft, 35, 16);
        slot.draw(minecraft, 10, 16);
        slot.draw(minecraft, 64, 16);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEIRecipeWrapperChisel recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.init(0, true, 10, 16);
        itemStackGroup.init(1, false, 64, 16);

        itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        itemStackGroup.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    @Override
    protected int getOutputSlotIndex() {
        return 0;
    }
}
