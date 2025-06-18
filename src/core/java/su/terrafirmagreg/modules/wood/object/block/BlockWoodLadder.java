package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockLadder;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.feature.woodtype.spi.IWoodBlock;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodLadder extends BaseBlockLadder implements IWoodBlock {

  protected final WoodType type;

  public BlockWoodLadder(WoodType type) {
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("ladder"))
      .customResource(type.getResource("ladder"))
      .sound(SoundType.LADDER)
      .fireInfo(5, 20)
      .oreDict("ladder")
      .oreDict("ladder", "wood");
  }
}
