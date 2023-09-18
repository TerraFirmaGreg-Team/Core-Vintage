package net.dries007.tfc.module.rock.common.blocks;

import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockRockSmooth extends BlockRock {

    public BlockRockSmooth(RockBlockVariant variant, RockType type) {
        super(variant, type);

        OreDictionaryHelper.register(this, "stonePolished");
    }
}
