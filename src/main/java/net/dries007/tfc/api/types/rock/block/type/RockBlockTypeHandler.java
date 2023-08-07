package net.dries007.tfc.api.types.rock.block.type;

import net.dries007.tfc.objects.blocks.rock.*;

import static net.dries007.tfc.api.types.rock.block.type.RockBlockTypes.*;
import static net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants.*;

public class RockBlockTypeHandler {

    public static void init() {

        COMMON = new RockBlockType
                .Builder("common", BlockRock::new)
                .addBlockVariation(RAW, BlockRockRaw::new)
                .addBlockVariation(COBBLE, BlockRockFallable::new)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .addBlockVariation(BRICK_CRACKED)
                .addBlockVariation(BRICK_CHISELED)
                .build();

        MOSSY = new RockBlockType
                .Builder("mossy", BlockRockMossy::new)
                .addBlockVariation(COBBLE)
                .addBlockVariation(BRICK)
                .build();

        FALLABLE = new RockBlockType
                .Builder("fallable", BlockRockFallable::new)
                .addBlockVariation(GRAVEL, BlockRockGravel::new)
                .addBlockVariation(SAND, BlockRockSand::new)
                .build();

        STAIRS = new RockBlockType
                .Builder("stairs", BlockRockStairs::new)
                .addBlockVariation(RAW)
                .addBlockVariation(COBBLE)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .build();

        SLAB_DOUBLE = new RockBlockType
                .Builder("slab_double", BlockRockSlab.Double::new)
                .addBlockVariation(RAW)
                .addBlockVariation(COBBLE)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .build();

        SLAB = new RockBlockType
                .Builder("slab", BlockRockSlab.Half::new)
                .addBlockVariation(RAW)
                .addBlockVariation(COBBLE)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .build();

        WALL = new RockBlockType
                .Builder("wall", BlockRockWall::new)
                .addBlockVariation(RAW)
                .addBlockVariation(COBBLE)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .build();

        LOOSE = new RockBlockType
                .Builder("loose", BlockRockLoose::new)
                .build();

        SPELEOTHEM = new RockBlockType
                .Builder("speleothem", BlockRockSpeleothem::new)
                .build();

        BUTTON = new RockBlockType
                .Builder("button", BlockRockButton::new)
                .build();

        PRESSURE_PLATE = new RockBlockType
                .Builder("pressure_plate", BlockRockPressurePlate::new)
                .build();

        ANVIL = new RockBlockType
                .Builder("anvil", BlockRockAnvil::new)
                .build();
    }
}
