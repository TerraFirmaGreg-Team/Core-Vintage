package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
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
import su.terrafirmagreg.modules.rock.object.block.BlockRockMossy;
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

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.COLLAPSABLE_ROCK;
import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK;
import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification.VERTICAL_ONLY;

public final class BlocksRock {

  public static final Map<Pair<RockBlockVariant, RockType>, Block> ROCK_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
  public static final Map<Pair<RockBlockVariant, EnumDyeColor>, BlockRockDecorative> ALABASTER_COLOR_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

  public static RockBlockVariant COBBLE;
  public static RockBlockVariant COBBLE_STAIRS;
  public static RockBlockVariant COBBLE_SLAB_DOUBLE;
  public static RockBlockVariant COBBLE_SLAB;
  public static RockBlockVariant COBBLE_WALL;

  public static RockBlockVariant MOSSY_COBBLE;
  public static RockBlockVariant MOSSY_COBBLE_STAIRS;
  public static RockBlockVariant MOSSY_COBBLE_SLAB_DOUBLE;
  public static RockBlockVariant MOSSY_COBBLE_SLAB;
  public static RockBlockVariant MOSSY_COBBLE_WALL;

  public static RockBlockVariant RAW;
  public static RockBlockVariant RAW_STAIRS;
  public static RockBlockVariant RAW_SLAB_DOUBLE;
  public static RockBlockVariant RAW_SLAB;
  public static RockBlockVariant RAW_WALL;

  public static RockBlockVariant MOSSY_RAW;
  public static RockBlockVariant MOSSY_RAW_STAIRS;
  public static RockBlockVariant MOSSY_RAW_SLAB_DOUBLE;
  public static RockBlockVariant MOSSY_RAW_SLAB;
  public static RockBlockVariant MOSSY_RAW_WALL;

  public static RockBlockVariant BRICKS;
  public static RockBlockVariant BRICKS_STAIRS;
  public static RockBlockVariant BRICKS_SLAB_DOUBLE;
  public static RockBlockVariant BRICKS_SLAB;
  public static RockBlockVariant BRICKS_WALL;

  public static RockBlockVariant BRICKS_MOSSY;
  public static RockBlockVariant BRICKS_MOSSY_STAIRS;
  public static RockBlockVariant BRICKS_MOSSY_SLAB_DOUBLE;
  public static RockBlockVariant BRICKS_MOSSY_SLAB;
  public static RockBlockVariant BRICKS_MOSSY_WALL;

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
            .build();

    COBBLE_STAIRS = RockBlockVariant
            .builder("cobble/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(COBBLE, v, t))
            .build();

    COBBLE_SLAB_DOUBLE = RockBlockVariant
            .builder("cobble/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(COBBLE, v, t))
            .build();

    COBBLE_SLAB = RockBlockVariant
            .builder("cobble/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(COBBLE, COBBLE_SLAB_DOUBLE, v, t))
            .build();

    COBBLE_WALL = RockBlockVariant
            .builder("cobble/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(COBBLE, v, t))
            .build();

    MOSSY_COBBLE = RockBlockVariant
            .builder("cobble_mossy")
            .baseHardness(6f)
            .factory(BlockRockMossy::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL_ROCK)
            .build();

    MOSSY_COBBLE_STAIRS = RockBlockVariant
            .builder("cobble_mossy/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(MOSSY_COBBLE, v, t))
            .build();

    MOSSY_COBBLE_SLAB_DOUBLE = RockBlockVariant
            .builder("cobble_mossy/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(MOSSY_COBBLE, v, t))
            .build();

    MOSSY_COBBLE_SLAB = RockBlockVariant
            .builder("cobble_mossy/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(MOSSY_COBBLE, MOSSY_COBBLE_SLAB_DOUBLE, v, t))
            .build();

    MOSSY_COBBLE_WALL = RockBlockVariant
            .builder("cobble_mossy/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(MOSSY_COBBLE, v, t))
            .build();

    RAW = RockBlockVariant
            .builder("raw")
            .baseHardness(6f)
            .factory(BlockRockRaw::new)
            .isStoneType()
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build();

    RAW_STAIRS = RockBlockVariant
            .builder("raw/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(RAW, v, t))
            .build();

    RAW_SLAB_DOUBLE = RockBlockVariant
            .builder("raw/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(RAW, v, t))
            .build();

    RAW_SLAB = RockBlockVariant
            .builder("raw/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(RAW, RAW_SLAB_DOUBLE, v, t))
            .build();

    RAW_WALL = RockBlockVariant
            .builder("raw/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(RAW, v, t))
            .build();

    MOSSY_RAW = RockBlockVariant
            .builder("raw_mossy")
            .baseHardness(6f)
            .factory(BlockRockRaw::new)
            .isStoneType()
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build();

    MOSSY_RAW_STAIRS = RockBlockVariant
            .builder("raw_mossy/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(MOSSY_RAW, v, t))
            .build();

    MOSSY_RAW_SLAB_DOUBLE = RockBlockVariant
            .builder("raw_mossy/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(MOSSY_RAW, v, t))
            .build();

    MOSSY_RAW_SLAB = RockBlockVariant
            .builder("raw_mossy/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(MOSSY_RAW, MOSSY_RAW_SLAB_DOUBLE, v, t))
            .build();

    MOSSY_RAW_WALL = RockBlockVariant
            .builder("raw_mossy/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(MOSSY_RAW, v, t))
            .build();

    BRICKS = RockBlockVariant
            .builder("bricks")
            .baseHardness(6f)
            .factory(BlockRockBricks::new)
            .build();

    BRICKS_STAIRS = RockBlockVariant
            .builder("bricks/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(BRICKS, v, t))
            .build();

    BRICKS_SLAB_DOUBLE = RockBlockVariant
            .builder("bricks/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(BRICKS, v, t))
            .build();

    BRICKS_SLAB = RockBlockVariant
            .builder("bricks/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(BRICKS, BRICKS_SLAB_DOUBLE, v, t))
            .build();

    BRICKS_WALL = RockBlockVariant
            .builder("bricks/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(BRICKS, v, t))
            .build();

    BRICKS_MOSSY = RockBlockVariant
            .builder("bricks_mossy")
            .baseHardness(6f)
            .factory(BlockRockMossy::new)
            .build();

    BRICKS_MOSSY_STAIRS = RockBlockVariant
            .builder("bricks_mossy/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(BRICKS_MOSSY, v, t))
            .build();

    BRICKS_MOSSY_SLAB_DOUBLE = RockBlockVariant
            .builder("bricks_mossy/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(BRICKS_MOSSY, v, t))
            .build();

    BRICKS_MOSSY_SLAB = RockBlockVariant
            .builder("bricks_mossy/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(BRICKS_MOSSY, BRICKS_MOSSY_SLAB_DOUBLE, v, t))
            .build();

    BRICKS_MOSSY_WALL = RockBlockVariant
            .builder("bricks_mossy/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(BRICKS_MOSSY, v, t))
            .build();

    SMOOTH = RockBlockVariant
            .builder("smooth")
            .baseHardness(6f)
            .factory(BlockRockSmooth::new)
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build();

    SMOOTH_STAIRS = RockBlockVariant
            .builder("smooth/stairs")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockStairs(SMOOTH, v, t))
            .build();

    SMOOTH_SLAB_DOUBLE = RockBlockVariant
            .builder("smooth/slab_double")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Double(SMOOTH, v, t))
            .build();

    SMOOTH_SLAB = RockBlockVariant
            .builder("smooth/slab")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockSlab.Half(SMOOTH, SMOOTH_SLAB_DOUBLE, v, t))
            .build();

    SMOOTH_WALL = RockBlockVariant
            .builder("smooth/wall")
            .baseHardness(6f)
            .factory((v, t) -> new BlockRockWall(SMOOTH, v, t))
            .build();

    BRICKS_CRACKED = RockBlockVariant
            .builder("bricks_cracked")
            .baseHardness(6f)
            .factory(BlockRockBricks::new)
            .build();

    CHISELED = RockBlockVariant
            .builder("chiseled")
            .baseHardness(6f)
            .factory(BlockRockBricks::new)
            .build();

    GRAVEL = RockBlockVariant
            .builder("gravel")
            .baseHardness(6f)
            .factory(BlockRockGravel::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build();

    SAND = RockBlockVariant
            .builder("sand")
            .baseHardness(6f)
            .factory(BlockRockSand::new)
            .fallingSpecification(VERTICAL_AND_HORIZONTAL)
            .build();

    SURFACE = RockBlockVariant
            .builder("surface")
            .baseHardness(6f)
            .factory(BlockRockSurface::new)
            .fallingSpecification(VERTICAL_ONLY)
            .build();

    SPELEOTHEM = RockBlockVariant
            .builder("speleothem")
            .baseHardness(6f)
            .factory(BlockRockSpeleothem::new)
            .build();

    BUTTON = RockBlockVariant
            .builder("button")
            .baseHardness(6f)
            .factory(BlockRockButton::new)
            .build();

    PRESSURE_PLATE = RockBlockVariant
            .builder("pressure_plate")
            .baseHardness(6f)
            .factory(BlockRockPressurePlate::new)
            .build();

    ANVIL = RockBlockVariant
            .builder("anvil")
            .baseHardness(6f)
            .factory(BlockRockAnvil::new)
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build();

    MAGMA = RockBlockVariant
            .builder("magma")
            .baseHardness(6f)
            .factory(BlockRockMagma::new)
            .fallingSpecification(COLLAPSABLE_ROCK)
            .build();

    STAND_GEM = RockBlockVariant
            .builder("stand_gem")
            .baseHardness(1F)
            .factory(BlockRockStandGem::new)
            .build();

    registry.blocks(ROCK_BLOCKS.values());

    ALABASTER_BRICKS = registry.block(new BlockAlabasterBricks());
    ALABASTER_SMOOTH = registry.block(new BlockAlabasterSmooth());
    ALABASTER_RAW = registry.block(new BlockAlabasterRaw());

    registry.blocks(ALABASTER_COLOR_BLOCKS.values());
  }

}
