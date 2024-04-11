package su.terrafirmagreg.modules.wood.api.types.variant.block;

import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodBarrel;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodBookshelf;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodButton;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodChest;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodDoor;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodFence;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodFenceGate;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodFenceGateLog;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodFenceLog;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodLadder;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodLoom;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodPlanks;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodPressurePlate;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodSlab;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodStairs;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodSupport;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodToolRack;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodTrapDoor;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodWall;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodWorkbench;


import static su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariants.*;

public class WoodBlockVariantHandler {

    public static void init() {
        //		WoodBlockVariants.LOG = new WoodBlockVariant
        //				.Builder("log")
        //				.setFactory(BlockWoodLog::new)
        //				.build();
        //
        //		WoodBlockVariants.LEAVES = new WoodBlockVariant
        //				.Builder("leaves")
        //				.setFactory(BlockWoodLeaves::new)
        //				.build();
        //
        //		WoodBlockVariants.SAPLING = new WoodBlockVariant
        //				.Builder("sapling")
        //				.setFactory(BlockWoodSapling::new)
        //				.build();

        PLANKS = new WoodBlockVariant.Builder("planks")
                .setFactory(BlockWoodPlanks::new)
                .setFireInfo(5, 20)
                .build();

        STAIRS_PLANKS = new WoodBlockVariant.Builder("stairs/planks")
                .setFactory((v, t) -> new BlockWoodStairs(PLANKS, v, t))
                .setFireInfo(5, 20)
                .build();

        SLAB_DOUBLE_PLANKS = new WoodBlockVariant.Builder("slab_double/planks")
                .setFactory((v, t) -> new BlockWoodSlab.Double(PLANKS, v, t))
                .setFireInfo(5, 20)
                .build();

        SLAB_PLANKS = new WoodBlockVariant.Builder("slab/planks")
                .setFactory((v, t) -> new BlockWoodSlab.Half(PLANKS, SLAB_DOUBLE_PLANKS, v, t))
                .setFireInfo(5, 20)
                .build();

        WALL_PLANKS = new WoodBlockVariant.Builder("wall/planks")
                .setFactory((v, t) -> new BlockWoodWall(PLANKS, v, t))
                .setFireInfo(5, 20)
                .build();

        BOOKSHELF = new WoodBlockVariant.Builder("bookshelf")
                .setFactory(BlockWoodBookshelf::new)
                .setFireInfo(30, 20)
                .build();

        DOOR = new WoodBlockVariant.Builder("door")
                .setFactory(BlockWoodDoor::new)
                .setFireInfo(5, 20)
                .build();

        TRAPDOOR = new WoodBlockVariant.Builder("trapdoor")
                .setFactory(BlockWoodTrapDoor::new)
                .setFireInfo(5, 20)
                .build();

        FENCE = new WoodBlockVariant.Builder("fence")
                .setFactory(BlockWoodFence::new)
                .setFireInfo(5, 20)
                .build();

        FENCE_LOG = new WoodBlockVariant.Builder("fence_log")
                .setFactory(BlockWoodFenceLog::new)
                .setFireInfo(5, 20)
                .build();

        FENCE_GATE = new WoodBlockVariant.Builder("fence_gate")
                .setFactory(BlockWoodFenceGate::new)
                .setFireInfo(5, 20)
                .build();

        FENCE_GATE_LOG = new WoodBlockVariant.Builder("fence_gate_log")
                .setFactory(BlockWoodFenceGateLog::new)
                .setFireInfo(5, 20)
                .build();

        BUTTON = new WoodBlockVariant.Builder("button")
                .setFactory(BlockWoodButton::new)
                .setFireInfo(5, 20)
                .build();

        PRESSURE_PLATE = new WoodBlockVariant.Builder("pressure_plate")
                .setFactory(BlockWoodPressurePlate::new)
                .setFireInfo(5, 20)
                .build();

        TOOL_RACK = new WoodBlockVariant.Builder("tool_rack")
                .setFactory(BlockWoodToolRack::new)
                .setFireInfo(5, 20)
                .build();

        SUPPORT = new WoodBlockVariant.Builder("support")
                .setFactory(BlockWoodSupport::new)
                .setFireInfo(5, 20)
                .build();

        WORKBENCH = new WoodBlockVariant.Builder("workbench")
                .setFactory(BlockWoodWorkbench::new)
                .setFireInfo(5, 20)
                .build();

        CHEST_TRAPPED = new WoodBlockVariant.Builder("chest_trapped")
                .setFactory(BlockWoodChest::new)
                .setFireInfo(5, 20)
                .build();

        CHEST = new WoodBlockVariant.Builder("chest")
                .setFactory(BlockWoodChest::new)
                .setFireInfo(5, 20)
                .build();

        LOOM = new WoodBlockVariant.Builder("loom")
                .setFactory(BlockWoodLoom::new)
                .setFireInfo(5, 20)
                .build();

        BARREL = new WoodBlockVariant.Builder("barrel")
                .setFactory(BlockWoodBarrel::new)
                .setFireInfo(5, 20)
                .build();

        LADDER = new WoodBlockVariant.Builder("ladder")
                .setFactory(BlockWoodLadder::new)
                .setFireInfo(5, 20)
                .build();

        //		WoodBlockVariants.CHOPPER = new WoodBlockVariant
        //				.Builder("chopper")
        //				.setFactory(BlockWoodChopper::new)
        //				.build();
    }
}
