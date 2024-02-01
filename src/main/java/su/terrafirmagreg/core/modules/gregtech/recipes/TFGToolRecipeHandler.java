package su.terrafirmagreg.core.modules.gregtech.recipes;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import su.terrafirmagreg.core.mixin.gregtech.IMaterialRecipeHandlerInvoker;
import su.terrafirmagreg.core.modules.gregtech.items.TFGMetaItems;
import su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix;

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

    private static void processHeadSword(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM)) {
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 2)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_SWORD)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        } else {
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 2)
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
                    .input(OrePrefix.ingot, material, 3)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_PICKAXE)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        } else {
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 3)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Magenta))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        }
    }

    private static void processHeadShovel(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 1)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_SHOVEL)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 1)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.LightBlue))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadAxe(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 3)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_AXE)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 3)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Yellow))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadHoe(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 2)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_HOE)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 2)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Lime))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadSense(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 3)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_SENSE)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 3)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Pink))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadFile(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 2)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_FILE)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 2)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Gray))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadHammer(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 6)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_HAMMER)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 6)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.LightGray))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadSaw(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 2)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_SAW)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 2)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Cyan))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadKnife(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 1)
                    .notConsumable(TFGMetaItems.SHAPE_MOLD_KNIFE)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 1)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Cyan))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadPropick(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 3)
                    .notConsumable(TFGMetaItems.SHAPE_MOLD_PROPICK)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 3)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Blue))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }

    private static void processHeadChisel(OrePrefix toolPrefix, Material material, ToolProperty property) {
        int voltageMultiplier = IMaterialRecipeHandlerInvoker.invokeGetVoltageMultiplier(material);

        if (!material.hasProperty(GEM))
            RecipeMaps.EXTRUDER_RECIPES.recipeBuilder()
                    .input(OrePrefix.ingot, material, 2)
                    .notConsumable(TFGMetaItems.SHAPE_EXTRUDER_CHISEL)
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
        else
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .input(OrePrefix.gem, material, 2)
                    .notConsumable(MetaItems.GLASS_LENSES.get(MarkerMaterials.Color.Brown))
                    .outputs(OreDictUnifier.get(toolPrefix, material))
                    .duration((int) material.getMass() * 2)
                    .EUt(2 * voltageMultiplier)
                    .buildAndRegister();
    }
}
