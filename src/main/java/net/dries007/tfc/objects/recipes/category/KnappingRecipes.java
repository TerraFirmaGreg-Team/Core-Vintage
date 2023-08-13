package net.dries007.tfc.objects.recipes.category;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeStone;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.objects.items.TFCItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class KnappingRecipes {

    public static void register() {
        var registry = TFCRegistries.KNAPPING;

        /* ROCK ITEMS */
        registry.registerAll(
                new KnappingRecipeStone(OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2), "X  X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName("toolheadknife_head_1"),
                new KnappingRecipeStone(OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2), "X   X", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName("toolheadknife_head_2"),
                new KnappingRecipeStone(OreDictUnifier.get(TFGOrePrefix.toolHeadKnife, Materials.Stone, 2), " X X ", "XX XX", "XX XX", "XX XX", "XX XX").setRegistryName("toolheadknife_head_3"),
                new KnappingRecipeStone(OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Stone, 2), "XXXXX", "XX   ", "     ", "XXXXX", "XX   ").setRegistryName("toolheadhoe_head_1"),
                new KnappingRecipeStone(OreDictUnifier.get(TFGOrePrefix.toolHeadHoe, Materials.Stone, 2), "XXXXX", "XX   ", "     ", "XXXXX", "   XX").setRegistryName("toolheadhoe_head_2")
        );

        for (var orePrefix : OrePrefix.values()) {
            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;

            if (extendedOrePrefix.getRockKnappingPattern() != null) {
                var amount = orePrefix == OrePrefix.ingot ? 2 : 1;

                registry.register(new KnappingRecipeStone(OreDictUnifier.get(orePrefix, Materials.Stone, amount), extendedOrePrefix.getRockKnappingPattern()).setRegistryName(orePrefix.name.toLowerCase() + "_head"));
            }
        }

        /* CLAY ITEMS */
        for (var orePrefix : OrePrefix.values()) {
            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;

            if (extendedOrePrefix.getClayKnappingPattern() != null) {
                var amount = orePrefix == OrePrefix.ingot ? 2 : 1;

                registry.register(new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemUnfiredMold.get(orePrefix), amount), extendedOrePrefix.getClayKnappingPattern()).setRegistryName(orePrefix.name.toLowerCase() + "_mold"));
            }
        }

        registry.registerAll(
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC.UNFIRED_VESSEL), " XXX ", "XXXXX", "XXXXX", "XXXXX", " XXX ").setRegistryName("clay_small_vessel"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC.UNFIRED_JUG), " X   ", "XXXX ", "XXX X", "XXXX ", "XXX  ").setRegistryName("clay_jug"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC.UNFIRED_POT), "X   X", "X   X", "X   X", "XXXXX", " XXX ").setRegistryName("clay_pot"),
                new KnappingRecipeSimple(KnappingType.CLAY, false, new ItemStack(ItemsTFC.UNFIRED_BOWL, 2), "X   X", " XXX ").setRegistryName(MOD_ID, "clay_bowl"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC.UNFIRED_BOWL, 4), "X   X", " XXX ", "     ", "X   X", " XXX ").setRegistryName("clay_bowl_2"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC.UNFIRED_LARGE_VESSEL), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName("clay_large_vessel"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC.UNFIRED_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName("clay_brick"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC.UNFIRED_FLOWER_POT, 2), " X X ", " XXX ", "     ", " X X ", " XXX ").setRegistryName("clay_flower_pot")
        );

        /* LEATHER ITEMS */
        registry.registerAll(
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.LEATHER_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("leather_helmet"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.LEATHER_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("leather_chestplate"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.LEATHER_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("leather_leggings"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.LEATHER_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("leather_boots"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.SADDLE), "  X  ", "XXXXX", "XXXXX", "XXXXX", "  X  ").setRegistryName("leather_saddle"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(TFCItems.QUIVER), " XXXX", "X XXX", "X XXX", "X XXX", " XXXX").setRegistryName("leather_quiver")
        );

        /* FIRE CLAY ITEMS */
        registry.registerAll(
                new KnappingRecipeSimple(KnappingType.FIRE_CLAY, true, new ItemStack(ItemsTFC.UNFIRED_CRUCIBLE), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName("fire_clay_crucible"),
                new KnappingRecipeSimple(KnappingType.FIRE_CLAY, true, new ItemStack(ItemsTFC.UNFIRED_FIRE_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName("fire_clay_brick")
        );

    }
}
