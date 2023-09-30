package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.core.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.module.core.api.recipes.knapping.KnappingType;
import net.dries007.tfc.common.objects.items.ItemsTFC_old;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.module.ceramic.StorageCeramic;
import net.dries007.tfc.module.core.init.ItemsCore;
import net.dries007.tfc.module.core.init.RegistryCore;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public class KnappingRecipes {

    public static void register() {
        var registry = RegistryCore.KNAPPING;

        /* ROCK ITEMS */


        /* CLAY ITEMS */
        for (var orePrefix : OrePrefix.values()) {
            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;

            if (extendedOrePrefix.getClayKnappingPattern() != null) {
                var amount = orePrefix == OrePrefix.ingot ? 2 : 1;

                registry.register(new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(StorageCeramic.UNFIRED_MOLDS.get(orePrefix), amount), extendedOrePrefix.getClayKnappingPattern()).setRegistryName(orePrefix.name.toLowerCase() + "_mold"));
            }
        }

        registry.registerAll(
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_VESSEL), " XXX ", "XXXXX", "XXXXX", "XXXXX", " XXX ").setRegistryName("clay_small_vessel"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_JUG), " X   ", "XXXX ", "XXX X", "XXXX ", "XXX  ").setRegistryName("clay_jug"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_POT), "X   X", "X   X", "X   X", "XXXXX", " XXX ").setRegistryName("clay_pot"),
                new KnappingRecipeSimple(KnappingType.CLAY, false, new ItemStack(ItemsTFC_old.UNFIRED_BOWL, 2), "X   X", " XXX ").setRegistryName(Tags.MOD_ID, "clay_bowl"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_BOWL, 4), "X   X", " XXX ", "     ", "X   X", " XXX ").setRegistryName("clay_bowl_2"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_LARGE_VESSEL), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName("clay_large_vessel"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName("clay_brick"),
                new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_FLOWER_POT, 2), " X X ", " XXX ", "     ", " X X ", " XXX ").setRegistryName("clay_flower_pot")
        );

        /* LEATHER ITEMS */
        registry.registerAll(
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.LEATHER_HELMET), "XXXXX", "X   X", "X   X", "     ", "     ").setRegistryName("leather_helmet"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.LEATHER_CHESTPLATE), "X   X", "XXXXX", "XXXXX", "XXXXX", "XXXXX").setRegistryName("leather_chestplate"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.LEATHER_LEGGINGS), "XXXXX", "XXXXX", "XX XX", "XX XX", "XX XX").setRegistryName("leather_leggings"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.LEATHER_BOOTS), "XX   ", "XX   ", "XX   ", "XXXX ", "XXXXX").setRegistryName("leather_boots"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(Items.SADDLE), "  X  ", "XXXXX", "XXXXX", "XXXXX", "  X  ").setRegistryName("leather_saddle"),
                new KnappingRecipeSimple(KnappingType.LEATHER, true, new ItemStack(ItemsCore.QUIVER), " XXXX", "X XXX", "X XXX", "X XXX", " XXXX").setRegistryName("leather_quiver")
        );

        /* FIRE CLAY ITEMS */
        registry.registerAll(
                new KnappingRecipeSimple(KnappingType.FIRE_CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_CRUCIBLE), "X   X", "X   X", "X   X", "X   X", "XXXXX").setRegistryName("fire_clay_crucible"),
                new KnappingRecipeSimple(KnappingType.FIRE_CLAY, true, new ItemStack(ItemsTFC_old.UNFIRED_FIRE_BRICK, 3), "XXXXX", "     ", "XXXXX", "     ", "XXXXX").setRegistryName("fire_clay_brick")
        );

    }
}
