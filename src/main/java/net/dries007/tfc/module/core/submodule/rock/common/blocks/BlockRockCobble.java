package net.dries007.tfc.module.core.submodule.rock.common.blocks;

import net.dries007.tfc.module.core.submodule.rock.api.type.RockType;
import net.dries007.tfc.module.core.submodule.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockRockCobble extends BlockRockFallable {

    public BlockRockCobble(RockBlockVariant variant, RockType type) {
        super(variant, type);

        FallingBlockManager.registerFallable(this, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK);

        setHardness(getFinalHardness());

        OreDictionaryHelper.register(this, "cobblestone");
    }

}
