package net.dries007.tfc.module.wood.common.recipes;

import net.dries007.tfc.module.core.common.objects.recipes.IWorkbenchRecipes;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.common.WoodStorage;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariants.*;
import static net.dries007.tfc.module.wood.api.variant.item.WoodItemVariants.BOAT;
import static net.dries007.tfc.module.wood.api.variant.item.WoodItemVariants.LUMBER;

public class WoodWorkbenchRecipes implements IWorkbenchRecipes {

    @Override
    public void register() {
        for (var type : WoodType.getWoodTypes()) {
            // Barrel
            registerShaped("wood/barrel/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(BARREL, type)),
                    "L L", "L L", "LLL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Boat
            registerShaped("wood/boat/" + type,
                    new ItemStack(WoodStorage.getWoodItem(BOAT, type)),
                    "   ", "L L", "LLL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Bookshelf
            registerShaped("wood/bookshelf/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(BOOKSHELF, type)),
                    "LLL", "BBB", "LLL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type)),
                    'B', new ItemStack(Items.BOOK)
            );

            // Chest
            registerShaped("wood/chest/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(CHEST, type)),
                    "LLL", "L L", "LLL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Door

            // Fence
            registerShaped("wood/fence/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(FENCE, type), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type)),
                    'P', new ItemStack(WoodStorage.getWoodBlock(PLANKS, type))
            );

            // Fence Gate
            registerShaped("wood/fence_gate/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(FENCE_GATE, type), 2),
                    "LPL", "LPL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type)),
                    'P', new ItemStack(WoodStorage.getWoodBlock(PLANKS, type))
            );

            // Fence Log
            registerShaped(
                    "wood/fence_log_" + type,
                    new ItemStack(WoodStorage.getWoodBlock(FENCE_LOG, type), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type)),
                    'P', new ItemStack(WoodStorage.getWoodBlock(LOG, type))
            );

            // Loom
            registerShaped("wood/loom/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(LOOM, type)),
                    "LLL", "LSL", "L L",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type)),
                    'S', new ItemStack(Items.STICK)
            );

            // Log -> Lumber
            registerShaped(
                    "wood/lumber_log/" + type,
                    new ItemStack(WoodStorage.getWoodItem(LUMBER, type), 8),
                    "s", "L",
                    'L', new ItemStack(WoodStorage.getWoodBlock(LOG, type))
            );

            // Planks -> Lumber
            registerShaped("wood/lumber_planks_" + type,
                    new ItemStack(WoodStorage.getWoodItem(LUMBER, type), 4),
                    "s", "P",
                    'P', new ItemStack(WoodStorage.getWoodBlock(PLANKS, type))
            );

            // Planks
            registerShaped("wood/planks/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(PLANKS, type)),
                    "LL", "LL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Pressure Plate
            registerShaped("wood/pressure_plate/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(PRESSURE_PLATE, type)),
                    "LL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Slab
            registerShaped("wood/slabs/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(SLAB, type), 6),
                    "LLL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Stairs
            registerShaped("wood/stairs/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(STAIRS, type), 8),
                    "L  ", "LL ", "LLL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Support
            registerShaped(
                    "wood/support/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(SUPPORT, type), 8),
                    "sL", " L",
                    'L', new ItemStack(WoodStorage.getWoodBlock(LOG, type))
            );

            // Tool Rack
            registerShaped("wood/tool_rack/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(TOOL_RACK, type)),
                    "LLL", "   ", "LLL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Trapdoor
            registerShaped("wood/trapdoor/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(TRAPDOOR, type), 3),
                    "LLL", "LLL",
                    'L', new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Trapdoor
            registerShaped("wood/workbench/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(WORKBENCH, type)),
                    "PP", "PP",
                    'P', new ItemStack(WoodStorage.getWoodBlock(PLANKS, type))
            );

            // Button
            registerShapeless("wood/button/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(BOOKSHELF, type)),
                    new ItemStack(WoodStorage.getWoodItem(LUMBER, type))
            );

            // Trapped chest
            registerShapeless("wood/trapped_chest/" + type,
                    new ItemStack(WoodStorage.getWoodBlock(CHEST_TRAP, type)),
                    new ItemStack(WoodStorage.getWoodBlock(CHEST, type)),
                    new ItemStack(Blocks.TRIPWIRE)
            );
        }
    }
}
