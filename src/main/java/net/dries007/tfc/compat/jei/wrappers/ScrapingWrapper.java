package net.dries007.tfc.compat.jei.wrappers;

import gregtech.api.unification.material.Materials;
import gregtech.common.items.ToolItems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.objects.items.ItemAnimalHide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ScrapingWrapper implements IRecipeWrapper {
    private final ItemStack inputKnife;
    private final ItemStack inputHide;
    private final ItemStack outputHide;

    public ScrapingWrapper(ItemAnimalHide inputHide, ItemAnimalHide outputHide) {
        this.inputHide = new ItemStack(inputHide);
        this.outputHide = new ItemStack(outputHide);

        inputKnife = ToolItems.KNIFE.get(Materials.Iron);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> allInputs = new ArrayList<>();

        allInputs.add(Collections.singletonList(inputHide));
        allInputs.add(Collections.singletonList(inputKnife));

        ingredients.setInputLists(VanillaTypes.ITEM, allInputs);
        ingredients.setOutput(VanillaTypes.ITEM, outputHide);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String text = I18n.format("jei.description.tfc.hide_scraping");
        int i = 0;
        for (String a : minecraft.fontRenderer.listFormattedStringToWidth(text, 150)) {
            minecraft.fontRenderer.drawString(a, 1, 60f + (minecraft.fontRenderer.FONT_HEIGHT + 2) * i, 0x000000, false);
            i++;
        }
    }
}
