package net.dries007.tfc.module.rock.common.blocks;

import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockRockCobble extends BlockRockFallable {

    public BlockRockCobble(RockBlockVariant variant, RockType type) {
        super(variant, type);

        setHardness(getFinalHardness());

        FallingBlockManager.registerFallable(this, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK);
        OreDictionaryHelper.register(this, "cobblestone");
    }

}
