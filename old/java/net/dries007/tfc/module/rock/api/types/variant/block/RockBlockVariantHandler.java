package net.dries007.tfc.module.rock.api.types.variant.block;

import net.dries007.tfc.module.rock.common.blocks.*;

public class RockBlockVariantHandler {

    public static void init() {
        RockBlockVariants.COBBLE = new RockBlockVariant("cobble", 6f, BlockRockCobble::new);
        RockBlockVariants.RAW = new RockBlockVariant("raw", 6f, BlockRockRaw::new);
        RockBlockVariants.SMOOTH = new RockBlockVariant("smooth", 6f, BlockRockSmooth::new);
        RockBlockVariants.BRICKS = new RockBlockVariant("bricks", 6f, BlockRock::new);
        RockBlockVariants.GRAVEL = new RockBlockVariant("gravel", 6f, BlockRockGravel::new);
        RockBlockVariants.SAND = new RockBlockVariant("sand", 6f, BlockRockSand::new);
        RockBlockVariants.LOOSE_ROCK = new RockBlockVariant("loose_rock", 6f, BlockRockLoose::new);
        RockBlockVariants.SPELEOTHEM = new RockBlockVariant("speleothem", 6f, BlockRockSpeleothem::new);
        RockBlockVariants.BUTTON = new RockBlockVariant("button", 6f, BlockRockButton::new);
        RockBlockVariants.PRESSURE_PLATE = new RockBlockVariant("pressure_plate", 6f, BlockRockPressurePlate::new);
        RockBlockVariants.ANVIL = new RockBlockVariant("anvil", 6f, BlockRockAnvil::new);
        RockBlockVariants.MAGMA = new RockBlockVariant("magma", 6f, BlockRockMagma::new);

        RockBlockVariants.COBBLE_MOSSY = new RockBlockVariant("cobble/mossy", 6f, BlockRockMossy::new);
        RockBlockVariants.BRICKS_MOSSY = new RockBlockVariant("bricks/mossy", 6f, BlockRockMossy::new);

        RockBlockVariants.BRICKS_CRACKED = new RockBlockVariant("bricks/cracked", 6f, BlockRock::new);
        RockBlockVariants.BRICKS_CHISELED = new RockBlockVariant("bricks/chiseled", 6f, BlockRock::new);

        RockBlockVariants.STAIRS_RAW = new RockBlockVariant("stairs/raw", 6f, BlockRockStairs::new);
        RockBlockVariants.STAIRS_COBBLE = new RockBlockVariant("stairs/cobble", 6f, BlockRockStairs::new);
        RockBlockVariants.STAIRS_SMOOTH = new RockBlockVariant("stairs/smooth", 6f, BlockRockStairs::new);
        RockBlockVariants.STAIRS_BRICK = new RockBlockVariant("stairs/bricks", 6f, BlockRockStairs::new);

        RockBlockVariants.SLAB_DOUBLE_RAW = new RockBlockVariant("slab_double/raw", 6f, BlockRockSlab.Double::new);
        RockBlockVariants.SLAB_DOUBLE_COBBLE = new RockBlockVariant("slab_double/cobble", 6f, BlockRockSlab.Double::new);
        RockBlockVariants.SLAB_DOUBLE_SMOOTH = new RockBlockVariant("slab_double/smooth", 6f, BlockRockSlab.Double::new);
        RockBlockVariants.SLAB_DOUBLE_BRICK = new RockBlockVariant("slab_double/bricks", 6f, BlockRockSlab.Double::new);

        RockBlockVariants.SLAB_RAW = new RockBlockVariant("slab/raw", 6f, BlockRockSlab.Half::new);
        RockBlockVariants.SLAB_COBBLE = new RockBlockVariant("slab/cobble", 6f, BlockRockSlab.Half::new);
        RockBlockVariants.SLAB_SMOOTH = new RockBlockVariant("slab/smooth", 6f, BlockRockSlab.Half::new);
        RockBlockVariants.SLAB_BRICK = new RockBlockVariant("slab/bricks", 6f, BlockRockSlab.Half::new);

        RockBlockVariants.WALL_RAW = new RockBlockVariant("wall/raw", 6f, BlockRockWall::new);
        RockBlockVariants.WALL_COBBLE = new RockBlockVariant("wall/cobble", 6f, BlockRockWall::new);
        RockBlockVariants.WALL_SMOOTH = new RockBlockVariant("wall/smooth", 6f, BlockRockWall::new);
        RockBlockVariants.WALL_BRICK = new RockBlockVariant("wall/bricks", 6f, BlockRockWall::new);
    }
}
