package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockDoor;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodDoor extends BaseBlockDoor implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodDoor(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .customResource(type.getResource("door"))
      .sound(SoundType.WOOD)
      .fireInfo(5, 20)
      .addOreDict("door", "wood")
      .addOreDict("door", "wood", type)
    );

    this.type = type;

  }

}
