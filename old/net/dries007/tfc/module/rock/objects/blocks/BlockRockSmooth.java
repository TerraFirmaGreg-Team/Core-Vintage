package net.dries007.tfc.module.rock.objects.blocks;

import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockRockSmooth extends BlockRock {

    public BlockRockSmooth(RockBlockVariant variant, RockType type) {
        super(variant, type);

        OreDictionaryHelper.register(this, "stonePolished");
    }
}
