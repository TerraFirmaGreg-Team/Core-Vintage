package su.terrafirmagreg.modules.wood.api.types.variant.block;


import su.terrafirmagreg.modules.wood.objects.blocks.*;

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

		STAIRS = new WoodBlockVariant.Builder("stairs")
				.setFactory((v, t) -> new BlockWoodStairs(PLANKS, v, t))
				.setFireInfo(5, 20)
				.build();

		SLAB_DOUBLE = new WoodBlockVariant.Builder("slab_double")
				.setFactory((v, t) -> new BlockWoodSlab.Double(PLANKS, v, t))
				.setFireInfo(5, 20)
				.build();

		SLAB = new WoodBlockVariant.Builder("slab")
				.setFactory((v, t) -> new BlockWoodSlab.Half(PLANKS, SLAB_DOUBLE, v, t))
				.setFireInfo(5, 20)
				.build();

		WALL = new WoodBlockVariant.Builder("wall")
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

		CHEST_TRAP = new WoodBlockVariant.Builder("chest_trap")
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
