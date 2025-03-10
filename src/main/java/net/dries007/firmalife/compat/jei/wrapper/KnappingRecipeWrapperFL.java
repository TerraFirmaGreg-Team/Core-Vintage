package net.dries007.firmalife.compat.jei.wrapper;

import net.minecraft.util.ResourceLocation;

import net.dries007.firmalife.init.KnappingFL;
import com.google.common.collect.Maps;
import mezz.jei.api.IGuiHelper;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.compat.jei.wrappers.KnappingRecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@ParametersAreNonnullByDefault
public class KnappingRecipeWrapperFL extends KnappingRecipeWrapper {

  public static final ResourceLocation PUMPKIN_TEXTURE = new ResourceLocation("minecraft", "textures/blocks/pumpkin_side.png");

  public static final Map<KnappingType, ResourceLocation> HIGHMAP = Maps.newHashMap();

  public static final Map<KnappingType, ResourceLocation> LOWMAP = Maps.newHashMap();

  static {
    HIGHMAP.put(KnappingFL.PUMPKIN, PUMPKIN_TEXTURE);
    LOWMAP.put(KnappingFL.PUMPKIN, null);
  }

  public KnappingRecipeWrapperFL(KnappingRecipe recipe, IGuiHelper guiHelper) {
    super(recipe, guiHelper, HIGHMAP.get(recipe.getType()), LOWMAP.get(recipe.getType()));
  }
}
