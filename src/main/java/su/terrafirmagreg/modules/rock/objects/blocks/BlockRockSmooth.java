package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

public class BlockRockSmooth extends BlockRock {

    public BlockRockSmooth(RockBlockVariant variant, RockType type) {
        super(variant, type);
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, getVariant());
        OreDictUtils.register(this, "stonePolished");
    }
}
