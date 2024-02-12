package su.terrafirmagreg.modules.rock.objects.blocks;

import net.dries007.tfc.util.OreDictionaryHelper;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

public class BlockRockSmooth extends BlockRock {

    public BlockRockSmooth(RockBlockVariant blockVariant, RockType type) {
        super(blockVariant, type);

        //OreDictionaryHelper.register(this, "stonePolished");
    }
}
