package pieman.caffeineaddon.jeicompat;

import net.minecraft.item.ItemStack;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import pieman.caffeineaddon.client.GuiDryingMat;
import pieman.caffeineaddon.init.ModBlocks;
import pieman.caffeineaddon.recipes.Registries;

import java.util.List;
import java.util.stream.Collectors;

import static su.terrafirmagreg.data.Constants.MODID_CAFFEINEADDON;

@JEIPlugin
public class CAJEIPlugin implements IModPlugin {

  private static final String DRYINGMAT_UID = MODID_CAFFEINEADDON + ".drying_mat";

  private static IModRegistry REGISTRY;

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    //Add new JEI recipe categories
    registry.addRecipeCategories(new DryingMatCategory(registry.getJeiHelpers().getGuiHelper(), DRYINGMAT_UID));
  }

  @Override
  public void register(IModRegistry registry) {
    REGISTRY = registry;
    //Wraps all quern recipes
    List<DryingMatRecipeWrapper> quernList = Registries.DRYINGMAT.getValuesCollection()
            .stream()
            .map(DryingMatRecipeWrapper::new)
            .collect(Collectors.toList());

    registry.addRecipes(quernList, DRYINGMAT_UID); //Register recipes to quern category
    registry.addRecipeCatalyst(new ItemStack(ModBlocks.DRYING_MAT_BLOCK),
            DRYINGMAT_UID); //Register BlockQuern as the device that do quern recipes

    registry.addRecipeClickArea(GuiDryingMat.class, 103, 35, 9, 14, DRYINGMAT_UID);
  }

}
