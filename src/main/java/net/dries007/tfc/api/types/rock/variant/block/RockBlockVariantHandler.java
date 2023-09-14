package net.dries007.tfc.api.types.rock.variant.block;

import net.dries007.tfc.common.objects.blocks.rock.*;

import static net.dries007.tfc.api.types.rock.variant.block.RockBlockVariants.*;

public class RockBlockVariantHandler {

    public static void init() {
        COBBLE = new RockBlockVariant("cobble", 6f, BlockRockCobble::new);
        RAW = new RockBlockVariant("raw", 6f, BlockRockRaw::new);
        SMOOTH = new RockBlockVariant("smooth", 6f, BlockRockSmooth::new);
        BRICKS = new RockBlockVariant("bricks", 6f, BlockRock::new);
        GRAVEL = new RockBlockVariant("gravel", 6f, BlockRockGravel::new);
        SAND = new RockBlockVariant("sand", 6f, BlockRockSand::new);
        LOOSE_ROCK = new RockBlockVariant("loose_rock", 6f, BlockRockLoose::new);
        SPELEOTHEM = new RockBlockVariant("speleothem", 6f, BlockRockSpeleothem::new);
        BUTTON = new RockBlockVariant("button", 6f, BlockRockButton::new);
        PRESSURE_PLATE = new RockBlockVariant("pressure_plate", 6f, BlockRockPressurePlate::new);
        ANVIL = new RockBlockVariant("anvil", 6f, BlockRockAnvil::new);
        MAGMA = new RockBlockVariant("magma", 6f, BlockRockMagma::new);

        COBBLE_MOSSY = new RockBlockVariant("cobble/mossy", 6f, BlockRockMossy::new);
        BRICKS_MOSSY = new RockBlockVariant("bricks/mossy", 6f, BlockRockMossy::new);

        BRICKS_CRACKED = new RockBlockVariant("bricks/cracked", 6f, BlockRock::new);
        BRICKS_CHISELED = new RockBlockVariant("bricks/chiseled", 6f, BlockRock::new);

        STAIRS_RAW = new RockBlockVariant("stairs/raw", 6f, BlockRockStairs::new);
        STAIRS_COBBLE = new RockBlockVariant("stairs/cobble", 6f, BlockRockStairs::new);
        STAIRS_SMOOTH = new RockBlockVariant("stairs/smooth", 6f, BlockRockStairs::new);
        STAIRS_BRICK = new RockBlockVariant("stairs/bricks", 6f, BlockRockStairs::new);

        SLAB_DOUBLE_RAW = new RockBlockVariant("slab_double/raw", 6f, BlockRockSlab.Double::new);
        SLAB_DOUBLE_COBBLE = new RockBlockVariant("slab_double/cobble", 6f, BlockRockSlab.Double::new);
        SLAB_DOUBLE_SMOOTH = new RockBlockVariant("slab_double/smooth", 6f, BlockRockSlab.Double::new);
        SLAB_DOUBLE_BRICK = new RockBlockVariant("slab_double/bricks", 6f, BlockRockSlab.Double::new);

        SLAB_RAW = new RockBlockVariant("slab/raw", 6f, BlockRockSlab.Half::new);
        SLAB_COBBLE = new RockBlockVariant("slab/cobble", 6f, BlockRockSlab.Half::new);
        SLAB_SMOOTH = new RockBlockVariant("slab/smooth", 6f, BlockRockSlab.Half::new);
        SLAB_BRICK = new RockBlockVariant("slab/bricks", 6f, BlockRockSlab.Half::new);

        WALL_RAW = new RockBlockVariant("wall/raw", 6f, BlockRockWall::new);
        WALL_COBBLE = new RockBlockVariant("wall/cobble", 6f, BlockRockWall::new);
        WALL_SMOOTH = new RockBlockVariant("wall/smooth", 6f, BlockRockWall::new);
        WALL_BRICK = new RockBlockVariant("wall/bricks", 6f, BlockRockWall::new);
    }
}
