package su.terrafirmagreg.modules.rock.objects.blocks;

import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.util.OreDictionaryHelper;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

public class BlockRockCobble extends BlockRockFallable {


    public BlockRockCobble(RockBlockVariant variant, RockType type) {
        super(variant, type);

        setHardness(getFinalHardness());

        FallingBlockManager.registerFallable(this, variant.getSpecification());
        OreDictionaryHelper.register(this, "cobblestone");
    }
}
