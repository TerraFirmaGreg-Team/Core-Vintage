package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.modules.rock.objects.blocks.*;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;

public class RockBlockVariantHandler {

	public static void init() {

		RockBlockVariants.COBBLE = new RockBlockVariant.Builder("cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockCobble::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL_ROCK)
				.build();

		RockBlockVariants.STAIRS_COBBLE = new RockBlockVariant.Builder("stairs/cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockStairs::new)
				.build();

		RockBlockVariants.SLAB_DOUBLE_COBBLE = new RockBlockVariant.Builder("slab_double/cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Double::new)
				.build();

		RockBlockVariants.SLAB_COBBLE = new RockBlockVariant.Builder("slab/cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Half::new)
				.build();

		RockBlockVariants.WALL_COBBLE = new RockBlockVariant.Builder("wall/cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockWall::new)
				.build();

		RockBlockVariants.MOSSY_COBBLE = new RockBlockVariant.Builder("mossy_cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockMossy::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL_ROCK)
				.build();

		RockBlockVariants.STAIRS_MOSSY_COBBLE = new RockBlockVariant.Builder("stairs/mossy_cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockStairs::new)
				.build();

		RockBlockVariants.SLAB_DOUBLE_MOSSY_COBBLE = new RockBlockVariant.Builder("slab_double/mossy_cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Double::new)
				.build();

		RockBlockVariants.SLAB_MOSSY_COBBLE = new RockBlockVariant.Builder("slab/mossy_cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Half::new)
				.build();

		RockBlockVariants.WALL_MOSSY_COBBLE = new RockBlockVariant.Builder("wall/mossy_cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockWall::new)
				.build();

		RockBlockVariants.RAW = new RockBlockVariant.Builder("raw")
				.setBaseHardness(6f)
				.setFactory(BlockRockRaw::new)
				.setStoneType()
				.setFallingSpecification(COLLAPSABLE_ROCK)
				.build();

		RockBlockVariants.STAIRS_RAW = new RockBlockVariant.Builder("stairs/raw")
				.setBaseHardness(6f)
				.setFactory(BlockRockStairs::new)
				.build();

		RockBlockVariants.SLAB_DOUBLE_RAW = new RockBlockVariant.Builder("slab_double/raw")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Double::new)
				.build();

		RockBlockVariants.SLAB_RAW = new RockBlockVariant.Builder("slab/raw")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Half::new)
				.build();

		RockBlockVariants.WALL_RAW = new RockBlockVariant.Builder("wall/raw")
				.setBaseHardness(6f)
				.setFactory(BlockRockWall::new)
				.build();

		RockBlockVariants.SMOOTH = new RockBlockVariant.Builder("smooth")
				.setBaseHardness(6f)
				.setFactory(BlockRockSmooth::new)
				.build();

		RockBlockVariants.STAIRS_SMOOTH = new RockBlockVariant.Builder("stairs/smooth")
				.setBaseHardness(6f)
				.setFactory(BlockRockStairs::new)
				.build();

		RockBlockVariants.SLAB_DOUBLE_SMOOTH = new RockBlockVariant.Builder("slab_double/smooth")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Double::new)
				.build();

		RockBlockVariants.SLAB_SMOOTH = new RockBlockVariant.Builder("slab/smooth")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Half::new)
				.build();

		RockBlockVariants.WALL_SMOOTH = new RockBlockVariant.Builder("wall/smooth")
				.setBaseHardness(6f)
				.setFactory(BlockRockWall::new)
				.build();

		RockBlockVariants.BRICKS = new RockBlockVariant.Builder("bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockBricks::new)
				.build();

		RockBlockVariants.STAIRS_BRICK = new RockBlockVariant.Builder("stairs/bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockStairs::new)
				.build();

		RockBlockVariants.SLAB_DOUBLE_BRICK = new RockBlockVariant.Builder("slab_double/bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Double::new)
				.build();

		RockBlockVariants.SLAB_BRICK = new RockBlockVariant.Builder("slab/bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Half::new)
				.build();

		RockBlockVariants.WALL_BRICK = new RockBlockVariant.Builder("wall/bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockWall::new)
				.build();

		RockBlockVariants.MOSSY_BRICKS = new RockBlockVariant.Builder("mossy_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockMossy::new)
				.build();

		RockBlockVariants.STAIRS_MOSSY_BRICKS = new RockBlockVariant.Builder("stairs/mossy_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockStairs::new)
				.build();

		RockBlockVariants.SLAB_DOUBLE_MOSSY_BRICKS = new RockBlockVariant.Builder("slab_double/mossy_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Double::new)
				.build();

		RockBlockVariants.SLAB_MOSSY_BRICKS = new RockBlockVariant.Builder("slab/mossy_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockSlab.Half::new)
				.build();

		RockBlockVariants.WALL_MOSSY_BRICKS = new RockBlockVariant.Builder("wall/mossy_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockWall::new)
				.build();

		RockBlockVariants.CRACKED_BRICKS = new RockBlockVariant.Builder("cracked_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockBricks::new)
				.build();

		RockBlockVariants.CHISELED = new RockBlockVariant.Builder("chiseled")
				.setBaseHardness(6f)
				.setFactory(BlockRockBricks::new)
				.build();

		RockBlockVariants.GRAVEL = new RockBlockVariant.Builder("gravel")
				.setBaseHardness(6f)
				.setFactory(BlockRockGravel::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL)
				.build();

		RockBlockVariants.SAND = new RockBlockVariant.Builder("sand")
				.setBaseHardness(6f)
				.setFactory(BlockRockSand::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL)
				.build();

		RockBlockVariants.SURFACE = new RockBlockVariant.Builder("surface")
				.setBaseHardness(6f)
				.setFactory(BlockRockSurface::new)
				.setFallingSpecification(VERTICAL_ONLY)
				.build();

		RockBlockVariants.SPELEOTHEM = new RockBlockVariant.Builder("speleothem")
				.setBaseHardness(6f)
				.setFactory(BlockRockSpeleothem::new)
				.build();

		RockBlockVariants.BUTTON = new RockBlockVariant.Builder("button")
				.setBaseHardness(6f)
				.setFactory(BlockRockButton::new)
				.build();

		RockBlockVariants.PRESSURE_PLATE = new RockBlockVariant.Builder("pressure_plate")
				.setBaseHardness(6f)
				.setFactory(BlockRockPressurePlate::new)
				.build();

		RockBlockVariants.ANVIL = new RockBlockVariant.Builder("anvil")
				.setBaseHardness(6f)
				.setFactory(BlockRockAnvil::new)
				.setFallingSpecification(COLLAPSABLE_ROCK)
				.build();

		RockBlockVariants.MAGMA = new RockBlockVariant.Builder("magma")
				.setBaseHardness(6f)
				.setFactory(BlockRockMagma::new)
				.setFallingSpecification(COLLAPSABLE_ROCK)
				.build();

		RockBlockVariants.STAND_GEM = new RockBlockVariant.Builder("stand_gem")
				.setBaseHardness(1F)
				.setFactory(BlockRockStandGem::new)
				.build();
	}
}
