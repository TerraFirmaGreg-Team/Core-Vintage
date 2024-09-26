package com.eerussianguy.firmalife.compat.jei;

import su.terrafirmagreg.modules.device.init.BlocksDevice;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import com.eerussianguy.firmalife.compat.jei.category.CastingCategoryFL;
import com.eerussianguy.firmalife.compat.jei.category.DryingRecipeCategory;
import com.eerussianguy.firmalife.compat.jei.category.OvenRecipeCategory;
import com.eerussianguy.firmalife.compat.jei.wrapper.CastingRecipeWrapperFL;
import com.eerussianguy.firmalife.compat.jei.wrapper.DryingRecipeWrapper;
import com.eerussianguy.firmalife.compat.jei.wrapper.KnappingRecipeWrapperFL;
import com.eerussianguy.firmalife.compat.jei.wrapper.OvenRecipeWrapper;
import com.eerussianguy.firmalife.compat.jei.wrapper.UnmoldRecipeWrapperFL;
import com.eerussianguy.firmalife.init.FoodFL;
import com.eerussianguy.firmalife.registry.BlocksFL;
import com.eerussianguy.firmalife.registry.ItemsFL;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.jei.categories.KnappingCategory;
import net.dries007.tfc.compat.jei.wrappers.KnappingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.SimpleRecipeWrapper;
import net.dries007.tfc.objects.items.ItemsTFC;

import java.util.List;
import java.util.stream.Collectors;

import static su.terrafirmagreg.data.Constants.MODID_FL;

@JEIPlugin
public class JEIPluginFL implements IModPlugin {

  public static final String OVEN_ID = MODID_FL + ".oven";
  public static final String DRY_ID = MODID_FL + ".drying";
  public static final String KNAP_PUMPKIN_UID = MODID_FL + ".knap.pumpkin";
  public static final String CASTING_UID = MODID_FL + ".casting";

  private static IModRegistry REGISTRY;

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    registry.addRecipeCategories(new OvenRecipeCategory(registry.getJeiHelpers().getGuiHelper(), OVEN_ID));
    registry.addRecipeCategories(new DryingRecipeCategory(registry.getJeiHelpers().getGuiHelper(), DRY_ID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers().getGuiHelper(), KNAP_PUMPKIN_UID));
    registry.addRecipeCategories(new CastingCategoryFL(registry.getJeiHelpers().getGuiHelper(), CASTING_UID));
  }

  @Override
  public void register(IModRegistry registry) {
    REGISTRY = registry;

    List<SimpleRecipeWrapper> ovenList = TFCRegistries.OVEN.getValuesCollection()
                                                           .stream()
                                                           .map(OvenRecipeWrapper::new)
                                                           .collect(Collectors.toList());
    registry.addRecipes(ovenList, OVEN_ID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.OVEN), OVEN_ID);

    List<SimpleRecipeWrapper> dryList = TFCRegistries.DRYING.getValuesCollection()
                                                            .stream()
                                                            .map(DryingRecipeWrapper::new)
                                                            .collect(Collectors.toList());
    registry.addRecipes(dryList, DRY_ID);
    registry.addRecipeCatalyst(new ItemStack(BlocksFL.LEAF_MAT, 1), DRY_ID);

    registry.addIngredientInfo(new ItemStack(ItemsFL.FRUIT_LEAF, 1), VanillaTypes.ITEM,
                               new TextComponentTranslation("jei.tooltip.firmalife.fruit_leaf").getFormattedText());
    registry.addIngredientInfo(new ItemStack(ItemsFL.COCOA_POWDER, 1), VanillaTypes.ITEM,
                               new TextComponentTranslation("jei.tooltip.firmalife.cocoa_powder").getFormattedText());
    registry.addIngredientInfo(new ItemStack(ItemsFL.getFood(FoodFL.PINEAPPLE_CHUNKS), 1), VanillaTypes.ITEM,
                               new TextComponentTranslation("jei.tooltip.firmalife.pineapple_chunks").getFormattedText());

    // Pumpkin Knapping
    List<KnappingRecipeWrapper> pumpkinknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                           .filter(recipe -> recipe.getType() == KnappingTypes.PUMPKIN)
                                                                           .map(recipe -> new KnappingRecipeWrapperFL(recipe, registry.getJeiHelpers()
                                                                                                                                      .getGuiHelper()))
                                                                           .collect(Collectors.toList());

    // Molds
    List<UnmoldRecipeWrapperFL> moldRecipes = TFCRegistries.METALS.getValuesCollection().stream()
                                                                  .filter(metal -> metal.isToolMetal() && metal.getTier()
                                                                                                               .isAtMost(Metal.Tier.TIER_II))
                                                                  .map(metal -> new UnmoldRecipeWrapperFL(metal, "mallet"))
                                                                  .collect(Collectors.toList());

    // Casts
    List<CastingRecipeWrapperFL> castRecipes = TFCRegistries.METALS.getValuesCollection().stream()
                                                                   .filter(metal -> metal.isToolMetal() && metal.getTier()
                                                                                                                .isAtMost(Metal.Tier.TIER_II))
                                                                   .map(metal -> new CastingRecipeWrapperFL(metal, "mallet"))
                                                                   .collect(Collectors.toList());

    registry.addRecipes(moldRecipes, "minecraft.crafting");
    registry.addRecipes(castRecipes, CASTING_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CRUCIBLE), CASTING_UID);
    registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), CASTING_UID);
    registry.addRecipes(pumpkinknapRecipes, KNAP_PUMPKIN_UID);
    registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(BlocksFL.PUMPKIN_FRUIT)), KNAP_PUMPKIN_UID);
  }
}
