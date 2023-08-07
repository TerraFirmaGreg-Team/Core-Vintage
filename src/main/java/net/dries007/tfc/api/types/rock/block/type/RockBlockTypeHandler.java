package net.dries007.tfc.api.types.rock.block.type;

import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants;
import net.dries007.tfc.objects.blocks.rock.*;

import static net.dries007.tfc.api.types.rock.block.type.RockBlockTypes.*;

public class RockBlockTypeHandler {

    public static void init() {

        COMMON = new RockBlockType
                .Builder("common", BlockRock::new)
                .addBlockVariation(RockBlockVariants.RAW, BlockRockRaw::new)
                .addBlockVariation(RockBlockVariants.COBBLE, BlockRockFallable::new)
                .addBlockVariation(RockBlockVariants.SMOOTH)
                .addBlockVariation(RockBlockVariants.BRICK)
                .addBlockVariation(RockBlockVariants.BRICK_CRACKED)
                .addBlockVariation(RockBlockVariants.BRICK_CHISELED)
                .build();

        MOSSY = new RockBlockType
                .Builder("mossy", BlockRockMossy::new)
                .addBlockVariation(RockBlockVariants.COBBLE)
                .addBlockVariation(RockBlockVariants.BRICK)
                .build();

        FALLABLE = new RockBlockType
                .Builder("fallable", BlockRockFallable::new)
                .addBlockVariation(RockBlockVariants.GRAVEL, BlockRockGravel::new)
                .addBlockVariation(RockBlockVariants.SAND, BlockRockSand::new)
                .build();

        STAIRS = new RockBlockType
                .Builder("stairs", BlockRockStairs::new)
                .addBlockVariation(RockBlockVariants.RAW)
                .addBlockVariation(RockBlockVariants.COBBLE)
                .addBlockVariation(RockBlockVariants.SMOOTH)
                .addBlockVariation(RockBlockVariants.BRICK)
                .build();

        SLAB_DOUBLE = new RockBlockType
                .Builder("slab_double", BlockRockSlab.Double::new)
                .addBlockVariation(RockBlockVariants.RAW)
                .addBlockVariation(RockBlockVariants.COBBLE)
                .addBlockVariation(RockBlockVariants.SMOOTH)
                .addBlockVariation(RockBlockVariants.BRICK)
                .build();

        SLAB = new RockBlockType
                .Builder("slab", BlockRockSlab.Half::new)
                .addBlockVariation(RockBlockVariants.RAW)
                .addBlockVariation(RockBlockVariants.COBBLE)
                .addBlockVariation(RockBlockVariants.SMOOTH)
                .addBlockVariation(RockBlockVariants.BRICK)
                .build();

        WALL = new RockBlockType
                .Builder("wall", BlockRockWall::new)
                .addBlockVariation(RockBlockVariants.RAW)
                .addBlockVariation(RockBlockVariants.COBBLE)
                .addBlockVariation(RockBlockVariants.SMOOTH)
                .addBlockVariation(RockBlockVariants.BRICK)
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
