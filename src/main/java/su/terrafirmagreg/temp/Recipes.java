package su.terrafirmagreg.temp;

import su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler;

import net.minecraftforge.registries.IForgeRegistry;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeStone;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;

import static gregtech.api.recipes.RecipeMaps.MACERATOR_RECIPES;
import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.temp.util.TFGModUtils.TFG_OREPREFIX_REGISTRY;

public class Recipes {

  public static void register() {
    fixStoneToolsRecipes();
    //fixFlintToolsRecipes(); // TODO

    registerKnappingRecipes();
  }

  private static void fixStoneToolsRecipes() {
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadSword, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadPickaxe, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadSense, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadFile, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadPropick, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadChisel, Materials.Stone));
  }

  private static void fixFlintToolsRecipes() {
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadSword, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadPickaxe, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadSense, Materials.Stone));
    // GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadFile, Materials.Stone));
    // GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadSaw, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadPropick, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCoreHandler.toolHeadChisel, Materials.Stone));
  }


  private static void registerKnappingRecipes() {
    IForgeRegistry<KnappingRecipe> r = TFCRegistries.KNAPPING;

    TFG_OREPREFIX_REGISTRY.forEach(s -> {

      // This covers all stone -> single tool head recipes
      r.register(new KnappingRecipeStone(KnappingType.STONE, rockIn -> OreDictUnifier.get(s.getOrePrefix(), Materials.Stone), s.getStoneKnappingRecipe())
        .setRegistryName(MOD_ID, s.getOrePrefix().name().toLowerCase() + "_stone_head"));

      // This covers all flint -> single tool head recipes
      if (s.getOrePrefix() != OrePrefixCoreHandler.toolHeadHammer) {
        r.register(new KnappingRecipeSimple(KnappingType.FLINT, true, OreDictUnifier.get(s.getOrePrefix(), Materials.Flint), s.getStoneKnappingRecipe())
          .setRegistryName(MOD_ID, s.getOrePrefix().name().toLowerCase() + "_flint_head"));
      }
    });

  }
}
