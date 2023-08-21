package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.recipes.workbench.UnmoldRecipe;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariants;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariants;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class WorkbenchRecipes {

    public static void register() {
        registerUnmoldRecipes();
        registerWoodRecipes();
        registerRockRecipes();
    }

    private static void registerUnmoldRecipes() {
        var registry = ForgeRegistries.RECIPES;

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            for (var orePrefix : OrePrefix.values()) {
                var extendedOrePrefix = (IOrePrefixExtension) orePrefix;
                if (material.hasProperty(TFGPropertyKey.HEAT) && extendedOrePrefix.getHasMold()) {
                    if (material.hasFlag(TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED) || orePrefix == OrePrefix.ingot) {
                        registry.register(
                                new UnmoldRecipe(new ItemStack(TFCBlocks.FIRED_MOLDS.get(orePrefix)), material, 1).setRegistryName(MOD_ID, "unmold_" + orePrefix.name + "_" + material.getName())
                        );
                    }

                }
            }
        }
    }

    private static void registerWoodRecipes() {
        for (var woodType : WoodType.getWoodTypes()) {
            // Barrel
            registerShaped(
                    "wood/barrel/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.BARREL, woodType)),
                    "L L", "L L", "LLL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Boat
            registerShaped(
                    "wood/boat/" + woodType,
                    new ItemStack(TFCBlocks.getBoatItem(woodType)),
                    "   ", "L L", "LLL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Bookshelf
            registerShaped(
                    "wood/bookshelf/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.BOOKSHELF, woodType)),
                    "LLL", "BBB", "LLL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType)),
                    'B', new ItemStack(Items.BOOK)
            );

            // Button
            registerShapeless(
                    "wood/button/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.BOOKSHELF, woodType)),
                    new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Chest
            registerShaped(
                    "wood/chest/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.CHEST, woodType)),
                    "LLL", "L L", "LLL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Trapped chest
            registerShapeless(
                    "wood/trapped_chest/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.CHEST_TRAP, woodType)),
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.CHEST, woodType)),
                    new ItemStack(Blocks.TRIPWIRE)
            );

            // Door

            // Fence
            registerShaped(
                    "wood/fence/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.FENCE, woodType), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType)),
                    'P', new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
            );

            // Fence Gate
            registerShaped(
                    "wood/fence_gate/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.FENCE_GATE, woodType), 2),
                    "LPL", "LPL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType)),
                    'P', new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
            );

            // Fence Log
            /*
            registerShaped(
                    "wood/fence_log_" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.LOG_FENCE, woodType), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType)),
                    'P', new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.LOG, woodType))
            );*/

            // Loom
            registerShaped(
                    "wood/loom/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.LOOM, woodType)),
                    "LLL", "LSL", "L L",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType)),
                    'S', new ItemStack(Items.STICK)
            );

            // Log -> Lumber
            registerShaped(
                    "wood/lumber_log/" + woodType,
                    new ItemStack(TFCBlocks.getLumberItem(woodType), 8),
                    "s", "L",
                    'L', new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.LOG, woodType))
            );

            // Planks -> Lumber
            registerShaped(
                    "wood/lumber_planks_" + woodType,
                    new ItemStack(TFCBlocks.getLumberItem(woodType), 4),
                    "s", "P",
                    'P', new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
            );

            // Planks
            registerShaped(
                    "wood/planks/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.PLANKS, woodType)),
                    "LL", "LL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Pressure Plate
            registerShaped(
                    "wood/pressure_plate/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.PRESSURE_PLATE, woodType)),
                    "LL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Slab
            registerShaped(
                    "wood/slabs/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.SLAB, woodType), 6),
                    "LLL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Stairs
            registerShaped(
                    "wood/stairs/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.STAIRS, woodType), 8),
                    "L  ", "LL ", "LLL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Support
            registerShaped(
                    "wood/support/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.SUPPORT, woodType), 8),
                    "sL", " L",
                    'L', new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.LOG, woodType))
            );

            // Tool Rack
            registerShaped(
                    "wood/tool_rack/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.TOOL_RACK, woodType)),
                    "LLL", "   ", "LLL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Trapdoor
            registerShaped(
                    "wood/trapdoor/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.TRAPDOOR, woodType), 3),
                    "LLL", "LLL",
                    'L', new ItemStack(TFCBlocks.getLumberItem(woodType))
            );

            // Trapdoor
            registerShaped(
                    "wood/workbench/" + woodType,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.WORKBENCH, woodType)),
                    "PP", "PP",
                    'P', new ItemStack(TFCBlocks.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
            );
        }
    }

    private static void registerRockRecipes() {
        for (var rockType : RockType.getRockTypes()) {
            registerShaped(
                    "rock/smooth/" + rockType,
                    new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.SMOOTH, rockType)),
                    "Re",
                    'R', new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.RAW, rockType))
            );

            registerShaped(
                    "rock/stairs_raw/" + rockType,
                    new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.STAIRS_RAW, rockType)),
                    "R  ", "RR ", "RRR",
                    'R', new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.RAW, rockType))
            );

            registerShaped(
                    "rock/stairs_smooth/" + rockType,
                    new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.STAIRS_SMOOTH, rockType)),
                    "S  ", "SS ", "SSS",
                    'S', new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.SMOOTH, rockType))
            );

            registerShaped(
                    "rock/stairs_brick/" + rockType,
                    new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.STAIRS_BRICK, rockType)),
                    "B  ", "BB ", "BBB",
                    'B', new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.BRICK, rockType))
            );

            registerShaped(
                    "rock/slabs_raw/" + rockType,
                    new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.SLAB_RAW, rockType)),
                    "RRR",
                    'R', new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.RAW, rockType))
            );

            registerShaped(
                    "rock/slabs_smooth/" + rockType,
                    new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.SLAB_SMOOTH, rockType)),
                    "SSS",
                    'S', new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.SMOOTH, rockType))
            );

            registerShaped(
                    "rock/slabs_brick/" + rockType,
                    new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.SLAB_BRICK, rockType)),
                    "BBB",
                    'B', new ItemStack(TFCBlocks.getRockBlock(RockBlockVariants.BRICK, rockType))
            );
        }
    }

    private static void registerShaped(String recipeName, @Nonnull ItemStack output, Object... recipePattern) {
        ModHandler.addShapedRecipe(recipeName, output, recipePattern);
    }

    private static void registerShapeless(String recipeName, @Nonnull ItemStack output, Object... recipePattern) {
        ModHandler.addShapelessRecipe(recipeName, output, recipePattern);
    }
}
