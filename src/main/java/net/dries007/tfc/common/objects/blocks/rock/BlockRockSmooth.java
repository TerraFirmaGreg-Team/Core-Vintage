package net.dries007.tfc.common.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockRockSmooth extends BlockRock {

    public BlockRockSmooth(RockBlockVariant rockBlockVariant, RockType rockType) {
        super(rockBlockVariant, rockType);

        OreDictionaryHelper.register(this, "stonePolished");
    }
}
