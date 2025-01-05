package su.terrafirmagreg.modules.core.plugin.gregtech.recipes;

import su.terrafirmagreg.mixin.gregtech.loaders.recipe.handlers.IMaterialRecipeHandlerInvoker;
import su.terrafirmagreg.modules.core.plugin.gregtech.oreprefix.OrePrefixCoreHandler;
import su.terrafirmagreg.modules.device.init.ItemsDevice;

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

public class ToolRecipeCoreHandler {

  public static void register() {
    OrePrefixCoreHandler.toolHeadSword.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadSword);
    OrePrefixCoreHandler.toolHeadPickaxe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadPickaxe);
    OrePrefixCoreHandler.toolHeadShovel.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadShovel);
    OrePrefixCoreHandler.toolHeadAxe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadAxe);
    OrePrefixCoreHandler.toolHeadHoe.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadHoe);
    OrePrefixCoreHandler.toolHeadSense.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadSense);
    OrePrefixCoreHandler.toolHeadFile.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadFile);
    OrePrefixCoreHandler.toolHeadHammer.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadHammer);
    OrePrefixCoreHandler.toolHeadSaw.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadSaw);
    OrePrefixCoreHandler.toolHeadKnife.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadKnife);
    OrePrefixCoreHandler.toolHeadPropick.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadPropick);
    OrePrefixCoreHandler.toolHeadChisel.addProcessingHandler(PropertyKey.TOOL, ToolRecipeCoreHandler::processHeadChisel);
  }

  private static void processHeadSword(OrePrefix toolPrefix, Material material, ToolProperty property) {
    int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

    if (!material.hasProperty(GEM)) {
      RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
        .input(ingot, material, 2)
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_SWORD)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_PICKAXE)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_SHOVEL)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_AXE)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_HOE)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_SENSE)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_FILE)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_HAMMER)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_SAW)
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
        .notConsumable(ItemsDevice.SHAPE_MOLD_KNIFE)
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
        .notConsumable(ItemsDevice.SHAPE_MOLD_PROPICK)
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
        .notConsumable(ItemsDevice.SHAPE_EXTRUDER_CHISEL)
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
