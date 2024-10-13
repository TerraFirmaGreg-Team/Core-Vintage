/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.patchouli;

import java.util.Collections;
import javax.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.VariableHolder;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@SuppressWarnings("unused")
public class WeldingComponent extends CustomComponent
{
    @VariableHolder
    @SerializedName("recipe")
    public String recipeName;

    @Nullable
    protected transient WeldingRecipe recipe;

    @Override
    public void build(int componentX, int componentY, int pageNum)
    {
        super.build(componentX, componentY, pageNum);
        recipe = TFCRegistries.WELDING.getValue(new ResourceLocation(recipeName));
    }

    @Override
    public void render(IComponentRenderContext context, float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, 0);
        GlStateManager.enableBlend();
        GlStateManager.color(1f, 1f, 1f, 1f);

        context.getGui().mc.getTextureManager().bindTexture(TFCPatchouliPlugin.BOOK_UTIL_TEXTURES);
        Gui.drawModalRectWithCustomSizedTexture(9, 0, 0, 116, 98, 26, 256, 256);

        if (recipe != null)
        {
            Ingredient ingredient1 = TFCPatchouliPlugin.getIngredient(recipe.getIngredients().get(0));
            Ingredient ingredient2 = TFCPatchouliPlugin.getIngredient(recipe.getIngredients().get(1));
            ItemStack outputStack = recipe.getOutputs().get(0);

            context.renderIngredient(14, 5, mouseX, mouseY, ingredient1);
            context.renderIngredient(42, 5, mouseX, mouseY, ingredient2);
            context.renderItemStack(86, 5, mouseX, mouseY, outputStack);
        }
        else
        {
            // If removed, render the indicator instead
            Gui.drawModalRectWithCustomSizedTexture(11, 2, 2, 144, 22, 22, 256, 256);
            Gui.drawModalRectWithCustomSizedTexture(39, 2, 2, 144, 22, 22, 256, 256);
            Gui.drawModalRectWithCustomSizedTexture(83, 2, 2, 144, 22, 22, 256, 256);
            if (context.isAreaHovered(mouseX, mouseY, 11, 2, 22, 22)
                || context.isAreaHovered(mouseX, mouseY, 39, 2, 22, 22)
                || context.isAreaHovered(mouseX, mouseY, 83, 2, 22, 22))
            {
                context.setHoverTooltip(Collections.singletonList(I18n.format(MOD_ID + ".patchouli.recipe_removed")));
            }
        }
        GlStateManager.popMatrix();
    }
}
