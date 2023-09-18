package net.dries007.tfc.module.core.submodule.rock.common.blocks;

import net.dries007.tfc.module.core.submodule.rock.api.type.RockType;
import net.dries007.tfc.module.core.submodule.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockRockSmooth extends BlockRock {

    public BlockRockSmooth(RockBlockVariant variant, RockType type) {
        super(variant, type);

        OreDictionaryHelper.register(this, "stonePolished");
    }
}
