package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.recipes.workbench.UnmoldRecipe;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.module.ceramic.StorageCeramic;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class WorkbenchRecipes {


    public static void register() {
        registerUnmoldRecipes();
        //registerWoodRecipes();
        //registerRockRecipes();
    }

    private static void registerUnmoldRecipes() {
        var registry = ForgeRegistries.RECIPES;

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            for (var orePrefix : OrePrefix.values()) {
                var extendedOrePrefix = (IOrePrefixExtension) orePrefix;
                if (material.hasProperty(TFGPropertyKey.HEAT) && extendedOrePrefix.getHasMold()) {
                    if (material.hasFlag(TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED) || orePrefix == OrePrefix.ingot) {
                        registry.register(
                                new UnmoldRecipe(new ItemStack(StorageCeramic.FIRED_MOLDS.get(orePrefix)), material, 1).setRegistryName(Tags.MOD_ID, "unmold_" + orePrefix.name + "_" + material.getName())
                        );
                    }

                }
            }
        }
    }


//    private static void registerRockRecipes() {
//        for (var type : RockType.getRockTypes()) {
//            registerShaped(
//                    "rock/smooth/" + type,
//                    new ItemStack(RockStorage.getRockBlock(SMOOTH, type)),
//                    "Re",
//                    'R', new ItemStack(RockStorage.getRockBlock(RAW, type))
//            );
//
//            registerShaped(
//                    "rock/stairs_raw/" + type,
//                    new ItemStack(RockStorage.getRockBlock(STAIRS_RAW, type)),
//                    "R  ", "RR ", "RRR",
//                    'R', new ItemStack(RockStorage.getRockBlock(RAW, type))
//            );
//
//            registerShaped(
//                    "rock/stairs_smooth/" + type,
//                    new ItemStack(RockStorage.getRockBlock(STAIRS_SMOOTH, type)),
//                    "S  ", "SS ", "SSS",
//                    'S', new ItemStack(RockStorage.getRockBlock(SMOOTH, type))
//            );
//
//            registerShaped(
//                    "rock/stairs_brick/" + type,
//                    new ItemStack(RockStorage.getRockBlock(STAIRS_BRICK, type)),
//                    "B  ", "BB ", "BBB",
//                    'B', new ItemStack(RockStorage.getRockBlock(BRICKS, type))
//            );
//
//            registerShaped(
//                    "rock/slabs_raw/" + type,
//                    new ItemStack(RockStorage.getRockBlock(SLAB_RAW, type)),
//                    "RRR",
//                    'R', new ItemStack(RockStorage.getRockBlock(RAW, type))
//            );
//
//            registerShaped(
//                    "rock/slabs_smooth/" + type,
//                    new ItemStack(RockStorage.getRockBlock(SLAB_SMOOTH, type)),
//                    "SSS",
//                    'S', new ItemStack(RockStorage.getRockBlock(SMOOTH, type))
//            );
//
//            registerShaped(
//                    "rock/slabs_brick/" + type,
//                    new ItemStack(RockStorage.getRockBlock(SLAB_BRICK, type)),
//                    "BBB",
//                    'B', new ItemStack(RockStorage.getRockBlock(BRICKS, type))
//            );
//        }
//    }


}
