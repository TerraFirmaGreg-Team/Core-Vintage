package su.terrafirmagreg.old.core.modules.gregtech.recipes;

import su.terrafirmagreg.old.core.modules.gregtech.items.TFGMetaItems;
import su.terrafirmagreg.old.core.modules.gregtech.oreprefix.TFGOrePrefix;
import su.terrafirmagreg.old.mixin.gregtech.loaders.recipe.handlers.IMaterialRecipeHandlerInvoker;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.common.items.MetaItems;

import static gregtech.api.unification.material.properties.PropertyKey.GEM;

public class TFGToolRecipeHandler {

  public static void register() {
    TFGOrePrefix.toolHeadSword.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadSword);
    TFGOrePrefix.toolHeadPickaxe.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadPickaxe);
    TFGOrePrefix.toolHeadShovel.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadShovel);
    TFGOrePrefix.toolHeadAxe.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadAxe);
    TFGOrePrefix.toolHeadHoe.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadHoe);
    TFGOrePrefix.toolHeadSense.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadSense);
    TFGOrePrefix.toolHeadFile.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadFile);
    TFGOrePrefix.toolHeadHammer.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadHammer);
    TFGOrePrefix.toolHeadSaw.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadSaw);
    TFGOrePrefix.toolHeadKnife.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadKnife);
    TFGOrePrefix.toolHeadPropick.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadPropick);
    TFGOrePrefix.toolHeadChisel.addProcessingHandler(PropertyKey.TOOL, TFGToolRecipeHandler::processHeadChisel);
  }

  private static void processHeadSword(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 2)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_SWORD)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 2)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Orange))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadPickaxe(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 3)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_PICKAXE)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 3)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Magenta))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadShovel(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 1)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_SHOVEL)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 1)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.LightBlue))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadAxe(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 3)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_AXE)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 3)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Yellow))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadHoe(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 2)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_HOE)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 2)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Lime))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadSense(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 3)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_SENSE)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 3)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Pink))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadFile(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 2)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_FILE)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 2)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Gray))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadHammer(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 6)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_HAMMER)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 6)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.LightGray))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadSaw(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 2)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_SAW)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 2)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Cyan))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadKnife(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                                      .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 1)
                                      .notConsumable(TFGMetaItems.SHAPE_MOLD_KNIFE)
                                      .outputs(OreDictUnifier.get(toolPrefix, material))
                                      .duration((int) material.getMass() * 2)
                                      .EUt(2 * voltageMultiplier)
                                      .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 1)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Cyan))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadPropick(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                                      .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 3)
                                      .notConsumable(TFGMetaItems.SHAPE_MOLD_PROPICK)
                                      .outputs(OreDictUnifier.get(toolPrefix, material))
                                      .duration((int) material.getMass() * 2)
                                      .EUt(2 * voltageMultiplier)
                                      .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 3)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Blue))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }

  private static void processHeadChisel(gregtech.api.unification.ore.OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                                 .input(gregtech.api.unification.ore.OrePrefix.ingot, material, 2)
                                 .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_CHISEL)
                                 .outputs(OreDictUnifier.get(toolPrefix, material))
                                 .duration((int) material.getMass() * 2)
                                 .EUt(2 * voltageMultiplier)
                                 .buildAndRegister();
    } else {
      RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                                       .input(gregtech.api.unification.ore.OrePrefix.gem, material, 2)
                                       .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Brown))
                                       .outputs(OreDictUnifier.get(toolPrefix, material))
                                       .duration((int) material.getMass() * 2)
                                       .EUt(2 * voltageMultiplier)
                                       .buildAndRegister();
    }
  }
}
