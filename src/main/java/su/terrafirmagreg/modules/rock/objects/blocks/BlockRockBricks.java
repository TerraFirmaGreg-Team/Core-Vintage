package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;


import net.dries007.tfc.api.util.FallingBlockManager;

public class BlockRockBricks extends BlockRock {

    public BlockRockBricks(RockBlockVariant blockVariant, RockType type) {
        super(blockVariant, type);

        FallingBlockManager.registerFallable(this, blockVariant.getSpecification());
    }
}
