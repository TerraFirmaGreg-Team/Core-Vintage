package net.dries007.tfc.module.rock.objects.blocks;

import net.dries007.tfc.module.core.api.util.FallingBlockManager;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;

public class BlockRockCobble extends BlockRockFallable {

    public BlockRockCobble(RockBlockVariant variant, RockType type) {
        super(variant, type);

        setHardness(getFinalHardness());

        FallingBlockManager.registerFallable(this, FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK);
        OreDictionaryHelper.register(this, "cobblestone");
    }

}
