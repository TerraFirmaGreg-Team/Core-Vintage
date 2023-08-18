///*
// * Work under Copyright. Licensed under the EUPL.
// * See the project README.md and LICENSE.txt for more information.
// */
//
//package net.dries007.tfc.compat.jei.wrappers;
//
//import mezz.jei.api.ingredients.IIngredients;
//import mezz.jei.api.ingredients.VanillaTypes;
//import mezz.jei.api.recipe.IRecipeWrapper;
//import net.dries007.tfc.api.types.Rock;
//import net.dries007.tfc.api.types.RockCategory;
//import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
//import net.dries007.tfc.objects.items.rock.ItemRock;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.item.ItemStack;
//
//import javax.annotation.ParametersAreNonnullByDefault;
//import java.util.ArrayList;
//import java.util.List;
//
//@ParametersAreNonnullByDefault
//public class RockLayerWrapper implements IRecipeWrapper
//{
//    private final Rock rock;
//
//    public RockLayerWrapper(Rock rock)
//    {
//        this.rock = rock;
//    }
//
//
//    @Override
//    public void getIngredients(IIngredients recipeIngredients)
//    {
//        List<ItemStack> input = new ArrayList<>();
//        input.add(new ItemStack(BlockRockVariant.get(rock, Rock.Type.RAW)));
//        input.add(new ItemStack(ItemRock.get(rock)));
//        recipeIngredients.setInputs(VanillaTypes.ITEM, input); // This will only show the raw block, but let use right click stones to open the "recipe"
//    }
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
//    {
//        final int newLine = 11;
//        float x = 33f;
//        float y = 6f;
//        // Draw Rock Category
//        String text = I18n.format("jei.tooltips.tfc.rock_layer.category");
//        x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
//        minecraft.fontRenderer.drawString(text, x, y, 0x00C300, false);
//
//        text = I18n.format(rock.getCategory().getTranslationKey());
//        List<String> listString = minecraft.fontRenderer.listFormattedStringToWidth(text, 64); // To fit igneous intrusive/extrusive
//        for (String str : listString)
//        {
//            x = 33f;
//            y += newLine;
//            x = x - minecraft.fontRenderer.getStringWidth(str) / 2.0f;
//            minecraft.fontRenderer.drawString(str, x, y, 0xFFFFFF, false);
//        }
//
//        // Draw Layers
//        x = 128f;
//        y = 6;
//        text = I18n.format("jei.tooltips.tfc.rock_layer.layers");
//        x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
//        minecraft.fontRenderer.drawString(text, x, y, 0x00C300, false);
//
//        if (RockCategory.Layer.TOP.test(rock))
//        {
//            x = 128f;
//            y += newLine;
//            text = I18n.format("jei.tooltips.tfc.rock_layer.top");
//            x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
//            minecraft.fontRenderer.drawString(text, x, y, 0xFFFFFF, false);
//        }
//        if (RockCategory.Layer.MIDDLE.test(rock))
//        {
//            x = 128f;
//            y += newLine;
//            text = I18n.format("jei.tooltips.tfc.rock_layer.middle");
//            x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
//            minecraft.fontRenderer.drawString(text, x, y, 0xFFFFFF, false);
//        }
//        if (RockCategory.Layer.BOTTOM.test(rock))
//        {
//            x = 128f;
//            y += newLine;
//            text = I18n.format("jei.tooltips.tfc.rock_layer.bottom");
//            x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
//            minecraft.fontRenderer.drawString(text, x, y, 0xFFFFFF, false);
//        }
//    }
//}
