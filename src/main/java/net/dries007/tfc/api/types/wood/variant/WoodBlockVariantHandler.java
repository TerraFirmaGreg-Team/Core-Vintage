package net.dries007.tfc.api.types.wood.variant;

import net.dries007.tfc.objects.blocks.wood.*;

import static net.dries007.tfc.api.types.wood.variant.WoodBlockVariants.*;

public class WoodBlockVariantHandler {
    public static void init() {

        LOG = new WoodBlockVariant("log", BlockWoodLog::new);
        // STRIPPED_LOG = new WoodBlockVariant("stripped_log");
        // WOOD = new WoodBlockVariant("wood");
        // STRIPPED_WOOD = new WoodBlockVariant("stripped_wood");
        LEAVES = new WoodBlockVariant("leaves", BlockWoodLeaves::new);
        PLANKS = new WoodBlockVariant("planks", BlockWoodPlanks::new);
        SAPLING = new WoodBlockVariant("sapling", BlockWoodSapling::new);
        // POTTED_SAPLING = new WoodBlockVariant("potted_sapling");
        BOOKSHELF = new WoodBlockVariant("bookshelf", BlockWoodBookshelf::new);
        DOOR = new WoodBlockVariant("door", BlockWoodDoor::new);
        TRAPDOOR = new WoodBlockVariant("trapdoor", BlockWoodTrapDoor::new);
        FENCE = new WoodBlockVariant("fence", BlockWoodFence::new);
        // LOG_FENCE = new WoodBlockVariant("log_fence");
        FENCE_GATE = new WoodBlockVariant("fence_gate", BlockWoodFenceGate::new);
        BUTTON = new WoodBlockVariant("button", BlockWoodButton::new);
        PRESSURE_PLATE = new WoodBlockVariant("pressure_plate", BlockWoodPressurePlate::new);
        SLAB_DOUBLE = new WoodBlockVariant("slab_double", BlockWoodSlab.Double::new);
        SLAB = new WoodBlockVariant("slab", BlockWoodSlab.Half::new);
        STAIRS = new WoodBlockVariant("stairs", BlockWoodStairs::new);
        TOOL_RACK = new WoodBlockVariant("tool_rack", BlockWoodToolRack::new);
        SUPPORT = new WoodBlockVariant("support", BlockWoodSupport::new);
        WORKBENCH = new WoodBlockVariant("workbench", BlockWoodWorkbench::new);
        CHEST_TRAP = new WoodBlockVariant("chest_trap", BlockWoodChest::new);
        CHEST = new WoodBlockVariant("chest", BlockWoodChest::new);
        LOOM = new WoodBlockVariant("loom", BlockWoodLoom::new);
        BARREL = new WoodBlockVariant("barrel", BlockWoodBarrel::new);
    }
}
