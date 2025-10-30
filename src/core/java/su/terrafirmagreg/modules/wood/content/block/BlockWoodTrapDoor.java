package su.terrafirmagreg.modules.wood.content.block;


import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockTrapDoor;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodTrapDoor extends BaseBlockTrapDoor implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodTrapDoor(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .customResource(type.getResource("trapdoor"))
      .sound(SoundType.WOOD)
      .hardness(0.5F)
      .fireInfo(5, 20)
      .addOreDict("trapdoor", "wood")
      .addOreDict("trapdoor", "wood", type)
    );

    this.type = type;
  }
}
