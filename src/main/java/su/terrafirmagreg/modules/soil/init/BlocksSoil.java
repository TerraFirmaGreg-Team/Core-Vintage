package su.terrafirmagreg.modules.soil.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilDirt;
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

import net.minecraft.block.Block;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_ONLY_SOIL;

public final class BlocksSoil {

  public static final Map<Pair<SoilBlockVariant, SoilType>, Block> SOIL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

  public static SoilBlockVariant GRASS;
  public static SoilBlockVariant DRY_GRASS;
  public static SoilBlockVariant SPARSE_GRASS;

  public static SoilBlockVariant PODZOL;
  public static SoilBlockVariant MYCELIUM;

  public static SoilBlockVariant COARSE_DIRT;
  public static SoilBlockVariant ROOTED_DIRT;
  public static SoilBlockVariant DIRT;

  public static SoilBlockVariant FARMLAND;
  public static SoilBlockVariant GRASS_PATH;

  public static SoilBlockVariant MUD;

  public static SoilBlockVariant MUD_BRICKS;
  public static SoilBlockVariant MUD_BRICKS_STAIRS;
  public static SoilBlockVariant MUD_BRICKS_SLAB_DOUBLE;
  public static SoilBlockVariant MUD_BRICKS_SLAB;
  public static SoilBlockVariant MUD_BRICKS_WALL;

  public static SoilBlockVariant DRYING_BRICKS;

  public static BlockSoilPeatGrass PEAT_GRASS;
  public static BlockSoilPeat PEAT;

  public static void onRegister(RegistryManager registry) {

    GRASS = SoilBlockVariant.builder("grass")
            .factory(BlockSoilGrass::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    DRY_GRASS = SoilBlockVariant.builder("dry_grass")
            .factory(BlockSoilGrass::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    SPARSE_GRASS = SoilBlockVariant.builder("sparse_grass")
            .factory(BlockSoilGrass::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    PODZOL = SoilBlockVariant.builder("podzol")
            .factory(BlockSoilPodzol::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    MYCELIUM = SoilBlockVariant.builder("mycelium")
            .factory(BlockSoilMycelium::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    DIRT = SoilBlockVariant.builder("dirt")
            .factory(BlockSoilDirt::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    COARSE_DIRT = SoilBlockVariant.builder("coarse_dirt")
            .factory(BlockSoilDirt::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    ROOTED_DIRT = SoilBlockVariant.builder("rooted_dirt")
            .factory(BlockSoilRootedDirt::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    FARMLAND = SoilBlockVariant.builder("farmland")
            .factory(BlockSoilFarmland::new)
            .fallingSpecification(VERTICAL_ONLY_SOIL)
            .build(registry);

    GRASS_PATH = SoilBlockVariant.builder("grass_path")
            .factory(BlockSoilGrassPath::new)
            .fallingSpecification(VERTICAL_ONLY_SOIL)
            .build(registry);

    MUD = SoilBlockVariant.builder("mud")
            .factory(BlockSoilMud::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    MUD_BRICKS = SoilBlockVariant.builder("mud_bricks")
            .factory(BlockSoilMudBricks::new)
            .build(registry);

    MUD_BRICKS_STAIRS = SoilBlockVariant.builder("mud_bricks/stairs")
            .factory((v, t) -> new BlockSoilMudStairs(MUD_BRICKS.get(t), v, t))
            .build(registry);

    MUD_BRICKS_SLAB_DOUBLE = SoilBlockVariant.builder("mud_bricks/slab_double")
            .factory((v, t) -> new BlockSoilMudSlab.Double(MUD_BRICKS.get(t), v, t))
            .build(registry);

    MUD_BRICKS_SLAB = SoilBlockVariant.builder("mud_bricks/slab")
            .factory((v, t) -> new BlockSoilMudSlab.Half(MUD_BRICKS.get(t), MUD_BRICKS_SLAB_DOUBLE.get(t), v, t))
            .build(registry);

    MUD_BRICKS_WALL = SoilBlockVariant.builder("mud_bricks/wall")
            .factory((v, t) -> new BlockSoilMudWall(MUD_BRICKS.get(t), v, t))
            .build(registry);

    PEAT_GRASS = registry.block(new BlockSoilPeatGrass());
    PEAT = registry.block(new BlockSoilPeat());

    registry.blocks(SOIL_BLOCKS.values());

  }
}
