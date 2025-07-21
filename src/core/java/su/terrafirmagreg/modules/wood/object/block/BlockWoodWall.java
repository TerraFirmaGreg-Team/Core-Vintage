package su.terrafirmagreg.modules.wood.object.block;


import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockWall;
import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodWall extends BaseBlockWall implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodWall(WoodType type) {
    super(BlocksWood.PLANKS.get(type));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("planks/wall"))
      .customResource(type.getResource("planks/wall"))
      .sound(SoundType.WOOD)
      .fireInfo(5, 20)
      .addOreDict("wall", "wood")
      .addOreDict("wall", "wood", type);
  }

}
