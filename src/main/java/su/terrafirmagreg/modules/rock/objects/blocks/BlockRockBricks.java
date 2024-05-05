package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;


import net.dries007.tfc.api.util.FallingBlockManager;

public class BlockRockBricks extends BlockRock {

    public BlockRockBricks(RockBlockVariant variant, RockType type) {
        super(variant, type);

        FallingBlockManager.registerFallable(this, variant.getSpecification());
    }
}
