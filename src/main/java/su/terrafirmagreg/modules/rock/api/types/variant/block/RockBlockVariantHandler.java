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

		RockBlockVariants.MOSSY_COBBLE = new RockBlockVariant.Builder("mossy_cobble")
				.setBaseHardness(6f)
				.setFactory(BlockRockMossy::new)
				.setFallingSpecification(VERTICAL_AND_HORIZONTAL_ROCK)
				.build();

		RockBlockVariants.RAW = new RockBlockVariant.Builder("raw")
				.setBaseHardness(6f)
				.setFactory(BlockRockRaw::new)
				.setStoneType()
				.setFallingSpecification(COLLAPSABLE_ROCK)
				.build();

		RockBlockVariants.MOSSY_RAW = new RockBlockVariant.Builder("mossy_raw")
				.setBaseHardness(6f)
				.setFactory(BlockRockMossy::new)
				.setFallingSpecification(COLLAPSABLE_ROCK)
				.build();

		RockBlockVariants.SMOOTH = new RockBlockVariant.Builder("smooth")
				.setBaseHardness(6f)
				.setFactory(BlockRockSmooth::new)
				.build();

		RockBlockVariants.BRICKS = new RockBlockVariant.Builder("bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockBricks::new)
				.build();

		RockBlockVariants.MOSSY_BRICKS = new RockBlockVariant.Builder("mossy_bricks")
				.setBaseHardness(6f)
				.setFactory(BlockRockMossy::new)
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

//        RockBlockVariants.SAND = new RockBlockVariant.Builder("sand")
//                .setBaseHardness(6f)
//                .setFactory(BlockRockSand::new)
//                .setFallingSpecification(VERTICAL_AND_HORIZONTAL)
//                .build();

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
				.setFactory(BlockRockGemDisplay::new)
				.build();

//
//		RockBlockVariants.STAIRS_RAW = new RockBlockVariant("stairs/raw", 6f, BlockRockStairs::new);
//		RockBlockVariants.STAIRS_COBBLE = new RockBlockVariant("stairs/cobble", 6f, BlockRockStairs::new);
//		RockBlockVariants.STAIRS_SMOOTH = new RockBlockVariant("stairs/smooth", 6f, BlockRockStairs::new);
//		RockBlockVariants.STAIRS_BRICK = new RockBlockVariant("stairs/bricks", 6f, BlockRockStairs::new);
//
//		RockBlockVariants.SLAB_DOUBLE_RAW = new RockBlockVariant("slab_double/raw", 6f, BlockRockSlab.Double::new);
//		RockBlockVariants.SLAB_DOUBLE_COBBLE = new RockBlockVariant("slab_double/cobble", 6f, BlockRockSlab.Double::new);
//		RockBlockVariants.SLAB_DOUBLE_SMOOTH = new RockBlockVariant("slab_double/smooth", 6f, BlockRockSlab.Double::new);
//		RockBlockVariants.SLAB_DOUBLE_BRICK = new RockBlockVariant("slab_double/bricks", 6f, BlockRockSlab.Double::new);
//
//		RockBlockVariants.SLAB_RAW = new RockBlockVariant("slab/raw", 6f, BlockRockSlab.Half::new);
//		RockBlockVariants.SLAB_COBBLE = new RockBlockVariant("slab/cobble", 6f, BlockRockSlab.Half::new);
//		RockBlockVariants.SLAB_SMOOTH = new RockBlockVariant("slab/smooth", 6f, BlockRockSlab.Half::new);
//		RockBlockVariants.SLAB_BRICK = new RockBlockVariant("slab/bricks", 6f, BlockRockSlab.Half::new);
//
//		RockBlockVariants.WALL_RAW = new RockBlockVariant("wall/raw", 6f, BlockRockWall::new);
//		RockBlockVariants.WALL_COBBLE = new RockBlockVariant("wall/cobble", 6f, BlockRockWall::new);
//		RockBlockVariants.WALL_SMOOTH = new RockBlockVariant("wall/smooth", 6f, BlockRockWall::new);
//		RockBlockVariants.WALL_BRICK = new RockBlockVariant("wall/bricks", 6f, BlockRockWall::new);
	}
}
