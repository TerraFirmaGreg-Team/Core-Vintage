package su.terrafirmagreg.modules.soil.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilCoarseDirt;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilDirt;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilDryGrass;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilFarmland;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilGrass;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilGrassPath;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilMud;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilMudBricks;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilMudSlab;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilMudStairs;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilMudWall;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilMycelium;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilPeat;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilPeatGrass;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilPodzol;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilRootedDirt;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilSparseGrass;

import java.util.Map;

public class BlocksSoil {

  public static Map<SoilType, BlockSoilGrass> GRASS;
  public static Map<SoilType, BlockSoilDryGrass> DRY_GRASS;
  public static Map<SoilType, BlockSoilSparseGrass> SPARSE_GRASS;
  public static Map<SoilType, BlockSoilPodzol> PODZOL;
  public static Map<SoilType, BlockSoilMycelium> MYCELIUM;
  public static Map<SoilType, BlockSoilCoarseDirt> COARSE_DIRT;
  public static Map<SoilType, BlockSoilRootedDirt> ROOTED_DIRT;
  public static Map<SoilType, BlockSoilDirt> DIRT;
  public static Map<SoilType, BlockSoilFarmland> FARMLAND;
  public static Map<SoilType, BlockSoilGrassPath> GRASS_PATH;
  public static Map<SoilType, BlockSoilMud> MUD;
  public static Map<SoilType, BlockSoilMudBricks> MUD_BRICKS;
  public static Map<SoilType, BlockSoilMudStairs> MUD_BRICKS_STAIRS;
  public static Map<SoilType, BlockSoilMudSlab.Double> MUD_BRICKS_SLAB_DOUBLE;
  public static Map<SoilType, BlockSoilMudSlab.Half> MUD_BRICKS_SLAB;
  public static Map<SoilType, BlockSoilMudWall> MUD_BRICKS_WALL;
  public static Map<SoilType, BlockSoilGrass> DRYING_BRICKS;

  public static BlockSoilPeatGrass PEAT_GRASS;
  public static BlockSoilPeat PEAT;

  public static void onRegister(IRegistryRegistrar registry) {

    GRASS = registry.addBlock(SoilType.getTypes(), BlockSoilGrass::new);
    DRY_GRASS = registry.addBlock(SoilType.getTypes(), BlockSoilDryGrass::new);
    SPARSE_GRASS = registry.addBlock(SoilType.getTypes(), BlockSoilSparseGrass::new);
    PODZOL = registry.addBlock(SoilType.getTypes(), BlockSoilPodzol::new);
    MYCELIUM = registry.addBlock(SoilType.getTypes(), BlockSoilMycelium::new);
    COARSE_DIRT = registry.addBlock(SoilType.getTypes(), BlockSoilCoarseDirt::new);
    ROOTED_DIRT = registry.addBlock(SoilType.getTypes(), BlockSoilRootedDirt::new);
    DIRT = registry.addBlock(SoilType.getTypes(), BlockSoilDirt::new);
    FARMLAND = registry.addBlock(SoilType.getTypes(), BlockSoilFarmland::new);
    GRASS_PATH = registry.addBlock(SoilType.getTypes(), BlockSoilGrassPath::new);
    MUD = registry.addBlock(SoilType.getTypes(), BlockSoilMud::new);
    MUD_BRICKS = registry.addBlock(SoilType.getTypes(), BlockSoilMudBricks::new);
//    MUD_BRICKS_STAIRS = registry.addBlock(SoilType.getTypes(), BlockSoilMudStairs::new);
//    MUD_BRICKS_SLAB_DOUBLE = registry.addBlock(SoilType.getTypes(), BlockSoilMudSlab.Double::new);
//    MUD_BRICKS_SLAB = registry.addBlock(SoilType.getTypes(), BlockSoilMudSlab.Half::new);
//    MUD_BRICKS_WALL = registry.addBlock(SoilType.getTypes(), BlockSoilMudWall::new);
//    DRYING_BRICKS = registry.addBlock(SoilType.getTypes(), BlockSoilGrass::new);

    PEAT_GRASS = registry.addBlock(new BlockSoilPeatGrass());
    PEAT = registry.addBlock(new BlockSoilPeat());
  }
}
