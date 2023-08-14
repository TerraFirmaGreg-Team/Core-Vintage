package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.recipes.workbench.UnmoldRecipe;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariants;
import net.dries007.tfc.common.objects.items.ceramics.ItemMold;
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
    }

    private static void registerUnmoldRecipes() {
        var registry = ForgeRegistries.RECIPES;

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            for (var orePrefix : OrePrefix.values()) {
                var extendedOrePrefix = (IOrePrefixExtension) orePrefix;
                if (material.hasProperty(TFGPropertyKey.HEAT) && extendedOrePrefix.getHasMold()) {
                    if (material.hasFlag(TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED) || orePrefix == OrePrefix.ingot) {
                        registry.register(
                                new UnmoldRecipe(new ItemStack(TFCStorage.FIRED_MOLDS.get(orePrefix)), material, 1).setRegistryName(MOD_ID, "unmold_" + orePrefix.name + "_" + material.getName())
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
                    "barrel/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.BARREL, woodType)),
                    "L L", "L L", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Boat
            registerShaped(
                    "boat/" + woodType,
                    new ItemStack(TFCStorage.getBoatItem(woodType)),
                    "   ", "L L", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Bookshelf
            registerShaped(
                    "bookshelf/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.BOOKSHELF, woodType)),
                    "LLL", "BBB", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType)),
                    'B', new ItemStack(Items.BOOK)
            );

            // Button
            registerShapeless(
                    "button/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.BOOKSHELF, woodType)),
                    new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Chest
            registerShaped(
                    "chest/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.CHEST, woodType)),
                    "LLL", "L L", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Trapped chest
            registerShapeless(
                    "trapped_chest/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.CHEST_TRAP, woodType)),
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.CHEST, woodType)),
                    new ItemStack(Blocks.TRIPWIRE)
            );

            // Door

            // Fence
            registerShaped(
                    "fence/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.FENCE, woodType), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType)),
                    'P', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
            );

            // Fence Gate
            registerShaped(
                    "fence_gate/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.FENCE_GATE, woodType), 2),
                    "LPL", "LPL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType)),
                    'P', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
            );

            // Fence Log
            /*
            registerShaped(
                    "fence_log_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.LOG_FENCE, woodType), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType)),
                    'P', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.LOG, woodType))
            );*/

            // Loom
            registerShaped(
                    "loom/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.LOOM, woodType)),
                    "LLL", "LSL", "L L",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType)),
                    'S', new ItemStack(Items.STICK)
            );

            // Log -> Lumber
            registerShaped(
                    "lumber_log/" + woodType,
                    new ItemStack(TFCStorage.getLumberItem(woodType), 8),
                    "s", "L",
                    'L', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.LOG, woodType))
            );

            // Planks -> Lumber
            registerShaped(
                    "lumber_planks_" + woodType,
                    new ItemStack(TFCStorage.getLumberItem(woodType), 4),
                    "s", "P",
                    'P', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
            );

            // Planks
            registerShaped(
                    "planks/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PLANKS, woodType)),
                    "LL", "LL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Pressure Plate
            registerShaped(
                    "pressure_plate/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PRESSURE_PLATE, woodType)),
                    "LL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Slab
            registerShaped(
                    "slab/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.SLAB, woodType), 6),
                    "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Stairs
            registerShaped(
                    "stairs/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.STAIRS, woodType), 8),
                    "L  ", "LL ", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Support
            registerShaped(
                    "support/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.SUPPORT, woodType), 8),
                    "sL", " L",
                    'L', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.LOG, woodType))
            );

            // Tool Rack
            registerShaped(
                    "tool_rack/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.TOOL_RACK, woodType)),
                    "LLL", "   ", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Trapdoor
            registerShaped(
                    "trapdoor/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.TRAPDOOR, woodType), 3),
                    "LLL", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Trapdoor
            registerShaped(
                    "workbench/" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.WORKBENCH, woodType)),
                    "PP", "PP",
                    'P', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
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
