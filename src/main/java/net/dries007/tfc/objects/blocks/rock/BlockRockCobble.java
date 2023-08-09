package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRockCobble extends BlockRockFallable {

    public BlockRockCobble(RockBlockVariant rockBlockVariant, RockType rockType) {
        super(rockBlockVariant, rockType);

        FallingBlockManager.registerFallable(this, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK);
    }

}
