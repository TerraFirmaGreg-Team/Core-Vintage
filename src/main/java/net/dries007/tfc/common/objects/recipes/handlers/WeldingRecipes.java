package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;

public class WeldingRecipes {

    public static void register() {
        var registry = TFCRegistries.WELDING;

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            if (material.hasProperty(TFGPropertyKey.HEAT) && !material.hasFlag(TFGMaterialFlags.UNUSABLE)) {

                registry.register(new WeldingRecipe(
                        TerraFirmaCraft.identifier("plate_" + material.getName()),
                        IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, material)),
                        IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, material)),
                        OreDictUnifier.get(TFGOrePrefix.ingotDouble, material),
                        material.getProperty(TFGPropertyKey.HEAT).getTier()
                ));

                registry.register(new WeldingRecipe(
                        TerraFirmaCraft.identifier("double_plate_" + material.getName()),
                        IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotDouble, material)),
                        IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, material)),
                        OreDictUnifier.get(TFGOrePrefix.ingotTriple, material),
                        material.getProperty(TFGPropertyKey.HEAT).getTier()
                ));

                registry.register(new WeldingRecipe(
                        TerraFirmaCraft.identifier("triple_plate_" + material.getName()),
                        IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotTriple, material)),
                        IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotTriple, material)),
                        OreDictUnifier.get(TFGOrePrefix.ingotHex, material),
                        material.getProperty(TFGPropertyKey.HEAT).getTier()
                ));
            }
        }

        registry.register(new WeldingRecipe(
                TerraFirmaCraft.identifier("high_carbon_black_steel"),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.WeakSteel)),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.PigIron)),
                OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.HighCarbonBlackSteel),
                TFGMaterials.HighCarbonBlackSteel.getProperty(TFGPropertyKey.HEAT).getTier()
        ));

        registry.register(new WeldingRecipe(
                TerraFirmaCraft.identifier("high_carbon_blue_steel"),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.WeakBlueSteel)),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, Materials.BlackSteel)),
                OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.HighCarbonBlueSteel),
                TFGMaterials.HighCarbonBlueSteel.getProperty(TFGPropertyKey.HEAT).getTier()
        ));

        registry.register(new WeldingRecipe(
                TerraFirmaCraft.identifier("high_carbon_red_steel"),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.WeakRedSteel)),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, Materials.BlackSteel)),
                OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.HighCarbonRedSteel),
                TFGMaterials.HighCarbonRedSteel.getProperty(TFGPropertyKey.HEAT).getTier()
        ));

        // Armor
        // addWelding(r, UNFINISHED_HELMET, SHEET, HELMET, true, ARMOR);
        // addWelding(r, UNFINISHED_CHESTPLATE, DOUBLE_SHEET, CHESTPLATE, true, ARMOR);
        // addWelding(r, UNFINISHED_GREAVES, SHEET, GREAVES, true, ARMOR);
        // addWelding(r, UNFINISHED_BOOTS, SHEET, BOOTS, true, ARMOR);
        // addWelding(r, KNIFE_BLADE, KNIFE_BLADE, SHEARS, true, TOOLS);
    }
}
