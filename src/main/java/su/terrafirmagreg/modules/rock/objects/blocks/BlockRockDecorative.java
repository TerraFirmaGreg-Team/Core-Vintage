package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlock;

import net.minecraft.block.SoundType;

public abstract class BlockRockDecorative extends BaseBlock {

    public BlockRockDecorative(Settings settings) {
        super(settings
                .soundType(SoundType.STONE)
                .hardness(1.0F));
    }
}
