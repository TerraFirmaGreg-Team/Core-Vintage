package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

public class BlockRockSmooth extends BlockRock {

    public BlockRockSmooth(RockBlockVariant blockVariant, RockType type) {
        super(blockVariant, type);
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, getBlockVariant());
        OreDictUtils.register(this, "stonePolished");
    }
}
