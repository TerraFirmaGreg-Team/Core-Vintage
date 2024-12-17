package tfcflorae.compat.jei.wrappers;

import su.terrafirmagreg.api.data.Reference;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.compat.jei.wrappers.KnappingRecipeWrapper;
import net.dries007.tfc.util.Helpers;
import tfcflorae.objects.items.rock.ItemMud;

import javax.annotation.Nullable;

public class KnappingRecipeWrapperTFCF extends KnappingRecipeWrapper {

  private static final ResourceLocation PINEAPPLE_LEATHER_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/gui/knapping/pineapple_leather_button.png");
  private static final ResourceLocation BURLAP_CLOTH_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/blocks/devices/loom/product/burlap.png");
  private static final ResourceLocation WOOL_CLOTH_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/blocks/devices/loom/product/wool.png");
  private static final ResourceLocation SILK_CLOTH_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/blocks/devices/loom/product/silk.png");
  private static final ResourceLocation SISAL_CLOTH_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/blocks/devices/loom/product/sisal.png");
  private static final ResourceLocation COTTON_CLOTH_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/blocks/devices/loom/product/cotton.png");
  private static final ResourceLocation LINEN_CLOTH_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/blocks/devices/loom/product/linen.png");
  private static final ResourceLocation HEMP_CLOTH_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/blocks/devices/loom/product/hemp.png");
  private static final ResourceLocation YUCCA_CANVAS_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/blocks/devices/loom/product/yucca.png");
  private static final ResourceLocation MUD_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/gui/knapping/mud_button.png");
  private static final ResourceLocation MUD_DISABLED_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/gui/knapping/mud_button_disabled.png");
  private static final ResourceLocation FLINT_TEXTURE = new ResourceLocation(Reference.TFCF, "textures/gui/knapping/flint_button.png");

  private static ResourceLocation getHighTexture(KnappingType type) {
    if (type == KnappingType.PINEAPPLE_LEATHER) {
      return PINEAPPLE_LEATHER_TEXTURE;
    } else if (type == KnappingType.BURLAP_CLOTH) {
      return BURLAP_CLOTH_TEXTURE;
    } else if (type == KnappingType.WOOL_CLOTH) {
      return WOOL_CLOTH_TEXTURE;
    } else if (type == KnappingType.SILK_CLOTH) {
      return SILK_CLOTH_TEXTURE;
    } else if (type == KnappingType.SISAL_CLOTH) {
      return SISAL_CLOTH_TEXTURE;
    } else if (type == KnappingType.COTTON_CLOTH) {
      return COTTON_CLOTH_TEXTURE;
    } else if (type == KnappingType.LINEN_CLOTH) {
      return LINEN_CLOTH_TEXTURE;
    } else if (type == KnappingType.HEMP_CLOTH) {
      return HEMP_CLOTH_TEXTURE;
    } else if (type == KnappingType.YUCCA_CANVAS) {
      return YUCCA_CANVAS_TEXTURE;
    } else if (type == KnappingType.FLINT) {
      return FLINT_TEXTURE;
    }
    return null;
  }

  private static ResourceLocation getLowTexture(KnappingType type) {
    return null;
  }

  public KnappingRecipeWrapperTFCF(KnappingRecipe recipe, IGuiHelper helper) {
    super(recipe, helper, getHighTexture(recipe.getType()), getLowTexture(recipe.getType()));
  }

  public KnappingRecipeWrapperTFCF(KnappingRecipe recipe, IGuiHelper helper, @Nullable ResourceLocation highTexture, @Nullable ResourceLocation lowTexture) {
    super(recipe, helper, highTexture, lowTexture);
  }

  public static class Mud extends KnappingRecipeWrapperTFCF {

    private final Rock rock;

    public Mud(KnappingRecipe recipe, IGuiHelper helper, Rock rock) {
      super(recipe, helper, ItemMud.get(rock).getForegroundTexture(), ItemMud.get(rock).getBackgroundTexture());

      this.rock = rock;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
      ingredients.setOutputLists(VanillaTypes.ITEM, Helpers.listOf(Helpers.listOf(recipe.getOutput(new ItemStack(ItemMud.get(rock))))));
    }
  }
}
