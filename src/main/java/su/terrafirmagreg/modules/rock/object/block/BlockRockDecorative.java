package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;

import net.minecraft.block.SoundType;

public abstract class BlockRockDecorative extends BaseBlock {

  public BlockRockDecorative(Settings settings) {
    super(settings
            .sound(SoundType.STONE)
            .hardness(1.0F));
  }
}
