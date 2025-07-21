package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockLadder;
import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodLadder extends BaseBlockLadder implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodLadder(WoodType type) {
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("ladder"))
      .customResource(type.getResource("ladder"))
      .sound(SoundType.LADDER)
      .fireInfo(5, 20)
      .addOreDict("ladder")
      .addOreDict("ladder", "wood");
  }
}
