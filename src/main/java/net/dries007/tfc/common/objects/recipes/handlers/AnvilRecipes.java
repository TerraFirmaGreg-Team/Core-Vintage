package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipeMeasurable;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipeSplitting;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolItems;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.minecraft.item.ItemStack;

import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.*;

public class AnvilRecipes {

    public static void register() {
        var registry = TFCRegistries.ANVIL;

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            if (material.hasProperty(TFGPropertyKey.HEAT) && !material.hasFlag(TFGMaterialFlags.UNUSABLE)) {

                if (material.hasFlag(MaterialFlags.GENERATE_PLATE)) {
                    // Ingot -> Plate
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_plate_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotDouble, material)),
                            OreDictUnifier.get(OrePrefix.plate, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            GENERAL,
                            HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
                }

                if (material.hasFlag(MaterialFlags.GENERATE_ROD)) {
                    // Ingot -> Stick
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_stick_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, material)),
                            OreDictUnifier.get(OrePrefix.stick, material, 2),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            GENERAL,
                            DRAW_LAST, DRAW_NOT_LAST, PUNCH_NOT_LAST));
                }

                if (material.hasProperty(PropertyKey.TOOL)) {
                    // Ingot x2 -> Sword Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("double_ingot_to_sword_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotDouble, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadSword, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            WEAPONS,
                            HIT_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST));

                    // Ingot x3 -> Pickaxe Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("triple_ingot_to_pickaxe_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotTriple, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadPickaxe, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            TOOLS,
                            PUNCH_LAST, BEND_NOT_LAST, DRAW_NOT_LAST));

                    // Ingot x3 -> Axe Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_axe_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotTriple, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadAxe, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            TOOLS,
                            PUNCH_LAST, BEND_NOT_LAST, DRAW_NOT_LAST));

                    // Ingot x1 -> Shovel Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_shovel_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadShovel, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            TOOLS,
                            PUNCH_LAST, HIT_NOT_LAST));

                    // Ingot x2 -> Saw Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_saw_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotDouble, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadSaw, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            TOOLS,
                            HIT_LAST, HIT_SECOND_LAST));

                    // Ingot x6 -> Hammer Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_hammer_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotHex, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadHammer, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            TOOLS,
                            PUNCH_LAST, SHRINK_NOT_LAST));

                    // Ingot x3 -> Sense Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_sense_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotTriple, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadSense, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            WEAPONS,
                            HIT_LAST, DRAW_SECOND_LAST, BEND_THIRD_LAST));

                    // Ingot x1 -> Knife Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_knife_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            WEAPONS,
                            HIT_LAST, DRAW_SECOND_LAST, DRAW_THIRD_LAST));

                    // Ingot 3x -> Propick
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_propick_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotTriple, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadPropick, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            TOOLS,
                            PUNCH_LAST, DRAW_NOT_LAST, BEND_NOT_LAST));

                    // Ingot 2x -> Chisel Head
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_chisel_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotDouble, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadChisel, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            TOOLS,
                            HIT_LAST, HIT_NOT_LAST, DRAW_NOT_LAST));

                    // Ingot 3x -> Javelin Head
                    /*
                    r.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_javelin_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotTriple, material)),
                            OreDictUnifier.get(TFGOrePrefix.toolHeadJavelin, material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            WEAPONS,
                            HIT_LAST, HIT_SECOND_LAST, DRAW_THIRD_LAST));*/

                    // Ingot 6x -> TUYERE
                    registry.register(new AnvilRecipe(
                            TerraFirmaCraft.identifier("ingot_to_tuyere_" + material.getName()),
                            IIngredient.of(OreDictUnifier.get(TFGOrePrefix.ingotHex, material)),
                            TFGToolItems.TUYERE.get(material),
                            material.getProperty(TFGPropertyKey.HEAT).getTier(),
                            GENERAL,
                            BEND_LAST, BEND_SECOND_LAST));
                }
            }
        }

        registry.register(new AnvilRecipe(
                TerraFirmaCraft.identifier("high_carbon_steel"),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.PigIron)),
                OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.HighCarbonSteel),
                TFGMaterials.HighCarbonSteel.getProperty(TFGPropertyKey.HEAT).getTier(),
                null,
                HIT_ANY, HIT_ANY, HIT_ANY));

        registry.register(new AnvilRecipe(
                TerraFirmaCraft.identifier("steel"),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.HighCarbonSteel)),
                OreDictUnifier.get(OrePrefix.ingot, Materials.Steel),
                Materials.Steel.getProperty(TFGPropertyKey.HEAT).getTier(),
                null,
                HIT_ANY, HIT_ANY, HIT_ANY));

        registry.register(new AnvilRecipe(
                TerraFirmaCraft.identifier("black_steel"),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.HighCarbonBlackSteel)),
                OreDictUnifier.get(OrePrefix.ingot, Materials.BlackSteel),
                Materials.BlackSteel.getProperty(TFGPropertyKey.HEAT).getTier(),
                null,
                HIT_ANY, HIT_ANY, HIT_ANY));

        registry.register(new AnvilRecipe(
                TerraFirmaCraft.identifier("blue_steel"),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.HighCarbonBlueSteel)),
                OreDictUnifier.get(OrePrefix.ingot, Materials.BlueSteel),
                Materials.BlueSteel.getProperty(TFGPropertyKey.HEAT).getTier(),
                null,
                HIT_ANY, HIT_ANY, HIT_ANY));

        registry.register(new AnvilRecipe(
                TerraFirmaCraft.identifier("red_steel"),
                IIngredient.of(OreDictUnifier.get(OrePrefix.ingot, TFGMaterials.HighCarbonRedSteel)),
                OreDictUnifier.get(OrePrefix.ingot, Materials.RedSteel),
                Materials.RedSteel.getProperty(TFGPropertyKey.HEAT).getTier(),
                null,
                HIT_ANY, HIT_ANY, HIT_ANY));

        // Blooms
        registry.register(new AnvilRecipeMeasurable(TerraFirmaCraft.identifier("refining_bloom"), IIngredient.of(TFCItems.UNREFINED_BLOOM), new ItemStack(TFCItems.REFINED_BLOOM), 2, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
        registry.register(new AnvilRecipeSplitting(TerraFirmaCraft.identifier("splitting_bloom"), IIngredient.of(TFCItems.REFINED_BLOOM), new ItemStack(TFCItems.REFINED_BLOOM), 144, 2, PUNCH_LAST));
        registry.register(new AnvilRecipe(TerraFirmaCraft.identifier("iron_bloom"), x -> {
            if (x.getItem() == TFCItems.REFINED_BLOOM) {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal) {
                    return ((IForgeableMeasurableMetal) cap).getMaterial() == Materials.Iron && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 144;
                }
            }
            return false;
        }, OreDictUnifier.get(OrePrefix.ingot, Materials.Iron), 2, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));

        // Misc
        // addAnvil(r, INGOT, LAMP, false, GENERAL, BEND_LAST, BEND_SECOND_LAST, DRAW_THIRD_LAST);
        // addAnvil(r, SHEET, TRAPDOOR, false, GENERAL, BEND_LAST, DRAW_SECOND_LAST, DRAW_THIRD_LAST);

        // Armor
        // addAnvil(r, DOUBLE_SHEET, UNFINISHED_HELMET, true, ARMOR, HIT_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);
        // addAnvil(r, DOUBLE_SHEET, UNFINISHED_CHESTPLATE, true, ARMOR, HIT_LAST, HIT_SECOND_LAST, UPSET_THIRD_LAST);
        // addAnvil(r, DOUBLE_SHEET, UNFINISHED_GREAVES, true, ARMOR, BEND_ANY, DRAW_ANY, HIT_ANY);
        // addAnvil(r, SHEET, UNFINISHED_BOOTS, true, ARMOR, BEND_LAST, BEND_SECOND_LAST, SHRINK_THIRD_LAST);

        // Shields
        // addAnvil(r, DOUBLE_SHEET, SHIELD, true, ARMOR, UPSET_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);

        // Misc
        // addAnvil(r, "iron_bars", SHEET, WROUGHT_IRON, new ItemStack(Blocks.IRON_BARS, 8), Metal.Tier.TIER_III, GENERAL, UPSET_LAST, PUNCH_SECOND_LAST, PUNCH_THIRD_LAST);
        // addAnvil(r, "iron_bars_double", DOUBLE_SHEET, WROUGHT_IRON, new ItemStack(Blocks.IRON_BARS, 16), Metal.Tier.TIER_III, GENERAL, UPSET_LAST, PUNCH_SECOND_LAST, PUNCH_THIRD_LAST);
        // addAnvil(r, "iron_door", SHEET, WROUGHT_IRON, new ItemStack(Items.IRON_DOOR), Metal.Tier.TIER_III, GENERAL, HIT_LAST, DRAW_NOT_LAST, PUNCH_NOT_LAST);
        // addAnvil(r, "red_steel_bucket", SHEET, RED_STEEL, new ItemStack(ItemMetal.get(Metal.RED_STEEL, BUCKET)), Metal.Tier.TIER_VI, GENERAL, BEND_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);
        // addAnvil(r, "blue_steel_bucket", SHEET, BLUE_STEEL, new ItemStack(ItemMetal.get(Metal.BLUE_STEEL, BUCKET)), Metal.Tier.TIER_VI, GENERAL, BEND_LAST, BEND_SECOND_LAST, BEND_THIRD_LAST);
        // addAnvil(r, "wrought_iron_grill", DOUBLE_SHEET, WROUGHT_IRON, new ItemStack(ItemsTFC_old.WROUGHT_IRON_GRILL), Metal.Tier.TIER_III, GENERAL, DRAW_ANY, PUNCH_LAST, PUNCH_NOT_LAST);
        // addAnvil(r, "brass_mechanisms", INGOT, BRASS, new ItemStack(ItemsTFC_old.BRASS_MECHANISMS, 2), Metal.Tier.TIER_II, GENERAL, PUNCH_LAST, HIT_SECOND_LAST, PUNCH_THIRD_LAST);
    }
}
