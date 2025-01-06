package su.terrafirmagreg.modules.integration.gregtech.recipes;

import su.terrafirmagreg.mixin.gregtech.loaders.recipe.handlers.IMaterialRecipeHandlerInvoker;
import su.terrafirmagreg.modules.integration.gregtech.object.item.MetaItemGregTech;
import su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import static gregtech.api.unification.material.properties.PropertyKey.GEM;
import static gregtech.api.unification.ore.OrePrefix.gem;
import static gregtech.api.unification.ore.OrePrefix.ingot;

public class ToolRecipeHandler {

  public static void register() {
    OrePrefixHandler.toolHeadSword.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadSword);
    OrePrefixHandler.toolHeadPickaxe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadPickaxe);
    OrePrefixHandler.toolHeadShovel.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadShovel);
    OrePrefixHandler.toolHeadAxe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadAxe);
    OrePrefixHandler.toolHeadHoe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadHoe);
    OrePrefixHandler.toolHeadSense.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadSense);
    OrePrefixHandler.toolHeadFile.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadFile);
    OrePrefixHandler.toolHeadHammer.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadHammer);
    OrePrefixHandler.toolHeadSaw.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadSaw);
    OrePrefixHandler.toolHeadKnife.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadKnife);
    OrePrefixHandler.toolHeadPropick.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadPropick);
    OrePrefixHandler.toolHeadChisel.addProcessingHandler(PropertyKey.TOOL, ToolRecipeHandler::processHeadChisel);
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
}
