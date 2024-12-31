package tfctech.compat.jei.categories;

import javax.annotation.ParametersAreNonnullByDefault;

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
import net.dries007.tfc.compat.jei.wrappers.SimpleRecipeWrapper;
import su.terrafirmagreg.old.api.data.Reference;

@ParametersAreNonnullByDefault
public class WireDrawingCategory extends BaseRecipeCategory<SimpleRecipeWrapper>
{
    private static final ResourceLocation ICONS = new ResourceLocation(Reference.TFCTECH, "textures/gui/elements.png");

    private final IDrawableStatic slot;
    private final IDrawableStatic arrow;
    private final IDrawableAnimated arrowAnimated;

    public WireDrawingCategory(IGuiHelper helper, String Uid)
    {
        super(helper.createBlankDrawable(140, 38), Uid);
        arrow = helper.createDrawable(ICONS, 0, 165, 22, 16);
        IDrawableStatic arrowAnimated = helper.createDrawable(ICONS, 22, 165, 22, 16);
        this.arrowAnimated = helper.createAnimatedDrawable(arrowAnimated, 80, IDrawableAnimated.StartDirection.LEFT, false);
        this.slot = helper.getSlotDrawable();
    }

    @Override
    public void drawExtras(Minecraft minecraft)
    {
        arrow.draw(minecraft, 77, 16);
        arrowAnimated.draw(minecraft, 77, 16);
        slot.draw(minecraft, 27, 16);
        slot.draw(minecraft, 47, 16);
        slot.draw(minecraft, 111, 16);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SimpleRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.init(0, true, 27, 16);
        itemStackGroup.init(1, true, 47, 16);
        itemStackGroup.init(2, false, 111, 16);

        itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 1)
        {
            itemStackGroup.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));
        }
        itemStackGroup.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
