package net.dries007.tfc.api.types.rock.block.type;

import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants;
import net.dries007.tfc.objects.blocks.rock.*;

public class RockBlockTypeHandler {

    public static void init() {

        RockBlockTypes.Common = new RockBlockType
                .Builder("common", BlockRock::new)
                .addBlockVariation(RockBlockVariants.Raw, BlockRockRaw::new)
                .addBlockVariation(RockBlockVariants.Cobble, BlockRockFallable::new)
                .addBlockVariation(RockBlockVariants.Smooth)
                .addBlockVariation(RockBlockVariants.Brick)
                .addBlockVariation(RockBlockVariants.BrickCracked)
                .addBlockVariation(RockBlockVariants.BrickChiseled)
                .build();

        RockBlockTypes.Mossy = new RockBlockType
                .Builder("mossy", BlockRockMossy::new)
                .addBlockVariation(RockBlockVariants.Cobble)
                .addBlockVariation(RockBlockVariants.Brick)
                .build();

        RockBlockTypes.Fallable = new RockBlockType
                .Builder("fallable", BlockRockFallable::new)
                .addBlockVariation(RockBlockVariants.Gravel, BlockRockGravel::new)
                .addBlockVariation(RockBlockVariants.Sand, BlockRockSand::new)
                .build();

        RockBlockTypes.Stairs = new RockBlockType
                .Builder("stairs", BlockRockStairs::new)
                .addBlockVariation(RockBlockVariants.Raw)
                .addBlockVariation(RockBlockVariants.Cobble)
                .addBlockVariation(RockBlockVariants.Smooth)
                .addBlockVariation(RockBlockVariants.Brick)
                .build();

        RockBlockTypes.SlabDouble = new RockBlockType
                .Builder("slab_double", BlockRockSlab.Double::new)
                .addBlockVariation(RockBlockVariants.Raw)
                .addBlockVariation(RockBlockVariants.Cobble)
                .addBlockVariation(RockBlockVariants.Smooth)
                .addBlockVariation(RockBlockVariants.Brick)
                .build();

        RockBlockTypes.Slab = new RockBlockType
                .Builder("slab", BlockRockSlab.Half::new)
                .addBlockVariation(RockBlockVariants.Raw)
                .addBlockVariation(RockBlockVariants.Cobble)
                .addBlockVariation(RockBlockVariants.Smooth)
                .addBlockVariation(RockBlockVariants.Brick)
                .build();

        RockBlockTypes.Wall = new RockBlockType
                .Builder("wall", BlockRockWall::new)
                .addBlockVariation(RockBlockVariants.Raw)
                .addBlockVariation(RockBlockVariants.Cobble)
                .addBlockVariation(RockBlockVariants.Smooth)
                .addBlockVariation(RockBlockVariants.Brick)
                .build();

        RockBlockTypes.Loose = new RockBlockType
                .Builder("loose", BlockRockLoose::new)
                .build();

        RockBlockTypes.Speleothem = new RockBlockType
                .Builder("speleothem", BlockRockSpeleothem::new)
                .build();

        RockBlockTypes.Button = new RockBlockType
                .Builder("button", BlockRockButton::new)
                .build();

        RockBlockTypes.PressurePlate = new RockBlockType
                .Builder("pressure_plate", BlockRockPressurePlate::new)
                .build();

        RockBlockTypes.Anvil = new RockBlockType
                .Builder("anvil", BlockRockAnvil::new)
                .build();
    }
}
