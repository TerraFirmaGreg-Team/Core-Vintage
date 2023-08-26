package net.dries007.tfc.common.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.block.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockRockSmooth extends BlockRock {

    public BlockRockSmooth(RockBlockVariant variant, RockType type) {
        super(variant, type);

        OreDictionaryHelper.register(this, "stonePolished");
    }
}
