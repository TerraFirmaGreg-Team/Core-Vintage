package net.dries007.tfc.module.core.api.plugin.jei;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.startup.ForgeModIdHelper;
import mezz.jei.util.Translator;
import net.dries007.tfc.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class RecipeCategoryBase<T extends IRecipeWrapperBase> implements IRecipeCategory<T> {

    private final IDrawable background;
    private final String localizedName;
    private final String Uid;

    public RecipeCategoryBase(IDrawable background, String Uid) {
        this.background = background;
        this.localizedName = Translator.translateToLocal("jei.category." + Uid);
        this.Uid = Uid;
    }

    @Nonnull
    @Override
    public String getUid() {
        return Uid;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public String getModName() {
        return Tags.MOD_NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T recipeWrapper, IIngredients ingredients) {

        ResourceLocation registryName = recipeWrapper.getRegistryName();

        if (registryName != null) {
            IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

            guiItemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {

                if (slotIndex == RecipeCategoryBase.this.getOutputSlotIndex()) {
                    String recipeModId = registryName.getNamespace();
                    boolean modIdDifferent = false;
                    ResourceLocation itemRegistryName = ingredient.getItem().getRegistryName();

                    if (itemRegistryName != null) {
                        String itemModId = itemRegistryName.getNamespace();
                        modIdDifferent = !recipeModId.equals(itemModId);
                    }

                    if (modIdDifferent) {
                        String modName = ForgeModIdHelper.getInstance()
                                .getFormattedModNameForModId(recipeModId);

                        if (modName != null) {
                            tooltip.add(TextFormatting.GRAY + Translator.translateToLocalFormatted("jei.tooltip.recipe.by", modName));
                        }
                    }

                    boolean showAdvanced = Minecraft.getMinecraft().gameSettings.advancedItemTooltips || GuiScreen.isShiftKeyDown();
                    if (showAdvanced) {
                        tooltip.add(TextFormatting.DARK_GRAY + Translator.translateToLocalFormatted("jei.tooltip.recipe.id", registryName.toString()));
                    }
                }
            });
        }
    }


    protected abstract int getOutputSlotIndex();
}
