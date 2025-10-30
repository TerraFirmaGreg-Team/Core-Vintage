package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockLadder;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodLadder extends BaseBlockLadder implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodLadder(WoodType type) {
    super(BlockSettings.of()
      .customResource(type.getResource("ladder"))
      .sound(SoundType.LADDER)
      .fireInfo(5, 20)
      .addOreDict("ladder")
      .addOreDict("ladder", "wood")
    );

    this.type = type;

  }
}
