package net.dries007.tfc.objects.recipes.category;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.ToolItems;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariants;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.recipes.UnmoldRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class WorkbenchRecipes {

    private static final IForgeRegistry<IRecipe> workbenchRegistry = ForgeRegistries.RECIPES;

    public static void onRegisterWorkbenchRecipes() {
        registerUnmoldRecipes();
        registerProcessingRecipes();
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
                                new UnmoldRecipe(new ItemStack(ItemMold.get(orePrefix)), material, 1).setRegistryName(MOD_ID, "unmold_" + orePrefix.name + "_" + material.getName())
                        );
                    }

                }
            }
        }
    }

    private static void registerProcessingRecipes() {
        TFGOrePrefix.oreChunk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

        TFGOrePrefix.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreChalk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreChert.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreClaystone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreConglomerate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreDacite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreDolomite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreRhyolite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSchist.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    }

    private static void registerWoodRecipes() {
        for (var woodType : WoodType.getWoodTypes()) {
            // Barrel
            registerShaped(
                    "barrel_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.BARREL, woodType)),
                    "L L", "L L", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Boat
            registerShaped(
                    "boat_" + woodType,
                    new ItemStack(TFCStorage.getBoatItem(woodType)),
                    "   ", "L L", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Bookshelf
            registerShaped(
                    "bookshelf_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.BOOKSHELF, woodType)),
                    "LLL", "BBB", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType)),
                    'B', new ItemStack(Items.BOOK)
            );

            // Button
            registerShapeless(
                    "button_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.BOOKSHELF, woodType)),
                    new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Chest
            registerShaped(
                    "chest_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.CHEST, woodType)),
                    "LLL", "L L", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Trapped chest
            registerShapeless(
                    "trapped_chest_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.CHEST_TRAP, woodType)),
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.CHEST, woodType)),
                    new ItemStack(Blocks.TRIPWIRE)
            );

            // Door

            // Fence
            registerShaped(
                    "fence_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.FENCE, woodType), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType)),
                    'P', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PLANKS, woodType))
            );

            // Fence Gate
            registerShaped(
                    "fence_gate_" + woodType,
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
                    "loom_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.LOOM, woodType)),
                    "LLL", "LSL", "L L",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType)),
                    'S', new ItemStack(Items.STICK)
            );

            // Log -> Lumber
            registerShaped(
                    "lumber_log_" + woodType,
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
                    "planks_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PLANKS, woodType)),
                    "LL", "LL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Pressure Plate
            registerShaped(
                    "pressure_plate_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.PRESSURE_PLATE, woodType)),
                    "LL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Slab
            registerShaped(
                    "slab_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.SLAB, woodType), 6),
                    "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Stairs
            registerShaped(
                    "slab_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.STAIRS, woodType), 8),
                    "L  ", "LL ", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Support
            registerShaped(
                    "support_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.SUPPORT, woodType), 8),
                    "sL", " L",
                    'L', new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.LOG, woodType))
            );

            // Tool Rack
            registerShaped(
                    "tool_rack_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.TOOL_RACK, woodType)),
                    "LLL", "   ", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Trapdoor
            registerShaped(
                    "trapdoor_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.TRAPDOOR, woodType), 3),
                    "LLL", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );

            // Trapdoor
            registerShaped(
                    "workbench_" + woodType,
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
