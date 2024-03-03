package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.modules.rock.objects.blocks.*;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;
import static su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariants.*;

public class RockBlockVariantHandler {

	public static void init() {

		COBBLE = new RockBlockVariant.Builder("cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockCobble::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL_ROCK)
				.build();

		STAIRS_COBBLE = new RockBlockVariant.Builder("stairs/cobble")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockStairs(COBBLE, v, t))
				.build();

		SLAB_DOUBLE_COBBLE = new RockBlockVariant.Builder("slab_double/cobble")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Double(COBBLE, v, t))
				.build();

		SLAB_COBBLE = new RockBlockVariant.Builder("slab/cobble")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Half(COBBLE, SLAB_DOUBLE_COBBLE, v, t))
				.build();

		WALL_COBBLE = new RockBlockVariant.Builder("wall/cobble")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockWall(COBBLE, v, t))
				.build();

		MOSSY_COBBLE = new RockBlockVariant.Builder("mossy_cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockMossy::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL_ROCK)
				.build();

		STAIRS_MOSSY_COBBLE = new RockBlockVariant.Builder("stairs/mossy_cobble")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockStairs(MOSSY_COBBLE, v, t))
				.build();

		SLAB_DOUBLE_MOSSY_COBBLE = new RockBlockVariant.Builder("slab_double/mossy_cobble")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Double(MOSSY_COBBLE, v, t))
				.build();

		SLAB_MOSSY_COBBLE = new RockBlockVariant.Builder("slab/mossy_cobble")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Half(MOSSY_COBBLE, SLAB_DOUBLE_MOSSY_COBBLE, v, t))
				.build();

		WALL_MOSSY_COBBLE = new RockBlockVariant.Builder("wall/mossy_cobble")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockWall(MOSSY_COBBLE, v, t))
				.build();

		RAW = new RockBlockVariant.Builder("raw")
				.setBaseHardness(6f)
				.setFactory(BlockRockRaw::new)
				.setStoneType()
				.setFallingSpecification(COLLAPSABLE_ROCK)
				.build();

		STAIRS_RAW = new RockBlockVariant.Builder("stairs/raw")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockStairs(RAW, v, t))
				.build();

		SLAB_DOUBLE_RAW = new RockBlockVariant.Builder("slab_double/raw")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Double(RAW, v, t))
				.build();

		SLAB_RAW = new RockBlockVariant.Builder("slab/raw")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Half(RAW, SLAB_DOUBLE_RAW, v, t))
				.build();

		WALL_RAW = new RockBlockVariant.Builder("wall/raw")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockWall(RAW, v, t))
				.build();

		SMOOTH = new RockBlockVariant.Builder("smooth")
				.setBaseHardness(6f)
				.setFactory(BlockRockSmooth::new)
				.build();

		STAIRS_SMOOTH = new RockBlockVariant.Builder("stairs/smooth")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockStairs(SMOOTH, v, t))
				.build();

		SLAB_DOUBLE_SMOOTH = new RockBlockVariant.Builder("slab_double/smooth")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Double(SMOOTH, v, t))
				.build();

		SLAB_SMOOTH = new RockBlockVariant.Builder("slab/smooth")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Half(SMOOTH, SLAB_DOUBLE_SMOOTH, v, t))
				.build();

		WALL_SMOOTH = new RockBlockVariant.Builder("wall/smooth")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockWall(SMOOTH, v, t))
				.build();

		BRICKS = new RockBlockVariant.Builder("bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockBricks::new)
				.build();

		STAIRS_BRICK = new RockBlockVariant.Builder("stairs/bricks")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockStairs(BRICKS, v, t))
				.build();

		SLAB_DOUBLE_BRICK = new RockBlockVariant.Builder("slab_double/bricks")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Double(BRICKS, v, t))
				.build();

		SLAB_BRICK = new RockBlockVariant.Builder("slab/bricks")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Half(BRICKS, SLAB_DOUBLE_BRICK, v, t))
				.build();

		WALL_BRICK = new RockBlockVariant.Builder("wall/bricks")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockWall(BRICKS, v, t))
				.build();

		MOSSY_BRICKS = new RockBlockVariant.Builder("mossy_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockMossy::new)
				.build();

		STAIRS_MOSSY_BRICKS = new RockBlockVariant.Builder("stairs/mossy_bricks")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockStairs(MOSSY_BRICKS, v, t))
				.build();

		SLAB_DOUBLE_MOSSY_BRICKS = new RockBlockVariant.Builder("slab_double/mossy_bricks")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Double(MOSSY_BRICKS, v, t))
				.build();

		SLAB_MOSSY_BRICKS = new RockBlockVariant.Builder("slab/mossy_bricks")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockSlab.Half(MOSSY_BRICKS, SLAB_DOUBLE_MOSSY_BRICKS, v, t))
				.build();

		WALL_MOSSY_BRICKS = new RockBlockVariant.Builder("wall/mossy_bricks")
				.setBaseHardness(6f)
				.setFactory((v, t) -> new BlockRockWall(MOSSY_BRICKS, v, t))
				.build();

		CRACKED_BRICKS = new RockBlockVariant.Builder("cracked_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockBricks::new)
				.build();

		CHISELED = new RockBlockVariant.Builder("chiseled")
				.setBaseHardness(6f)
				.setFactory(BlockRockBricks::new)
				.build();

		GRAVEL = new RockBlockVariant.Builder("gravel")
				.setBaseHardness(6f)
				.setFactory(BlockRockGravel::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL)
				.build();

		SAND = new RockBlockVariant.Builder("sand")
				.setBaseHardness(6f)
				.setFactory(BlockRockSand::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL)
				.build();

		SURFACE = new RockBlockVariant.Builder("surface")
				.setBaseHardness(6f)
				.setFactory(BlockRockSurface::new)
				.setFallingSpecification(VERTICAL_ONLY)
				.build();

		SPELEOTHEM = new RockBlockVariant.Builder("speleothem")
				.setBaseHardness(6f)
				.setFactory(BlockRockSpeleothem::new)
				.build();

		BUTTON = new RockBlockVariant.Builder("button")
				.setBaseHardness(6f)
				.setFactory(BlockRockButton::new)
				.build();

		PRESSURE_PLATE = new RockBlockVariant.Builder("pressure_plate")
				.setBaseHardness(6f)
				.setFactory(BlockRockPressurePlate::new)
				.build();

		ANVIL = new RockBlockVariant.Builder("anvil")
				.setBaseHardness(6f)
				.setFactory(BlockRockAnvil::new)
				.setFallingSpecification(COLLAPSABLE_ROCK)
				.build();

		MAGMA = new RockBlockVariant.Builder("magma")
				.setBaseHardness(6f)
				.setFactory(BlockRockMagma::new)
				.setFallingSpecification(COLLAPSABLE_ROCK)
				.build();

		STAND_GEM = new RockBlockVariant.Builder("stand_gem")
				.setBaseHardness(1F)
				.setFactory(BlockRockStandGem::new)
				.build();
	}
}
