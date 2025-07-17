package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilGrass;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodBarrel;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodBookshelf;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodButton;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodChest;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodChestTrap;
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

import java.util.Map;

public class BlocksWood {

  public static Map<WoodType, BlockWoodLog> LOG;
  public static Map<WoodType, BlockWoodLog> STRIPPED_LOG;
  public static Map<WoodType, BlockWoodLeaves> LEAVES;
  public static Map<WoodType, BlockWoodSapling> SAPLING;
  public static Map<WoodType, BlockWoodSapling> POTTED_SAPLING;
  public static Map<WoodType, BlockWoodPlanks> PLANKS;
  public static Map<WoodType, BlockWoodStairs> STAIRS_PLANKS;
  public static Map<WoodType, BlockWoodSlab> SLAB_DOUBLE_PLANKS;
  public static Map<WoodType, BlockWoodSlab.Half> SLAB_PLANKS;
  public static Map<WoodType, BlockWoodWall> WALL_PLANKS;
  public static Map<WoodType, BlockWoodBookshelf> BOOKSHELF;
  public static Map<WoodType, BlockWoodDoor> DOOR;
  public static Map<WoodType, BlockWoodTrapDoor> TRAPDOOR;
  public static Map<WoodType, BlockWoodFence> FENCE;
  public static Map<WoodType, BlockWoodFenceLog> FENCE_LOG;
  public static Map<WoodType, BlockWoodFenceGate> FENCE_GATE;
  public static Map<WoodType, BlockWoodFenceGateLog> FENCE_GATE_LOG;
  public static Map<WoodType, BlockWoodButton> BUTTON;
  public static Map<WoodType, BlockWoodPressurePlate> PRESSURE_PLATE;
  public static Map<WoodType, BlockWoodToolRack> TOOL_RACK;
  public static Map<WoodType, BlockWoodSupport> SUPPORT;
  public static Map<WoodType, BlockWoodWorkbench> WORKBENCH;
  public static Map<WoodType, BlockWoodChestTrap> CHEST_TRAPPED;
  public static Map<WoodType, BlockWoodChest> CHEST;
  public static Map<WoodType, BlockWoodLoom> LOOM;
  public static Map<WoodType, BlockWoodBarrel> BARREL;
  public static Map<WoodType, BlockWoodLadder> LADDER;
  public static Map<WoodType, BlockSoilGrass> CHOPPER;

  public static void onRegister(IRegistryRegistrar registrar) {

    LOG = registrar.addBlock(WoodType.getTypes(), BlockWoodLog::new);
//    STRIPPED_LOG = registrar.addBlock(WoodType.getTypes(), BlockWoodLog::new);
//    LEAVES = registrar.addBlock(WoodType.getTypes(), BlockWoodLeaves::new);
//    SAPLING = registrar.addBlock(WoodType.getTypes(), BlockWoodSapling::new);
//    POTTED_SAPLING = registrar.addBlock(WoodType.getTypes(), BlockWoodSapling::new);
    PLANKS = registrar.addBlock(WoodType.getTypes(), BlockWoodPlanks::new);
    STAIRS_PLANKS = registrar.addBlock(WoodType.getTypes(), BlockWoodStairs::new);
    SLAB_DOUBLE_PLANKS = registrar.addBlock(WoodType.getTypes(), BlockWoodSlab::new);
    SLAB_PLANKS = registrar.addBlock(WoodType.getTypes(), BlockWoodSlab.Half::new);
    WALL_PLANKS = registrar.addBlock(WoodType.getTypes(), BlockWoodWall::new);
    BOOKSHELF = registrar.addBlock(WoodType.getTypes(), BlockWoodBookshelf::new);
    DOOR = registrar.addBlock(WoodType.getTypes(), BlockWoodDoor::new);
    TRAPDOOR = registrar.addBlock(WoodType.getTypes(), BlockWoodTrapDoor::new);
    FENCE = registrar.addBlock(WoodType.getTypes(), BlockWoodFence::new);
    FENCE_LOG = registrar.addBlock(WoodType.getTypes(), BlockWoodFenceLog::new);
    FENCE_GATE = registrar.addBlock(WoodType.getTypes(), BlockWoodFenceGate::new);
    FENCE_GATE_LOG = registrar.addBlock(WoodType.getTypes(), BlockWoodFenceGateLog::new);
    BUTTON = registrar.addBlock(WoodType.getTypes(), BlockWoodButton::new);
    PRESSURE_PLATE = registrar.addBlock(WoodType.getTypes(), BlockWoodPressurePlate::new);
    //TOOL_RACK = registrar.addBlock(WoodType.getTypes(), BlockWoodToolRack::new);
    SUPPORT = registrar.addBlock(WoodType.getTypes(), BlockWoodSupport::new);
    WORKBENCH = registrar.addBlock(WoodType.getTypes(), BlockWoodWorkbench::new);
    //CHEST_TRAPPED = registrar.addBlock(WoodType.getTypes(), BlockWoodChestTrap::new);
    //CHEST = registrar.addBlock(WoodType.getTypes(), BlockWoodChest::new);
    //LOOM = registrar.addBlock(WoodType.getTypes(), BlockWoodLoom::new);
    //BARREL = registrar.addBlock(WoodType.getTypes(), BlockWoodBarrel::new);
    LADDER = registrar.addBlock(WoodType.getTypes(), BlockWoodLadder::new);
    // CHOPPER = registrar.addBlock(WoodType.getTypes(), BlockWoodLog::new);
  }
}
