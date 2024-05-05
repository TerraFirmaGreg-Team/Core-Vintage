package tfctech.compat.jei.categories;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;


import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.compat.jei.BaseRecipeCategory;
import tfctech.compat.jei.wrappers.GlassworkingRecipeWrapper;

import static su.terrafirmagreg.api.data.Constants.MODID_TFCTECH;

public class GlassworkingCategory extends BaseRecipeCategory<GlassworkingRecipeWrapper> {

    private static final ResourceLocation ICONS = new ResourceLocation(MODID_TFCTECH, "textures/gui/elements.png");

    private final IDrawableStatic arrow, outputSlot;

    public GlassworkingCategory(IGuiHelper helper, String Uid) {
        super(helper.createBlankDrawable(135, 82), Uid);
        arrow = helper.createDrawable(ICONS, 0, 165, 22, 15);
        outputSlot = helper.getSlotDrawable();
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        outputSlot.draw(minecraft, 116, 32);
        arrow.draw(minecraft, 86, 33);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GlassworkingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.init(0, true, 116, 32);
        itemStackGroup.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
