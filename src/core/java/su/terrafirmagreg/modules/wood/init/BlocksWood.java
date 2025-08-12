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

    LOG = registrar.addBlock(BlockWoodLog::new, WoodType.getTypes());
//    STRIPPED_LOG = registrar.addBlock(WoodType.getTypes(), BlockWoodLog::new);
    LEAVES = registrar.addBlock(BlockWoodLeaves::new, WoodType.getTypes());
    SAPLING = registrar.addBlock(BlockWoodSapling::new, WoodType.getTypes());
//    POTTED_SAPLING = registrar.addBlock(WoodType.getTypes(), BlockWoodSapling::new);
    PLANKS = registrar.addBlock(BlockWoodPlanks::new, WoodType.getTypes());
    STAIRS_PLANKS = registrar.addBlock(BlockWoodStairs::new, WoodType.getTypes());
    SLAB_DOUBLE_PLANKS = registrar.addBlock(BlockWoodSlab::new, WoodType.getTypes());
    SLAB_PLANKS = registrar.addBlock(BlockWoodSlab.Half::new, WoodType.getTypes());
    WALL_PLANKS = registrar.addBlock(BlockWoodWall::new, WoodType.getTypes());
    BOOKSHELF = registrar.addBlock(BlockWoodBookshelf::new, WoodType.getTypes());
    DOOR = registrar.addBlock(BlockWoodDoor::new, WoodType.getTypes());
    TRAPDOOR = registrar.addBlock(BlockWoodTrapDoor::new, WoodType.getTypes());
    FENCE = registrar.addBlock(BlockWoodFence::new, WoodType.getTypes());
    FENCE_LOG = registrar.addBlock(BlockWoodFenceLog::new, WoodType.getTypes());
    FENCE_GATE = registrar.addBlock(BlockWoodFenceGate::new, WoodType.getTypes());
    FENCE_GATE_LOG = registrar.addBlock(BlockWoodFenceGateLog::new, WoodType.getTypes());
    BUTTON = registrar.addBlock(BlockWoodButton::new, WoodType.getTypes());
    PRESSURE_PLATE = registrar.addBlock(BlockWoodPressurePlate::new, WoodType.getTypes());
    TOOL_RACK = registrar.addBlock(BlockWoodToolRack::new, WoodType.getTypes());
    SUPPORT = registrar.addBlock(BlockWoodSupport::new, WoodType.getTypes());
    WORKBENCH = registrar.addBlock(BlockWoodWorkbench::new, WoodType.getTypes());
    CHEST_TRAPPED = registrar.addBlock(BlockWoodChestTrap::new, WoodType.getTypes());
    CHEST = registrar.addBlock(BlockWoodChest::new, WoodType.getTypes());
    LOOM = registrar.addBlock(BlockWoodLoom::new, WoodType.getTypes());
    BARREL = registrar.addBlock(BlockWoodBarrel::new, WoodType.getTypes());
    LADDER = registrar.addBlock(BlockWoodLadder::new, WoodType.getTypes());
    // CHOPPER = registrar.addBlock(WoodType.getTypes(), BlockWoodLog::new);
  }
}
