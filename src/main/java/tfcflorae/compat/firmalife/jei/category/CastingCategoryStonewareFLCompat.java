package tfcflorae.compat.firmalife.jei.category;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;

import net.dries007.tfc.compat.jei.BaseRecipeCategory;

import tfcflorae.compat.firmalife.jei.wrappers.CastingRecipeWrapperStonewareFL;

public class CastingCategoryStonewareFLCompat extends BaseRecipeCategory<CastingRecipeWrapperStonewareFL>
{
    private static final ResourceLocation ICONS = new ResourceLocation("tfc", "textures/gui/icons/jei.png");
    private final IDrawableStatic slot;
    private final IDrawableStatic arrow;
    private final IDrawableAnimated arrowAnimated;

    public CastingCategoryStonewareFLCompat(IGuiHelper helper, String Uid)
    {
        super(helper.createBlankDrawable(120, 38), Uid);
        this.arrow = helper.createDrawable(ICONS, 0, 14, 22, 16);
        IDrawableStatic arrowAnimated = helper.createDrawable(ICONS, 22, 14, 22, 16);
        this.arrowAnimated = helper.createAnimatedDrawable(arrowAnimated, 80, IDrawableAnimated.StartDirection.LEFT, false);
        this.slot = helper.getSlotDrawable();
    }

    public void drawExtras(Minecraft minecraft)
    {
        this.arrow.draw(minecraft, 48, 16);
        this.arrowAnimated.draw(minecraft, 48, 16);
        this.slot.draw(minecraft, 20, 16);
        this.slot.draw(minecraft, 84, 16);
    }

    public void setRecipe(IRecipeLayout recipeLayout, CastingRecipeWrapperStonewareFL recipeWrapper, IIngredients ingredients)
    {
        IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
        fluidStackGroup.init(0, false, 21, 17, 16, 16, ingredients.getInputs(VanillaTypes.FLUID).get(0).get(0).amount, false, null);
        fluidStackGroup.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.init(0, true, 84, 16);
        itemStackGroup.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}