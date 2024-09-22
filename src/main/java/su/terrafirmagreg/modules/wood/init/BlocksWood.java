package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodBarrel;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodBookshelf;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodButton;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodChest;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodDoor;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodFence;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodFenceGate;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodFenceGateLog;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodFenceLog;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodLadder;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodLeaves;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodLog;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodLoom;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodPlanks;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodPressurePlate;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSapling;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSlab;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodStairs;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSupport;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodToolRack;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodTrapDoor;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodWall;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodWorkbench;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlocksWood {


  public static WoodBlockVariant LOG;
  public static WoodBlockVariant STRIPPED_LOG;
  public static WoodBlockVariant LEAVES;
  public static WoodBlockVariant SAPLING;
  public static WoodBlockVariant POTTED_SAPLING;
  public static WoodBlockVariant PLANKS;
  public static WoodBlockVariant STAIRS_PLANKS;
  public static WoodBlockVariant SLAB_DOUBLE_PLANKS;
  public static WoodBlockVariant SLAB_PLANKS;
  public static WoodBlockVariant WALL_PLANKS;
  public static WoodBlockVariant BOOKSHELF;
  public static WoodBlockVariant DOOR;
  public static WoodBlockVariant TRAPDOOR;
  public static WoodBlockVariant FENCE;
  public static WoodBlockVariant FENCE_LOG;
  public static WoodBlockVariant FENCE_GATE;
  public static WoodBlockVariant FENCE_GATE_LOG;
  public static WoodBlockVariant BUTTON;
  public static WoodBlockVariant PRESSURE_PLATE;
  public static WoodBlockVariant TOOL_RACK;
  public static WoodBlockVariant SUPPORT;
  public static WoodBlockVariant WORKBENCH;
  public static WoodBlockVariant CHEST_TRAPPED;
  public static WoodBlockVariant CHEST;
  public static WoodBlockVariant LOOM;
  public static WoodBlockVariant BARREL;
  public static WoodBlockVariant LADDER;
  public static WoodBlockVariant CHOPPER;

  public static void onRegister(RegistryManager registry) {

    LOG = WoodBlockVariant.builder("log")
            .factory(BlockWoodLog::new)
            .build(registry);

    LEAVES = WoodBlockVariant.builder("leaves")
            .factory(BlockWoodLeaves::new)
            .build(registry);

    SAPLING = WoodBlockVariant.builder("sapling")
            .factory(BlockWoodSapling::new)
            .build(registry);

    PLANKS = WoodBlockVariant.builder("planks")
            .factory(BlockWoodPlanks::new)
            .fireInfo(5, 20)
            .build(registry);

    STAIRS_PLANKS = WoodBlockVariant.builder("stairs/planks")
            .factory((v, t) -> new BlockWoodStairs(PLANKS, v, t))
            .fireInfo(5, 20)
            .build(registry);

    SLAB_DOUBLE_PLANKS = WoodBlockVariant.builder("slab_double/planks")
            .factory((v, t) -> new BlockWoodSlab.Double(PLANKS.get(t), v, t))
            .fireInfo(5, 20)
            .build(registry);

    SLAB_PLANKS = WoodBlockVariant.builder("slab/planks")
            .factory((v, t) -> new BlockWoodSlab.Half(PLANKS.get(t), SLAB_DOUBLE_PLANKS.get(t), v, t))
            .fireInfo(5, 20)
            .build(registry);

    WALL_PLANKS = WoodBlockVariant.builder("wall/planks")
            .factory((v, t) -> new BlockWoodWall(PLANKS.get(t), v, t))
            .fireInfo(5, 20)
            .build(registry);

    BOOKSHELF = WoodBlockVariant.builder("bookshelf")
            .factory(BlockWoodBookshelf::new)
            .fireInfo(30, 20)
            .build(registry);

    DOOR = WoodBlockVariant.builder("door")
            .factory(BlockWoodDoor::new)
            .fireInfo(5, 20)
            .build(registry);

    TRAPDOOR = WoodBlockVariant.builder("trapdoor")
            .factory(BlockWoodTrapDoor::new)
            .fireInfo(5, 20)
            .build(registry);

    FENCE = WoodBlockVariant.builder("fence")
            .factory(BlockWoodFence::new)
            .fireInfo(5, 20)
            .build(registry);

    FENCE_LOG = WoodBlockVariant.builder("fence_log")
            .factory(BlockWoodFenceLog::new)
            .fireInfo(5, 20)
            .build(registry);

    FENCE_GATE = WoodBlockVariant.builder("fence_gate")
            .factory(BlockWoodFenceGate::new)
            .fireInfo(5, 20)
            .build(registry);

    FENCE_GATE_LOG = WoodBlockVariant.builder("fence_gate_log")
            .factory(BlockWoodFenceGateLog::new)
            .fireInfo(5, 20)
            .build(registry);

    BUTTON = WoodBlockVariant.builder("button")
            .factory(BlockWoodButton::new)
            .fireInfo(5, 20)
            .build(registry);

    PRESSURE_PLATE = WoodBlockVariant.builder("pressure_plate")
            .factory(BlockWoodPressurePlate::new)
            .fireInfo(5, 20)
            .build(registry);

    TOOL_RACK = WoodBlockVariant.builder("tool_rack")
            .factory(BlockWoodToolRack::new)
            .fireInfo(5, 20)
            .build(registry);

    SUPPORT = WoodBlockVariant.builder("support")
            .factory(BlockWoodSupport::new)
            .fireInfo(5, 20)
            .build(registry);

    WORKBENCH = WoodBlockVariant.builder("workbench")
            .factory(BlockWoodWorkbench::new)
            .fireInfo(5, 20)
            .build(registry);

    CHEST_TRAPPED = WoodBlockVariant.builder("chest_trapped")
            .factory(BlockWoodChest::new)
            .fireInfo(5, 20)
            .build(registry);

    CHEST = WoodBlockVariant.builder("chest")
            .factory(BlockWoodChest::new)
            .fireInfo(5, 20)
            .build(registry);

    LOOM = WoodBlockVariant.builder("loom")
            .factory(BlockWoodLoom::new)
            .fireInfo(5, 20)
            .build(registry);

    BARREL = WoodBlockVariant.builder("barrel")
            .factory(BlockWoodBarrel::new)
            .fireInfo(5, 20)
            .build(registry);

    LADDER = WoodBlockVariant.builder("ladder")
            .factory(BlockWoodLadder::new)
            .fireInfo(5, 20)
            .build(registry);
//
//            CHOPPER = WoodBlockVariant.builder("chopper")
//                    .setFactory(BlockWoodChopper::new)
//                    .build(registry);

    //for (var block : TREE_BLOCKS) registry.registerBlock(block);

    //registry.registerBlocks(LeavesPaging.getLeavesMapForModId(Tags.MOD_ID).values().toArray(new Block[0]));

  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(RegistryManager registry) {
    ModelResourceLocation blockModelLocation = new ModelResourceLocation("tfg:leaves");
    ModelResourceLocation itemModelLocation = new ModelResourceLocation("tfg:leaves", "inventory");

    //        registry.registerClientModelRegistrationStrategy(() -> {
    //            //for (var tree : WoodTreeVariant.getTreeTypes()) ModelHelperTFC.regModel(tree);
    //        });

  }

  public static void onPostInitialization() {
    //        for (var type : WoodType.getTypes()) {
    //            FuelManager.addFuel(new Fuel(IIngredient.of(new ItemStack(StorageWood.get(LOG, type))), type.getBurnTicks(), type.getBurnTemp()));
    //        }
  }
}
