package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilGrass;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodLog;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSlab;

import java.util.Map;

public class BlocksWood {

  public static Map<WoodType, BlockWoodLog> LOG;
  public static Map<WoodType, BlockSoilGrass> STRIPPED_LOG;
  public static Map<WoodType, BlockSoilGrass> LEAVES;
  public static Map<WoodType, BlockSoilGrass> SAPLING;
  public static Map<WoodType, BlockSoilGrass> POTTED_SAPLING;
  public static Map<WoodType, BlockSoilGrass> PLANKS;
  public static Map<WoodType, BlockWoodSlab.Half> STAIRS_PLANKS;
  public static Map<WoodType, BlockWoodSlab.Double> SLAB_DOUBLE_PLANKS;
  public static Map<WoodType, BlockSoilGrass> SLAB_PLANKS;
  public static Map<WoodType, BlockSoilGrass> WALL_PLANKS;
  public static Map<WoodType, BlockSoilGrass> BOOKSHELF;
  public static Map<WoodType, BlockSoilGrass> DOOR;
  public static Map<WoodType, BlockSoilGrass> TRAPDOOR;
  public static Map<WoodType, BlockSoilGrass> FENCE;
  public static Map<WoodType, BlockSoilGrass> FENCE_LOG;
  public static Map<WoodType, BlockSoilGrass> FENCE_GATE;
  public static Map<WoodType, BlockSoilGrass> FENCE_GATE_LOG;
  public static Map<WoodType, BlockSoilGrass> BUTTON;
  public static Map<WoodType, BlockSoilGrass> PRESSURE_PLATE;
  public static Map<WoodType, BlockSoilGrass> TOOL_RACK;
  public static Map<WoodType, BlockSoilGrass> SUPPORT;
  public static Map<WoodType, BlockSoilGrass> WORKBENCH;
  public static Map<WoodType, BlockSoilGrass> CHEST_TRAPPED;
  public static Map<WoodType, BlockSoilGrass> CHEST;
  public static Map<WoodType, BlockSoilGrass> LOOM;
  public static Map<WoodType, BlockSoilGrass> BARREL;
  public static Map<WoodType, BlockSoilGrass> LADDER;
  public static Map<WoodType, BlockSoilGrass> CHOPPER;

  public static void onRegister(IRegistryRegistrar registry) {

    LOG = registry.addBlock(WoodType.getTypes(), BlockWoodLog::new);
  }
}
