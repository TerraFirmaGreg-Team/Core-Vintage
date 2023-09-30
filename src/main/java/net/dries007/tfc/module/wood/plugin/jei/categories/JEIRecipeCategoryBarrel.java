package net.dries007.tfc.module.wood.plugin.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.core.api.plugin.jei.RecipeCategoryBase;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.wood.plugin.jei.wrappers.JEIRecipeWrapperBarrel;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class JEIRecipeCategoryBarrel extends RecipeCategoryBase<JEIRecipeWrapperBarrel> {

    public static final String UID = Tags.MOD_ID + ".barrel";
    private static final ResourceLocation ICONS = Helpers.getID("textures/gui/icons/jei.png");
    private static final ResourceLocation BARREL_TEXTURES = Helpers.getID("textures/gui/barrel.png");

    private final IDrawableStatic fluidSlotBackgroound, fluidSlot;
    private final IDrawableStatic slot;
    private final IDrawableStatic arrow;
    private final IDrawableAnimated arrowAnimated;

    public JEIRecipeCategoryBarrel(IGuiHelper helper) {
        super(helper.createBlankDrawable(122, 62), UID);
        fluidSlotBackgroound = helper.createDrawable(BARREL_TEXTURES, 7, 15, 18, 60);
        fluidSlot = helper.createDrawable(BARREL_TEXTURES, 176, 0, 18, 53);
        arrow = helper.createDrawable(ICONS, 0, 14, 22, 16);
        IDrawableStatic arrowAnimated = helper.createDrawable(ICONS, 22, 14, 22, 16);
        this.arrowAnimated = helper.createAnimatedDrawable(arrowAnimated, 80, IDrawableAnimated.StartDirection.LEFT, false);
        this.slot = helper.getSlotDrawable();
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        //Input
        fluidSlotBackgroound.draw(minecraft, 1, 1);
        fluidSlot.draw(minecraft, 1, 5);
        slot.draw(minecraft, 25, 22);

        arrow.draw(minecraft, 50, 22);
        arrowAnimated.draw(minecraft, 50, 22);

        //Output
        slot.draw(minecraft, 79, 22);
        fluidSlotBackgroound.draw(minecraft, 103, 1);
        fluidSlot.draw(minecraft, 103, 5);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEIRecipeWrapperBarrel recipeWrapper, IIngredients ingredients) {
        super.setRecipe(recipeLayout, recipeWrapper, ingredients);
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
        int fluidSlot = 0;
        int itemSlot = 0;

        // Shows input fluid (slot + fluidstack) only if the recipe has one
        if (!ingredients.getInputs(VanillaTypes.FLUID).isEmpty()) {
            List<FluidStack> inputFluid = ingredients.getInputs(VanillaTypes.FLUID).get(0);
            fluidStackGroup.init(fluidSlot, true, 6, 6, 8, 50, inputFluid.get(0).amount, false, null);
            fluidStackGroup.set(fluidSlot, inputFluid);
            fluidSlot++;
        }

        if (recipeWrapper.isFluidMixing()) {
            // If this is a fluid mixing recipe, fill the input slot with the "bucket" fluid
            List<FluidStack> inputFluid = ingredients.getInputs(VanillaTypes.FLUID).get(1);
            fluidStackGroup.init(fluidSlot, true, 26, 23, 16, 16, inputFluid.get(0).amount, false, null);
            fluidStackGroup.set(fluidSlot, inputFluid);
            fluidSlot++;
        } else {
            // Draws the input slot and stack othewise
            itemStackGroup.init(itemSlot, true, 25, 22);
            if (!ingredients.getInputs(VanillaTypes.ITEM).isEmpty()) {
                itemStackGroup.set(itemSlot, ingredients.getInputs(VanillaTypes.ITEM).get(0));
            }
            itemSlot++;
        }

        // Shows output fluid (slot + fluidstack) only if the recipe has one
        if (!ingredients.getOutputs(VanillaTypes.FLUID).isEmpty()) {
            List<FluidStack> outputFluid = ingredients.getOutputs(VanillaTypes.FLUID).get(0);
            fluidStackGroup.init(fluidSlot, false, 108, 6, 8, 50, outputFluid.get(0).amount, false, null);
            fluidStackGroup.set(fluidSlot, outputFluid);
        }

        // Draws the output slot and stack
        itemStackGroup.init(itemSlot, false, 79, 22);
        if (!ingredients.getOutputs(VanillaTypes.ITEM).isEmpty()) {
            itemStackGroup.set(itemSlot, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
        }
    }

    @Override
    protected int getOutputSlotIndex() {
        return 0;
    }
}
