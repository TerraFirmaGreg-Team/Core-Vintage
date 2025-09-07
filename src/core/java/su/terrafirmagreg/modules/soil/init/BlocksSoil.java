package su.terrafirmagreg.modules.soil.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilCoarseDirt;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilDirt;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilDryGrass;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilFarmland;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilGrass;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilGrassPath;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilMud;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilMudBricks;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilMycelium;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilPeat;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilPeatGrass;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilPodzol;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilRootedDirt;
import su.terrafirmagreg.modules.soil.content.block.BlockSoilSparseGrass;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

import java.util.Map;

public class BlocksSoil {

  public static BlockSoilPeatGrass PEAT_GRASS;
  public static BlockSoilPeat PEAT;

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
  public static Map<SoilType, BlockSoilGrass> DRYING_BRICKS;


  public static void onRegister(IContentRegistrar registrar) {

    PEAT_GRASS = registrar.addBlock(new BlockSoilPeatGrass());
    PEAT = registrar.addBlock(new BlockSoilPeat());

    GRASS = registrar.addBlock(BlockSoilGrass::new, SoilType.getTypes());
    DRY_GRASS = registrar.addBlock(BlockSoilDryGrass::new, SoilType.getTypes());
    SPARSE_GRASS = registrar.addBlock(BlockSoilSparseGrass::new, SoilType.getTypes());
    PODZOL = registrar.addBlock(BlockSoilPodzol::new, SoilType.getTypes());
    MYCELIUM = registrar.addBlock(BlockSoilMycelium::new, SoilType.getTypes());
    COARSE_DIRT = registrar.addBlock(BlockSoilCoarseDirt::new, SoilType.getTypes());
    ROOTED_DIRT = registrar.addBlock(BlockSoilRootedDirt::new, SoilType.getTypes());
    DIRT = registrar.addBlock(BlockSoilDirt::new, SoilType.getTypes());
    FARMLAND = registrar.addBlock(BlockSoilFarmland::new, SoilType.getTypes());
    GRASS_PATH = registrar.addBlock(BlockSoilGrassPath::new, SoilType.getTypes());
    MUD = registrar.addBlock(BlockSoilMud::new, SoilType.getTypes());
    MUD_BRICKS = registrar.addBlock(BlockSoilMudBricks::new, SoilType.getTypes());
  }
}
