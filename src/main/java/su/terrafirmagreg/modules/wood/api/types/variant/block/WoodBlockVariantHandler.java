package su.terrafirmagreg.modules.wood.api.types.variant.block;


import su.terrafirmagreg.modules.wood.objects.blocks.*;

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

		WoodBlockVariants.PLANKS = new WoodBlockVariant
				.Builder("planks")
				.setFactory(BlockWoodPlanks::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.BOOKSHELF = new WoodBlockVariant
				.Builder("bookshelf")
				.setFactory(BlockWoodBookshelf::new)
				.setFireInfo(30, 20)
				.build();

		WoodBlockVariants.DOOR = new WoodBlockVariant
				.Builder("door")
				.setFactory(BlockWoodDoor::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.TRAPDOOR = new WoodBlockVariant
				.Builder("trapdoor")
				.setFactory(BlockWoodTrapDoor::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.FENCE = new WoodBlockVariant
				.Builder("fence")
				.setFactory(BlockWoodFence::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.FENCE_LOG = new WoodBlockVariant
				.Builder("fence_log")
				.setFactory(BlockWoodFenceLog::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.FENCE_GATE = new WoodBlockVariant
				.Builder("fence_gate")
				.setFactory(BlockWoodFenceGate::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.FENCE_GATE_LOG = new WoodBlockVariant
				.Builder("fence_gate_log")
				.setFactory(BlockWoodFenceGateLog::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.BUTTON = new WoodBlockVariant
				.Builder("button")
				.setFactory(BlockWoodButton::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.PRESSURE_PLATE = new WoodBlockVariant
				.Builder("pressure_plate")
				.setFactory(BlockWoodPressurePlate::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.SLAB_DOUBLE = new WoodBlockVariant
				.Builder("slab_double")
				.setFactory(BlockWoodSlab.Double::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.SLAB = new WoodBlockVariant
				.Builder("slab")
				.setFactory(BlockWoodSlab.Half::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.STAIRS = new WoodBlockVariant
				.Builder("stairs")
				.setFactory(BlockWoodStairs::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.TOOL_RACK = new WoodBlockVariant
				.Builder("tool_rack")
				.setFactory(BlockWoodToolRack::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.SUPPORT = new WoodBlockVariant
				.Builder("support")
				.setFactory(BlockWoodSupport::new)
				.setFireInfo(5, 20)
				.build();

//		WoodBlockVariants.WORKBENCH = new WoodBlockVariant
//				.Builder("workbench")
//				.setFactory(BlockWoodWorkbench::new)
//				.setFireInfo(5, 20)
//				.build();

		WoodBlockVariants.CHEST_TRAP = new WoodBlockVariant
				.Builder("chest_trap")
				.setFactory(BlockWoodChest::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.CHEST = new WoodBlockVariant
				.Builder("chest")
				.setFactory(BlockWoodChest::new)
				.setFireInfo(5, 20)
				.build();

		WoodBlockVariants.LOOM = new WoodBlockVariant
				.Builder("loom")
				.setFactory(BlockWoodLoom::new)
				.setFireInfo(5, 20)
				.build();

//		WoodBlockVariants.BARREL = new WoodBlockVariant
//				.Builder("barrel")
//				.setFactory(BlockWoodBarrel::new)
//				.setFireInfo(5, 20)
//				.build();

		WoodBlockVariants.LADDER = new WoodBlockVariant
				.Builder("ladder")
				.setFactory(BlockWoodLadder::new)
				.setFireInfo(5, 20)
				.build();

//		WoodBlockVariants.CHOPPER = new WoodBlockVariant
//				.Builder("chopper")
//				.setFactory(BlockWoodChopper::new)
//				.build();
	}
}
