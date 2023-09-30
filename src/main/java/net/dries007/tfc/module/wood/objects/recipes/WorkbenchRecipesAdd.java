package net.dries007.tfc.module.wood.objects.recipes;

import net.dries007.tfc.common.objects.recipes.IWorkbenchRecipes;
import net.dries007.tfc.module.wood.StorageWood;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariants;
import net.dries007.tfc.module.wood.api.variant.item.WoodItemVariants;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class WorkbenchRecipesAdd implements IWorkbenchRecipes {

    @Override
    public void register() {
        for (var type : WoodType.getWoodTypes()) {
            // Barrel
            registerShaped("wood/barrel/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.BARREL, type)),
                    "L L", "L L", "LLL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Boat
            registerShaped("wood/boat/" + type,
                    new ItemStack(StorageWood.getWoodItem(WoodItemVariants.BOAT, type)),
                    "   ", "L L", "LLL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Bookshelf
            registerShaped("wood/bookshelf/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.BOOKSHELF, type)),
                    "LLL", "BBB", "LLL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type)),
                    'B', new ItemStack(Items.BOOK)
            );

            // Chest
            registerShaped("wood/chest/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.CHEST, type)),
                    "LLL", "L L", "LLL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Door

            // Fence
            registerShaped("wood/fence/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.FENCE, type), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type)),
                    'P', new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.PLANKS, type))
            );

            // Fence Gate
            registerShaped("wood/fence_gate/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.FENCE_GATE, type), 2),
                    "LPL", "LPL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type)),
                    'P', new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.PLANKS, type))
            );

            // Fence Log
            registerShaped(
                    "wood/fence_log_" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.FENCE_LOG, type), 8),
                    "PLP", "PLP",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type)),
                    'P', new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.LOG, type))
            );

            // Loom
            registerShaped("wood/loom/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.LOOM, type)),
                    "LLL", "LSL", "L L",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type)),
                    'S', new ItemStack(Items.STICK)
            );

            // Log -> Lumber
            registerShaped(
                    "wood/lumber_log/" + type,
                    new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type), 8),
                    "s", "L",
                    'L', new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.LOG, type))
            );

            // Planks -> Lumber
            registerShaped("wood/lumber_planks_" + type,
                    new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type), 4),
                    "s", "P",
                    'P', new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.PLANKS, type))
            );

            // Planks
            registerShaped("wood/planks/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.PLANKS, type)),
                    "LL", "LL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Pressure Plate
            registerShaped("wood/pressure_plate/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.PRESSURE_PLATE, type)),
                    "LL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Slab
            registerShaped("wood/slabs/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.SLAB, type), 6),
                    "LLL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Stairs
            registerShaped("wood/stairs/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.STAIRS, type), 8),
                    "L  ", "LL ", "LLL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Support
            registerShaped(
                    "wood/support/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.SUPPORT, type), 8),
                    "sL", " L",
                    'L', new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.LOG, type))
            );

            // Tool Rack
            registerShaped("wood/tool_rack/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.TOOL_RACK, type)),
                    "LLL", "   ", "LLL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Trapdoor
            registerShaped("wood/trapdoor/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.TRAPDOOR, type), 3),
                    "LLL", "LLL",
                    'L', new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Trapdoor
            registerShaped("wood/workbench/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.WORKBENCH, type)),
                    "PP", "PP",
                    'P', new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.PLANKS, type))
            );

            // Button
            registerShapeless("wood/button/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.BOOKSHELF, type)),
                    new ItemStack(StorageWood.getWoodItem(WoodItemVariants.LUMBER, type))
            );

            // Trapped chest
            registerShapeless("wood/trapped_chest/" + type,
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.CHEST_TRAP, type)),
                    new ItemStack(StorageWood.getWoodBlock(WoodBlockVariants.CHEST, type)),
                    new ItemStack(Blocks.TRIPWIRE)
            );
        }
    }
}
