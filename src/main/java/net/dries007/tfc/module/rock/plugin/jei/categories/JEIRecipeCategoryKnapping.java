package net.dries007.tfc.module.rock.plugin.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.core.api.plugin.jei.RecipeCategoryBase;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.rock.plugin.jei.wrappers.JEIRecipeWrapperKnapping;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class JEIRecipeCategoryKnapping extends RecipeCategoryBase<JEIRecipeWrapperKnapping> {

    public static final String UID = Tags.MOD_ID + ".knap.stone";
    private static final ResourceLocation KNAP_TEXTURES = Helpers.getID("textures/gui/knapping.png");

    private final IDrawableStatic arrow, outputSlot;

    public JEIRecipeCategoryKnapping(IGuiHelper helper) {
        super(helper.createBlankDrawable(135, 82), UID);
        arrow = helper.createDrawable(KNAP_TEXTURES, 97, 44, 22, 15);
        outputSlot = helper.getSlotDrawable();
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        outputSlot.draw(minecraft, 116, 32);
        arrow.draw(minecraft, 86, 33);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEIRecipeWrapperKnapping recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.init(0, true, 116, 32);
        itemStackGroup.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    @Override
    protected int getOutputSlotIndex() {
        return 0;
    }
}
