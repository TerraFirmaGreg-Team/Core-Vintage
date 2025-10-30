package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodBarrel;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodBookshelf;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodButton;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodChest;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodChestTrap;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodDoor;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodFence;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodFenceGate;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodFenceGateLog;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodFenceLog;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodLadder;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodLeaves;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodLog;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodLoom;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodPlanks;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodPressurePlate;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodSapling;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodSupport;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodToolRack;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodTrapDoor;
import su.terrafirmagreg.modules.wood.content.block.BlockWoodWorkbench;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import java.util.Map;

public class BlocksWood {

  public static Map<WoodType, BlockWoodLog> LOG;
  public static Map<WoodType, BlockWoodLog> STRIPPED_LOG;
  public static Map<WoodType, BlockWoodLeaves> LEAVES;
  public static Map<WoodType, BlockWoodSapling> SAPLING;
  public static Map<WoodType, BlockWoodSapling> POTTED_SAPLING;
  public static Map<WoodType, BlockWoodPlanks> PLANKS;
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
  public static Map<WoodType, BlockWoodLog> CHOPPER;

  public static void onRegister(IContentRegistrar registrar) {

    LOG = registrar.addBlock("log", BlockWoodLog::new, WoodType.getTypes());
//    STRIPPED_LOG = registrar.addBlock(WoodType.getTypes(), BlockWoodLog::new);
    LEAVES = registrar.addBlock("leaves", BlockWoodLeaves::new, WoodType.getTypes());
    SAPLING = registrar.addBlock("sapling", BlockWoodSapling::new, WoodType.getTypes());
//    POTTED_SAPLING = registrar.addBlock(WoodType.getTypes(), BlockWoodSapling::new);
    PLANKS = registrar.addBlock("planks", BlockWoodPlanks::new, WoodType.getTypes());
    BOOKSHELF = registrar.addBlock("bookshelf", BlockWoodBookshelf::new, WoodType.getTypes());
    DOOR = registrar.addBlock("door", BlockWoodDoor::new, WoodType.getTypes());
    TRAPDOOR = registrar.addBlock("trapdoor", BlockWoodTrapDoor::new, WoodType.getTypes());
    FENCE = registrar.addBlock("fence", BlockWoodFence::new, WoodType.getTypes());
    FENCE_LOG = registrar.addBlock("fence_log", BlockWoodFenceLog::new, WoodType.getTypes());
    FENCE_GATE = registrar.addBlock("fence_gate", BlockWoodFenceGate::new, WoodType.getTypes());
    FENCE_GATE_LOG = registrar.addBlock("fence_gate_log", BlockWoodFenceGateLog::new, WoodType.getTypes());
    BUTTON = registrar.addBlock("button", BlockWoodButton::new, WoodType.getTypes());
    PRESSURE_PLATE = registrar.addBlock("pressure_plate", BlockWoodPressurePlate::new, WoodType.getTypes());
    TOOL_RACK = registrar.addBlock("tool_rack", BlockWoodToolRack::new, WoodType.getTypes());
    SUPPORT = registrar.addBlock("support", BlockWoodSupport::new, WoodType.getTypes());
    WORKBENCH = registrar.addBlock("workbench", BlockWoodWorkbench::new, WoodType.getTypes());
    CHEST_TRAPPED = registrar.addBlock("chest_trapped", BlockWoodChestTrap::new, WoodType.getTypes());
    CHEST = registrar.addBlock("chest", BlockWoodChest::new, WoodType.getTypes());
    LOOM = registrar.addBlock("loom", BlockWoodLoom::new, WoodType.getTypes());
    BARREL = registrar.addBlock("barrel", BlockWoodBarrel::new, WoodType.getTypes());
    LADDER = registrar.addBlock("ladder", BlockWoodLadder::new, WoodType.getTypes());
    // CHOPPER = registrar.addBlock(WoodType.getTypes(), BlockWoodLog::new);
  }
}
