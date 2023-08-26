package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.recipes.workbench.UnmoldRecipe;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.block.RockBlockVariants;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariants.*;
import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.BOAT;
import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.LUMBER;

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
                                new UnmoldRecipe(new ItemStack(TFCItems.FIRED_MOLDS.get(orePrefix)), material, 1).setRegistryName(MOD_ID, "unmold_" + orePrefix.name + "_" + material.getName())
                        );
                    }

                }
            }
        }
    }

    private static void registerWoodRecipes() {
        for (var type : WoodType.getWoodTypes()) {
            // Barrel
            registerShaped(
                    "wood/barrel/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(BARREL, type)),
                    "L L", "L L", "LLL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Boat
            registerShaped(
                    "wood/boat/" + type,
                    new ItemStack(TFCItems.getWoodItem(BOAT, type)),
                    "   ", "L L", "LLL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Bookshelf
            registerShaped(
                    "wood/bookshelf/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(BOOKSHELF, type)),
                    "LLL", "BBB", "LLL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type)),
                    'B', new ItemStack(Items.BOOK)
            );

            // Button
            registerShapeless(
                    "wood/button/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(BOOKSHELF, type)),
                    new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Chest
            registerShaped(
                    "wood/chest/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(CHEST, type)),
                    "LLL", "L L", "LLL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Trapped chest
            registerShapeless(
                    "wood/trapped_chest/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(CHEST_TRAP, type)),
                    new ItemStack(TFCBlocks.getWoodBlock(CHEST, type)),
                    new ItemStack(Blocks.TRIPWIRE)
            );

            // Door

            // Fence
            registerShaped(
                    "wood/fence/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(FENCE, type), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type)),
                    'P', new ItemStack(TFCBlocks.getWoodBlock(PLANKS, type))
            );

            // Fence Gate
            registerShaped(
                    "wood/fence_gate/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(FENCE_GATE, type), 2),
                    "LPL", "LPL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type)),
                    'P', new ItemStack(TFCBlocks.getWoodBlock(PLANKS, type))
            );

            // Fence Log
            /*
            registerShaped(
                    "wood/fence_log_" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(WoodItemVariants.LOG_FENCE, type), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type)),
                    'P', new ItemStack(TFCBlocks.getWoodBlock(WoodItemVariants.LOG, type))
            );*/

            // Loom
            registerShaped(
                    "wood/loom/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(LOOM, type)),
                    "LLL", "LSL", "L L",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type)),
                    'S', new ItemStack(Items.STICK)
            );

            // Log -> Lumber
            registerShaped(
                    "wood/lumber_log/" + type,
                    new ItemStack(TFCItems.getWoodItem(LUMBER, type), 8),
                    "s", "L",
                    'L', new ItemStack(TFCBlocks.getWoodBlock(LOG, type))
            );

            // Planks -> Lumber
            registerShaped(
                    "wood/lumber_planks_" + type,
                    new ItemStack(TFCItems.getWoodItem(LUMBER, type), 4),
                    "s", "P",
                    'P', new ItemStack(TFCBlocks.getWoodBlock(PLANKS, type))
            );

            // Planks
            registerShaped(
                    "wood/planks/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(PLANKS, type)),
                    "LL", "LL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Pressure Plate
            registerShaped(
                    "wood/pressure_plate/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(PRESSURE_PLATE, type)),
                    "LL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Slab
            registerShaped(
                    "wood/slabs/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(SLAB, type), 6),
                    "LLL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Stairs
            registerShaped(
                    "wood/stairs/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(STAIRS, type), 8),
                    "L  ", "LL ", "LLL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Support
            registerShaped(
                    "wood/support/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(SUPPORT, type), 8),
                    "sL", " L",
                    'L', new ItemStack(TFCBlocks.getWoodBlock(LOG, type))
            );

            // Tool Rack
            registerShaped(
                    "wood/tool_rack/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(TOOL_RACK, type)),
                    "LLL", "   ", "LLL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Trapdoor
            registerShaped(
                    "wood/trapdoor/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(TRAPDOOR, type), 3),
                    "LLL", "LLL",
                    'L', new ItemStack(TFCItems.getWoodItem(LUMBER, type))
            );

            // Trapdoor
            registerShaped(
                    "wood/workbench/" + type,
                    new ItemStack(TFCBlocks.getWoodBlock(WORKBENCH, type)),
                    "PP", "PP",
                    'P', new ItemStack(TFCBlocks.getWoodBlock(PLANKS, type))
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
