package net.dries007.tfc.module.rock.objects.recipes;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.dries007.tfc.module.core.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.module.rock.api.recipes.RecipeRockKnapping;
import net.minecraftforge.registries.IForgeRegistry;

public class KnappingRecipesAdd {

    public static void apply(IForgeRegistry<KnappingRecipe> registry) {

        registry.registerAll(
                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2),
                        "X  X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName("toolHeadKnife_1"),

                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2),
                        "X   X", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName("toolHeadKnife_2"),

                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2),
                        " X X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName("toolHeadKnife_3"),

                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Stone, 2),
                        "XXXXX", "XX   ", "     ", "XXXXX", "XX   ").setRegistryName("toolHeadHoe_1"),

                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Stone, 2),
                        "XXXXX", "XX   ", "     ", "XXXXX", "   XX").setRegistryName("toolHeadHoe_2")
//
//                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadShovel, Materials.Stone, 2),
//                        "XXX", "XXX", "XXX", "XXX", " X ").setRegistryName("toolHeadShovel_2"),
//
//                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadAxe, Materials.Stone, 2),
//                        " X   ", "XXXX ", "XXXXX", "XXXX ", " X   ").setRegistryName("toolHeadAxe_2"),
//
//                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Stone, 2),
//                        "XXXXX", "   XX").setRegistryName("toolHeadHoe_2"),
//
//                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadHammer, Materials.Stone, 2),
//                        "XXXXX", "XXXXX", "  X  ").setRegistryName("toolHeadHammer_2"),
//
//                new RecipeRockKnapping(OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2),
//                        "X ", "XX", "XX", "XX", "XX").setRegistryName("toolHeadKnife_2")

        );

        for (var orePrefix : OrePrefix.values()) {
            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;

            if (extendedOrePrefix.getRockKnappingPattern() != null) {
                var amount = orePrefix == OrePrefix.ingot ? 2 : 1;

                registry.register(new RecipeRockKnapping(OreDictUnifier.get(orePrefix, Materials.Stone, amount), extendedOrePrefix.getRockKnappingPattern()).setRegistryName(orePrefix.name.toLowerCase() + "_head"));
            }
        }
    }
}
