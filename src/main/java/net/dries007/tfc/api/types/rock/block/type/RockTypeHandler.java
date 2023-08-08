package net.dries007.tfc.api.types.rock.block.type;

import net.dries007.tfc.objects.blocks.rock.*;

import static net.dries007.tfc.api.types.rock.block.type.RockTypes.*;
import static net.dries007.tfc.api.types.rock.block.variant.RockVariants.*;

public class RockTypeHandler {

    public static void init() {

        COMMON = new RockType
                .Builder("common", BlockRock::new)
                .addBlockVariation(RAW, BlockRockRaw::new)
                .addBlockVariation(COBBLE, BlockRockFallable::new)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .addBlockVariation(BRICK_CRACKED)
                .addBlockVariation(BRICK_CHISELED)
                .build();

        MOSSY = new RockType
                .Builder("mossy", BlockRockMossy::new)
                .addBlockVariation(COBBLE)
                .addBlockVariation(BRICK)
                .build();

        FALLABLE = new RockType
                .Builder("fallable", BlockRockFallable::new)
                .addBlockVariation(GRAVEL, BlockRockGravel::new)
                .addBlockVariation(SAND, BlockRockSand::new)
                .build();

        STAIRS = new RockType
                .Builder("stairs", BlockRockStairs::new)
                .addBlockVariation(RAW)
                .addBlockVariation(COBBLE)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .build();

        SLAB_DOUBLE = new RockType
                .Builder("slab_double", BlockRockSlab.Double::new)
                .addBlockVariation(RAW)
                .addBlockVariation(COBBLE)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .build();

        SLAB = new RockType
                .Builder("slab", BlockRockSlab.Half::new)
                .addBlockVariation(RAW)
                .addBlockVariation(COBBLE)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .build();

        WALL = new RockType
                .Builder("wall", BlockRockWall::new)
                .addBlockVariation(RAW)
                .addBlockVariation(COBBLE)
                .addBlockVariation(SMOOTH)
                .addBlockVariation(BRICK)
                .build();

        LOOSE = new RockType
                .Builder("loose", BlockRockLoose::new)
                .build();

        SPELEOTHEM = new RockType
                .Builder("speleothem", BlockRockSpeleothem::new)
                .build();

        BUTTON = new RockType
                .Builder("button", BlockRockButton::new)
                .build();

        PRESSURE_PLATE = new RockType
                .Builder("pressure_plate", BlockRockPressurePlate::new)
                .build();

        ANVIL = new RockType
                .Builder("anvil", BlockRockAnvil::new)
                .build();
    }
}
