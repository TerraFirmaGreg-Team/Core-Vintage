package net.dries007.tfc.module.wood.api.variant.block;

import net.dries007.tfc.module.wood.objects.blocks.*;

public class WoodBlockVariantHandler {
    public static void init() {

        WoodBlockVariants.LOG = new WoodBlockVariant("log", BlockWoodLog::new);
        // STRIPPED_LOG = new WoodBlockVariant("stripped_log");
        WoodBlockVariants.LEAVES = new WoodBlockVariant("leaves", BlockWoodLeaves::new);
        WoodBlockVariants.SAPLING = new WoodBlockVariant("sapling", BlockWoodSapling::new);
        // POTTED_SAPLING = new WoodBlockVariant("potted_sapling");
        WoodBlockVariants.PLANKS = new WoodBlockVariant("planks", BlockWoodPlanks::new);
        WoodBlockVariants.BOOKSHELF = new WoodBlockVariant("bookshelf", BlockWoodBookshelf::new);
        WoodBlockVariants.DOOR = new WoodBlockVariant("door", BlockWoodDoor::new);
        WoodBlockVariants.TRAPDOOR = new WoodBlockVariant("trapdoor", BlockWoodTrapDoor::new);
        WoodBlockVariants.FENCE = new WoodBlockVariant("fence", BlockWoodFence::new);
        WoodBlockVariants.FENCE_LOG = new WoodBlockVariant("fence_log", BlockWoodFenceLog::new);
        WoodBlockVariants.FENCE_GATE = new WoodBlockVariant("fence_gate", BlockWoodFenceGate::new);
        WoodBlockVariants.FENCE_GATE_LOG = new WoodBlockVariant("fence_gate_log", BlockWoodFenceGateLog::new);
        WoodBlockVariants.BUTTON = new WoodBlockVariant("button", BlockWoodButton::new);
        WoodBlockVariants.PRESSURE_PLATE = new WoodBlockVariant("pressure_plate", BlockWoodPressurePlate::new);
        WoodBlockVariants.SLAB_DOUBLE = new WoodBlockVariant("slab_double", BlockWoodSlab.Double::new);
        WoodBlockVariants.SLAB = new WoodBlockVariant("slab", BlockWoodSlab.Half::new);
        WoodBlockVariants.STAIRS = new WoodBlockVariant("stairs", BlockWoodStairs::new);
        WoodBlockVariants.TOOL_RACK = new WoodBlockVariant("tool_rack", BlockWoodToolRack::new);
        WoodBlockVariants.SUPPORT = new WoodBlockVariant("support", BlockWoodSupport::new);
        WoodBlockVariants.WORKBENCH = new WoodBlockVariant("workbench", BlockWoodWorkbench::new);
        WoodBlockVariants.CHEST_TRAP = new WoodBlockVariant("chest_trap", BlockWoodChest::new);
        WoodBlockVariants.CHEST = new WoodBlockVariant("chest", BlockWoodChest::new);
        WoodBlockVariants.LOOM = new WoodBlockVariant("loom", BlockWoodLoom::new);
        WoodBlockVariants.BARREL = new WoodBlockVariant("barrel", BlockWoodBarrel::new);
        WoodBlockVariants.LADDER = new WoodBlockVariant("ladder", BlockWoodLadder::new);
    }
}
