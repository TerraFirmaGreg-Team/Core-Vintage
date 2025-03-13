package su.terrafirmagreg.modules.integration.gregtech.recipes;

import su.terrafirmagreg.mixin.gregtech.loaders.recipe.handlers.IMaterialRecipeHandlerInvoker;
import su.terrafirmagreg.modules.integration.gregtech.object.item.MetaItemGregTech;
import su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore;

import net.minecraftforge.registries.IForgeRegistry;

import static gregtech.api.recipes.RecipeMaps.MACERATOR_RECIPES;
import static gregtech.api.unification.material.properties.PropertyKey.GEM;
import static gregtech.api.unification.ore.OrePrefix.gem;
import static gregtech.api.unification.ore.OrePrefix.ingot;
import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.temp.util.TFGModUtils.TFG_OREPREFIX_REGISTRY;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeStone;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;

public class ToolRecipeHandler {

  public static void register() {
    OrePrefixCore.toolHeadSword.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadSword);
    OrePrefixCore.toolHeadPickaxe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadPickaxe);
    OrePrefixCore.toolHeadShovel.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadShovel);
    OrePrefixCore.toolHeadAxe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadAxe);
    OrePrefixCore.toolHeadHoe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadHoe);
    OrePrefixCore.toolHeadSense.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadSense);
    OrePrefixCore.toolHeadFile.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadFile);
    OrePrefixCore.toolHeadHammer.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadHammer);
    OrePrefixCore.toolHeadSaw.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadSaw);
    OrePrefixCore.toolHeadKnife.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadKnife);
    OrePrefixCore.toolHeadPropick.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadPropick);
    OrePrefixCore.toolHeadChisel.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadChisel);

    fixStoneToolsRecipes();
    //fixFlintToolsRecipes(); // TODO

    registerKnappingRecipes();
  }

  private static void processHeadSword(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 2)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_SWORD)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 2)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Orange))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadPickaxe(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 3)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_PICKAXE)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 3)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Magenta))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadShovel(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 1)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_SHOVEL)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 1)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.LightBlue))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadAxe(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 3)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_AXE)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 3)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Yellow))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadHoe(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 2)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_HOE)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 2)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Lime))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadSense(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 3)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_SENSE)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 3)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Pink))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadFile(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 2)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_FILE)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 2)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Gray))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadHammer(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 6)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_HAMMER)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 6)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.LightGray))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadSaw(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 2)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_SAW)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 2)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Cyan))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadKnife(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
        .input(ingot, material, 1)
        .notConsumable(MetaItemGregTech.SHAPE_MOLD_KNIFE)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 1)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Cyan))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadPropick(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
        .input(ingot, material, 3)
        .notConsumable(MetaItemGregTech.SHAPE_MOLD_PROPICK)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 3)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Blue))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void processHeadChisel(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 2)
        .notConsumable(MetaItemGregTech.SHAPE_EXTRUDER_CHISEL)
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
        .input(gem, material, 2)
        .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Brown))
        .outputs(OreDictUnifier.get(toolPrefix, material))
        .duration((int) material.getMass() * 2)
        .EUt(2 * voltageMultiplier)
        .buildAndRegister();
    }
  }

  private static void fixStoneToolsRecipes() {
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadSword, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadPickaxe, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadSense, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadFile, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadPropick, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadChisel, Materials.Stone));
  }

  private static void fixFlintToolsRecipes() {
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadSword, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadPickaxe, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadSense, Materials.Stone));
    // GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadFile, Materials.Stone));
    // GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(TFGOrePrefix.toolHeadSaw, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadPropick, Materials.Stone));
    GTRecipeHandler.removeRecipesByInputs(MACERATOR_RECIPES, OreDictUnifier.get(OrePrefixCore.toolHeadChisel, Materials.Stone));
  }


  private static void registerKnappingRecipes() {
    IForgeRegistry<KnappingRecipe> r = TFCRegistries.KNAPPING;

    TFG_OREPREFIX_REGISTRY.forEach(s -> {

      // This covers all stone -> single tool head recipes
      r.register(new KnappingRecipeStone(KnappingType.STONE, rockIn -> OreDictUnifier.get(s.getOrePrefix(), Materials.Stone), s.getStoneKnappingRecipe())
        .setRegistryName(MOD_ID, s.getOrePrefix().name().toLowerCase() + "_stone_head"));

      // This covers all flint -> single tool head recipes
      if (s.getOrePrefix() != OrePrefixCore.toolHeadHammer) {
        r.register(new KnappingRecipeSimple(KnappingType.FLINT, true, OreDictUnifier.get(s.getOrePrefix(), Materials.Flint), s.getStoneKnappingRecipe())
          .setRegistryName(MOD_ID, s.getOrePrefix().name().toLowerCase() + "_flint_head"));
      }
    });

  }
}
