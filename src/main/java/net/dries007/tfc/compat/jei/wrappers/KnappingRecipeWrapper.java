package net.dries007.tfc.compat.jei.wrappers;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
public class KnappingRecipeWrapper implements IRecipeWrapper {
    private static final ResourceLocation CLAY_DISABLED_TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/knapping/clay_button_disabled.png");
    private static final ResourceLocation FIRE_CLAY_DISABLED_TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/knapping/clay_button_fire_disabled.png");
    private static final ResourceLocation CLAY_TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/knapping/clay_button.png");
    private static final ResourceLocation FIRE_CLAY_TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/knapping/clay_button_fire.png");
    private static final ResourceLocation LEATHER_TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/knapping/leather_button.png");

    private static final List<ResourceLocation> ROCK_TEXTURES = new ArrayList<>();
    protected final KnappingRecipe recipe;
    private final int TIME_IN_TICKS = 150;
    private final IGuiHelper guiHelper;
    private final IDrawable squareLow;
    private int ticks;
    private IDrawable squareHigh;

    public KnappingRecipeWrapper(KnappingRecipe recipe, IGuiHelper guiHelper) {
        this(recipe, guiHelper, getHighTexture(recipe.getType()), getLowTexture(recipe.getType()));
    }

    protected KnappingRecipeWrapper(KnappingRecipe recipe, IGuiHelper helper, @Nullable ResourceLocation highTexture, @Nullable ResourceLocation lowTexture) {
        this.guiHelper = helper;
        this.recipe = recipe;

        this.squareHigh = highTexture == null ? null : helper.drawableBuilder(highTexture, 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.squareLow = lowTexture == null ? null : helper.drawableBuilder(lowTexture, 0, 0, 16, 16).setTextureSize(16, 16).build();

        for (var rock : RockType.getAllRockTypes()) {
            ROCK_TEXTURES.add(rock.getTexture());
        }
    }

    @Nullable
    private static ResourceLocation getHighTexture(KnappingType type) {
        if (type == KnappingType.CLAY) {
            return CLAY_TEXTURE;
        } else if (type == KnappingType.FIRE_CLAY) {
            return FIRE_CLAY_TEXTURE;
        } else if (type == KnappingType.LEATHER) {
            return LEATHER_TEXTURE;
        }
        return null;
    }

    @Nullable
    private static ResourceLocation getLowTexture(KnappingType type) {
        if (type == KnappingType.CLAY) {
            return CLAY_DISABLED_TEXTURE;
        } else if (type == KnappingType.FIRE_CLAY) {
            return FIRE_CLAY_DISABLED_TEXTURE;
        }
        return null;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack output = recipe.getOutput();
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        switch (recipe.getType()) {
            case CLAY, FIRE_CLAY, LEATHER -> drawDefaultKnappingUI(minecraft);
            case STONE -> drawRockKnappingUI(minecraft);
        }

    }

    private void drawDefaultKnappingUI(Minecraft minecraft) {
        for (int y = 0; y < recipe.getMatrix().getHeight(); y++) {
            for (int x = 0; x < recipe.getMatrix().getWidth(); x++) {
                if (recipe.getMatrix().get(x, y) && squareHigh != null) {
                    squareHigh.draw(minecraft, 1 + x * 16, 1 + y * 16);
                } else if (squareLow != null) {
                    squareLow.draw(minecraft, 1 + x * 16, 1 + y * 16);
                }
            }
        }
    }

    private void drawRockKnappingUI(Minecraft minecraft) {
        if (ticks == 0 || ticks % TIME_IN_TICKS == 0) {
            squareHigh = guiHelper.drawableBuilder(ROCK_TEXTURES.get(ticks / TIME_IN_TICKS), 0, 0, 16, 16).setTextureSize(16, 16).build();

            if (ticks >= (RockType.getAllRockTypes().size() - 1) * TIME_IN_TICKS)
                ticks = 0;
        }

        for (int y = 0; y < recipe.getMatrix().getHeight(); y++) {
            for (int x = 0; x < recipe.getMatrix().getWidth(); x++) {
                if (recipe.getMatrix().get(x, y) && squareHigh != null) {
                    squareHigh.draw(minecraft, 1 + x * 16, 1 + y * 16);
                }
            }
        }

        ticks++;
    }
}
