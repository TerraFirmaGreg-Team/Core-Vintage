package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.object.block.BlockAlabasterBricks;
import su.terrafirmagreg.modules.rock.object.block.BlockAlabasterRaw;
import su.terrafirmagreg.modules.rock.object.block.BlockAlabasterSmooth;
import su.terrafirmagreg.modules.rock.object.block.BlockRockAnvil;
import su.terrafirmagreg.modules.rock.object.block.BlockRockBricks;
import su.terrafirmagreg.modules.rock.object.block.BlockRockButton;
import su.terrafirmagreg.modules.rock.object.block.BlockRockCobble;
import su.terrafirmagreg.modules.rock.object.block.BlockRockDecorative;
import su.terrafirmagreg.modules.rock.object.block.BlockRockGravel;
import su.terrafirmagreg.modules.rock.object.block.BlockRockMagma;
import su.terrafirmagreg.modules.rock.object.block.BlockRockPressurePlate;
import su.terrafirmagreg.modules.rock.object.block.BlockRockRaw;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSand;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSlab;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSmooth;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSpeleothem;
import su.terrafirmagreg.modules.rock.object.block.BlockRockStairs;
import su.terrafirmagreg.modules.rock.object.block.BlockRockStandGem;
import su.terrafirmagreg.modules.rock.object.block.BlockRockSurface;
import su.terrafirmagreg.modules.rock.object.block.BlockRockWall;

import net.minecraft.item.EnumDyeColor;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.COLLAPSABLE_ROCK;
import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK;
import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_ONLY;

public final class BlocksRock {

  public static final Map<Pair<RockBlockVariant, EnumDyeColor>, BlockRockDecorative> ALABASTER_COLOR_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

  public static RockBlockVariant COBBLE;
  public static RockBlockVariant COBBLE_STAIRS;
  public static RockBlockVariant COBBLE_SLAB_DOUBLE;
  public static RockBlockVariant COBBLE_SLAB;
  public static RockBlockVariant COBBLE_WALL;

  public static RockBlockVariant RAW;
  public static RockBlockVariant RAW_STAIRS;
  public static RockBlockVariant RAW_SLAB_DOUBLE;
  public static RockBlockVariant RAW_SLAB;
  public static RockBlockVariant RAW_WALL;

  public static RockBlockVariant BRICKS;
  public static RockBlockVariant BRICKS_STAIRS;
  public static RockBlockVariant BRICKS_SLAB_DOUBLE;
  public static RockBlockVariant BRICKS_SLAB;
  public static RockBlockVariant BRICKS_WALL;

  public static RockBlockVariant SMOOTH;
  public static RockBlockVariant SMOOTH_STAIRS;
  public static RockBlockVariant SMOOTH_SLAB_DOUBLE;
  public static RockBlockVariant SMOOTH_SLAB;
  public static RockBlockVariant SMOOTH_WALL;

  public static RockBlockVariant BRICKS_CRACKED;
  public static RockBlockVariant CHISELED;

  public static RockBlockVariant GRAVEL;
  public static RockBlockVariant SAND;
  public static RockBlockVariant SURFACE;
  public static RockBlockVariant SPELEOTHEM;
  public static RockBlockVariant BUTTON;
  public static RockBlockVariant PRESSURE_PLATE;
  public static RockBlockVariant ANVIL;
  public static RockBlockVariant MAGMA;
  public static RockBlockVariant STAND_GEM;

  public static BlockAlabasterBricks ALABASTER_BRICKS;
  public static BlockAlabasterSmooth ALABASTER_SMOOTH;
  public static BlockAlabasterRaw ALABASTER_RAW;


  public static void onRegister(RegistryManager registry) {

    COBBLE = RockBlockVariant
            .builder("cobble")
            .baseHardness(6f)
            .factory(BlockRockCobble::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL_ROCK)
            .build(registry);

    COBBLE_STAIRS = RockBlockVariant
            .builder("cobble/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(COBBLE.get(t), v, t))
            .build(registry);

    COBBLE_SLAB_DOUBLE = RockBlockVariant
            .builder("cobble/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(COBBLE.get(t), v, t))
            .build(registry);

    COBBLE_SLAB = RockBlockVariant
            .builder("cobble/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(COBBLE.get(t), COBBLE_SLAB_DOUBLE.get(t), v, t))
            .build(registry);

    COBBLE_WALL = RockBlockVariant
            .builder("cobble/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(COBBLE.get(t), v, t))
            .build(registry);

    RAW = RockBlockVariant
            .builder("raw")
            .baseHardness(6f)
            .factory(BlockRockRaw::new)
            .isStoneType()
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build(registry);

    RAW_STAIRS = RockBlockVariant
            .builder("raw/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(RAW.get(t), v, t))
            .build(registry);

    RAW_SLAB_DOUBLE = RockBlockVariant
            .builder("raw/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(RAW.get(t), v, t))
            .build(registry);

    RAW_SLAB = RockBlockVariant
            .builder("raw/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(RAW.get(t), RAW_SLAB_DOUBLE.get(t), v, t))
            .build(registry);

    RAW_WALL = RockBlockVariant
            .builder("raw/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(RAW.get(t), v, t))
            .build(registry);

    BRICKS = RockBlockVariant
            .builder("bricks")
            .baseHardness(6f)
            .factory(BlockRockBricks::new)
            .build(registry);

    BRICKS_STAIRS = RockBlockVariant
            .builder("bricks/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(BRICKS.get(t), v, t))
            .build(registry);

    BRICKS_SLAB_DOUBLE = RockBlockVariant
            .builder("bricks/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(BRICKS.get(t), v, t))
            .build(registry);

    BRICKS_SLAB = RockBlockVariant
            .builder("bricks/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(BRICKS.get(t), BRICKS_SLAB_DOUBLE.get(t), v, t))
            .build(registry);

    BRICKS_WALL = RockBlockVariant
            .builder("bricks/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(BRICKS.get(t), v, t))
            .build(registry);

    SMOOTH = RockBlockVariant
            .builder("smooth")
            .baseHardness(6f)
            .factory(BlockRockSmooth::new)
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build(registry);

    SMOOTH_STAIRS = RockBlockVariant
            .builder("smooth/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(SMOOTH.get(t), v, t))
            .build(registry);

    SMOOTH_SLAB_DOUBLE = RockBlockVariant
            .builder("smooth/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(SMOOTH.get(t), v, t))
            .build(registry);

    SMOOTH_SLAB = RockBlockVariant
            .builder("smooth/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(SMOOTH.get(t), SMOOTH_SLAB_DOUBLE.get(t), v, t))
            .build(registry);

    SMOOTH_WALL = RockBlockVariant
            .builder("smooth/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(SMOOTH.get(t), v, t))
            .build(registry);

    BRICKS_CRACKED = RockBlockVariant
            .builder("bricks_cracked")
            .baseHardness(6f)
            .factory(BlockRockBricks::new)
            .build(registry);

    CHISELED = RockBlockVariant
            .builder("chiseled")
            .baseHardness(6f)
            .factory(BlockRockBricks::new)
            .build(registry);

    GRAVEL = RockBlockVariant
            .builder("gravel")
            .baseHardness(6f)
            .factory(BlockRockGravel::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    SAND = RockBlockVariant
            .builder("sand")
            .baseHardness(6f)
            .factory(BlockRockSand::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build(registry);

    SURFACE = RockBlockVariant
            .builder("surface")
            .baseHardness(6f)
            .factory(BlockRockSurface::new)
            .fallingSpecification(VERTICAL_ONLY)
            .build(registry);

    SPELEOTHEM = RockBlockVariant
            .builder("speleothem")
            .baseHardness(6f)
            .factory(BlockRockSpeleothem::new)
            .build(registry);

    BUTTON = RockBlockVariant
            .builder("button")
            .baseHardness(6f)
            .factory(BlockRockButton::new)
            .build(registry);

    PRESSURE_PLATE = RockBlockVariant
            .builder("pressure_plate")
            .baseHardness(6f)
            .factory(BlockRockPressurePlate::new)
            .build(registry);

    ANVIL = RockBlockVariant
            .builder("anvil")
            .baseHardness(6f)
            .factory(BlockRockAnvil::new)
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build(registry);

    MAGMA = RockBlockVariant
            .builder("magma")
            .baseHardness(6f)
            .factory(BlockRockMagma::new)
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build(registry);

    STAND_GEM = RockBlockVariant
            .builder("stand_gem")
            .baseHardness(1f)
            .factory(BlockRockStandGem::new)
            .build(registry);

    ALABASTER_BRICKS = registry.block(new BlockAlabasterBricks());
    ALABASTER_SMOOTH = registry.block(new BlockAlabasterSmooth());
    ALABASTER_RAW = registry.block(new BlockAlabasterRaw());

    registry.blocks(ALABASTER_COLOR_BLOCKS.values());
  }

}
