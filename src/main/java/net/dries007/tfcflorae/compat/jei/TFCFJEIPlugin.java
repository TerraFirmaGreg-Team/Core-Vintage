package net.dries007.tfcflorae.compat.jei;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.compat.jei.categories.CastingCategory;
import net.dries007.tfc.compat.jei.categories.KnappingCategory;
import net.dries007.tfc.objects.container.ContainerInventoryCrafting;
import net.dries007.tfcflorae.client.GuiKnappingTFCF;
import net.dries007.tfcflorae.compat.jei.wrappers.KnappingRecipeWrapperTFCF;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static su.terrafirmagreg.api.data.Reference.MODID_TFCF;

@JEIPlugin
public class TFCFJEIPlugin implements IModPlugin {

  public static final String KNAP_PINEAPPLE_LEATHER_UID = MODID_TFCF + ".knap.pineapple_leather";
  public static final String KNAP_BURLAP_CLOTH_UID = MODID_TFCF + ".knap.burlap_cloth";
  public static final String KNAP_WOOL_CLOTH_UID = MODID_TFCF + ".knap.wool_cloth";
  public static final String KNAP_SILK_CLOTH_UID = MODID_TFCF + ".knap.silk_cloth";
  public static final String KNAP_SISAL_CLOTH_UID = MODID_TFCF + ".knap.sisal_cloth";
  public static final String KNAP_COTTON_CLOTH_UID = MODID_TFCF + ".knap.cotton_cloth";
  public static final String KNAP_LINEN_CLOTH_UID = MODID_TFCF + ".knap.linen_cloth";
  public static final String KNAP_HEMP_CLOTH_UID = MODID_TFCF + ".knap.hemp_cloth";
  public static final String KNAP_YUCCA_CANVAS_UID = MODID_TFCF + ".knap.yucca_canvas";
  public static final String KNAP_MUD_UID = MODID_TFCF + ".knap.mud";
  public static final String KNAP_FLINT_UID = MODID_TFCF + ".knap.flint";
  public static final String CASTING_UID = MODID_TFCF + ".casting";
  private static IModRegistry REGISTRY;

  /**
   * Helper method to return a collection containing all possible itemstacks registered in JEI
   *
   * @return Collection of ItemStacks
   */
  public static Collection<ItemStack> getAllIngredients() {
    return REGISTRY.getIngredientRegistry().getAllIngredients(VanillaTypes.ITEM);
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_PINEAPPLE_LEATHER_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_BURLAP_CLOTH_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_WOOL_CLOTH_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_SILK_CLOTH_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_SISAL_CLOTH_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_COTTON_CLOTH_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_LINEN_CLOTH_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_HEMP_CLOTH_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers()
                                                              .getGuiHelper(), KNAP_YUCCA_CANVAS_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers().getGuiHelper(), KNAP_MUD_UID));
    registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers().getGuiHelper(), KNAP_FLINT_UID));
    registry.addRecipeCategories(new CastingCategory(registry.getJeiHelpers().getGuiHelper(), CASTING_UID));
  }

  @Override
  public void register(IModRegistry registry) {
    REGISTRY = registry;

    // Knapping Pineapple Leather
    List<KnappingRecipeWrapperTFCF> leatherPineappleRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                                    .filter(recipe -> recipe.getType() == KnappingTypes.PINEAPPLE_LEATHER)
                                                                                    .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                                 .getGuiHelper()))
                                                                                    .collect(Collectors.toList());
    registry.addRecipes(leatherPineappleRecipes, KNAP_PINEAPPLE_LEATHER_UID);
    NonNullList<ItemStack> leatherPineapple = OreDictionary.getOres("leatherPineapple");
    for (ItemStack itemStack : leatherPineapple) {
      registry.addRecipeCatalyst(itemStack, KNAP_PINEAPPLE_LEATHER_UID);
    }

    // Knapping Burlap Cloth
    List<KnappingRecipeWrapperTFCF> clothBurlapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                               .filter(recipe -> recipe.getType() == KnappingTypes.BURLAP_CLOTH)
                                                                               .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                            .getGuiHelper()))
                                                                               .collect(Collectors.toList());
    registry.addRecipes(clothBurlapRecipes, KNAP_BURLAP_CLOTH_UID);
    NonNullList<ItemStack> clothBurlap = OreDictionary.getOres("clothBurlap");
    for (ItemStack itemStack : clothBurlap) {
      registry.addRecipeCatalyst(itemStack, KNAP_BURLAP_CLOTH_UID);
    }

    // Knapping Wool Cloth
    List<KnappingRecipeWrapperTFCF> clothWoolRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                             .filter(recipe -> recipe.getType() == KnappingTypes.WOOL_CLOTH)
                                                                             .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                          .getGuiHelper()))
                                                                             .collect(Collectors.toList());
    registry.addRecipes(clothWoolRecipes, KNAP_WOOL_CLOTH_UID);
    NonNullList<ItemStack> clothWool = OreDictionary.getOres("clothWool");
    for (ItemStack itemStack : clothWool) {
      registry.addRecipeCatalyst(itemStack, KNAP_WOOL_CLOTH_UID);
    }

    // Knapping Silk Cloth
    List<KnappingRecipeWrapperTFCF> clothSilkRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                             .filter(recipe -> recipe.getType() == KnappingTypes.SILK_CLOTH)
                                                                             .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                          .getGuiHelper()))
                                                                             .collect(Collectors.toList());
    registry.addRecipes(clothSilkRecipes, KNAP_SILK_CLOTH_UID);
    NonNullList<ItemStack> clothSilk = OreDictionary.getOres("clothSilk");
    for (ItemStack itemStack : clothSilk) {
      registry.addRecipeCatalyst(itemStack, KNAP_SILK_CLOTH_UID);
    }

    // Knapping Sisal Cloth
    List<KnappingRecipeWrapperTFCF> clothSisalRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                              .filter(recipe -> recipe.getType() == KnappingTypes.SISAL_CLOTH)
                                                                              .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                           .getGuiHelper()))
                                                                              .collect(Collectors.toList());
    registry.addRecipes(clothSisalRecipes, KNAP_SISAL_CLOTH_UID);
    NonNullList<ItemStack> clothSisal = OreDictionary.getOres("clothSisal");
    for (ItemStack itemStack : clothSisal) {
      registry.addRecipeCatalyst(itemStack, KNAP_SISAL_CLOTH_UID);
    }

    // Knapping Cotton Cloth
    List<KnappingRecipeWrapperTFCF> clothCottonRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                               .filter(recipe -> recipe.getType() == KnappingTypes.COTTON_CLOTH)
                                                                               .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                            .getGuiHelper()))
                                                                               .collect(Collectors.toList());
    registry.addRecipes(clothCottonRecipes, KNAP_COTTON_CLOTH_UID);
    NonNullList<ItemStack> clothCotton = OreDictionary.getOres("clothCotton");
    for (ItemStack itemStack : clothCotton) {
      registry.addRecipeCatalyst(itemStack, KNAP_COTTON_CLOTH_UID);
    }

    // Knapping Linen Cloth
    List<KnappingRecipeWrapperTFCF> clothLinenRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                              .filter(recipe -> recipe.getType() == KnappingTypes.LINEN_CLOTH)
                                                                              .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                           .getGuiHelper()))
                                                                              .collect(Collectors.toList());
    registry.addRecipes(clothLinenRecipes, KNAP_LINEN_CLOTH_UID);
    NonNullList<ItemStack> oresLinen = OreDictionary.getOres("clothLinen");
    for (ItemStack itemStack : oresLinen) {
      registry.addRecipeCatalyst(itemStack, KNAP_LINEN_CLOTH_UID);
    }

    // Knapping Hemp Cloth
    List<KnappingRecipeWrapperTFCF> clothHempRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                             .filter(recipe -> recipe.getType() == KnappingTypes.HEMP_CLOTH)
                                                                             .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                          .getGuiHelper()))
                                                                             .collect(Collectors.toList());
    registry.addRecipes(clothHempRecipes, KNAP_HEMP_CLOTH_UID);
    NonNullList<ItemStack> oresHemp = OreDictionary.getOres("clothHemp");
    for (ItemStack itemStack : oresHemp) {
      registry.addRecipeCatalyst(itemStack, KNAP_HEMP_CLOTH_UID);
    }

    // Knapping Yucca Canvas
    List<KnappingRecipeWrapperTFCF> canvasYuccaRecipes = TFCRegistries.KNAPPING.getValuesCollection()
                                                                               .stream()
                                                                               .filter(recipe -> recipe.getType() == KnappingTypes.YUCCA_CANVAS)
                                                                               .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                            .getGuiHelper()))
                                                                               .collect(Collectors.toList());
    registry.addRecipes(canvasYuccaRecipes, KNAP_YUCCA_CANVAS_UID);
    NonNullList<ItemStack> oresYucca = OreDictionary.getOres("canvasYucca");
    for (ItemStack itemStack : oresYucca) {
      registry.addRecipeCatalyst(itemStack, KNAP_YUCCA_CANVAS_UID);
    }

    // Knapping Mud
//    List<KnappingRecipeWrapperTFCF> mudKnapRecipes = TFCRegistries.KNAPPING.getValuesCollection()
//        .stream()
//        .filter(recipe -> recipe.getType() == KnappingTypes.MUD)
//        .flatMap(recipe -> SoilType.getTypes()
//            .stream()
//            .map(type -> new KnappingRecipeWrapperTFCF.Mud(recipe, registry.getJeiHelpers().getGuiHelper(), type)))
//        .collect(Collectors.toList());
//    registry.addRecipes(mudKnapRecipes, KNAP_MUD_UID);
//    NonNullList<ItemStack> oresMud = OreDictionary.getOres("mud");
//    SoilType.getTypes().forEach(type -> {
//      registry.addRecipeCatalyst(new ItemStack(ItemsSoil.MUD.get(type)), KNAP_MUD_UID);
//    });

    // Knapping Flint
    List<KnappingRecipeWrapperTFCF> flintKnapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                             .filter(recipe -> recipe.getType() == KnappingTypes.FLINT)
                                                                             .map(recipe -> new KnappingRecipeWrapperTFCF(recipe, registry.getJeiHelpers()
                                                                                                                                          .getGuiHelper()))
                                                                             .collect(Collectors.toList());
    registry.addRecipes(flintKnapRecipes, KNAP_FLINT_UID);
    NonNullList<ItemStack> oresFlint = OreDictionary.getOres("flint");
    for (ItemStack itemStack : oresFlint) {
      registry.addRecipeCatalyst(itemStack, KNAP_FLINT_UID);
    }
    registry.addRecipeClickArea(GuiKnappingTFCF.class, 97, 44, 22, 15, KNAP_MUD_UID, KNAP_FLINT_UID);

    //ContainerInventoryCrafting - Add ability to transfer recipe items
    IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
    transferRegistry.addRecipeTransferHandler(ContainerInventoryCrafting.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
  }
}
