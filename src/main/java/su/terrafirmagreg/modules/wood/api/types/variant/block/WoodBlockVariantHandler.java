package su.terrafirmagreg.modules.wood.api.types.variant.block;


import su.terrafirmagreg.modules.wood.objects.blocks.*;

public class WoodBlockVariantHandler {

	public static void init() {
		WoodBlockVariants.LOG = new WoodBlockVariant
				.Builder("log")
				.setFactory(BlockWoodLog::new)
				.build();

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
				.build();

		WoodBlockVariants.BOOKSHELF = new WoodBlockVariant
				.Builder("bookshelf")
				.setFactory(BlockWoodBookshelf::new)
				.build();

		WoodBlockVariants.DOOR = new WoodBlockVariant
				.Builder("door")
				.setFactory(BlockWoodDoor::new)
				.build();

		WoodBlockVariants.TRAPDOOR = new WoodBlockVariant
				.Builder("trapdoor")
				.setFactory(BlockWoodTrapDoor::new)
				.build();

		WoodBlockVariants.FENCE = new WoodBlockVariant
				.Builder("fence")
				.setFactory(BlockWoodFence::new)
				.build();

		WoodBlockVariants.FENCE_LOG = new WoodBlockVariant
				.Builder("fence_log")
				.setFactory(BlockWoodFenceLog::new)
				.build();

		WoodBlockVariants.FENCE_GATE = new WoodBlockVariant
				.Builder("fence_gate")
				.setFactory(BlockWoodFenceGate::new)
				.build();

		WoodBlockVariants.FENCE_GATE_LOG = new WoodBlockVariant
				.Builder("fence_gate_log")
				.setFactory(BlockWoodFenceGateLog::new)
				.build();

		WoodBlockVariants.BUTTON = new WoodBlockVariant
				.Builder("button")
				.setFactory(BlockWoodButton::new)
				.build();

		WoodBlockVariants.PRESSURE_PLATE = new WoodBlockVariant
				.Builder("pressure_plate")
				.setFactory(BlockWoodPressurePlate::new)
				.build();

		WoodBlockVariants.SLAB_DOUBLE = new WoodBlockVariant
				.Builder("slab_double")
				.setFactory(BlockWoodSlab.Double::new)
				.build();

		WoodBlockVariants.SLAB = new WoodBlockVariant
				.Builder("slab")
				.setFactory(BlockWoodSlab.Half::new)
				.build();

		WoodBlockVariants.STAIRS = new WoodBlockVariant
				.Builder("stairs")
				.setFactory(BlockWoodStairs::new)
				.build();

		WoodBlockVariants.TOOL_RACK = new WoodBlockVariant
				.Builder("tool_rack")
				.setFactory(BlockWoodToolRack::new)
				.build();

		WoodBlockVariants.SUPPORT = new WoodBlockVariant
				.Builder("support")
				.setFactory(BlockWoodSupport::new)
				.build();

		WoodBlockVariants.WORKBENCH = new WoodBlockVariant
				.Builder("workbench")
				.setFactory(BlockWoodWorkbench::new)
				.build();

		WoodBlockVariants.CHEST_TRAP = new WoodBlockVariant
				.Builder("chest_trap")
				.setFactory(BlockWoodChest::new)
				.build();

		WoodBlockVariants.CHEST = new WoodBlockVariant
				.Builder("chest")
				.setFactory(BlockWoodChest::new)
				.build();

		WoodBlockVariants.LOOM = new WoodBlockVariant
				.Builder("loom")
				.setFactory(BlockWoodLoom::new)
				.build();

		WoodBlockVariants.BARREL = new WoodBlockVariant
				.Builder("barrel")
				.setFactory(BlockWoodBarrel::new)
				.build();

		WoodBlockVariants.LADDER = new WoodBlockVariant
				.Builder("ladder")
				.setFactory(BlockWoodLadder::new)
				.build();

//		WoodBlockVariants.CHOPPER = new WoodBlockVariant
//				.Builder("chopper")
//				.setFactory(BlockWoodChopper::new)
//				.build();
	}
}
