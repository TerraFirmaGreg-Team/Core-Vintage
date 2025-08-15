package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockDoor;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodDoor extends BaseBlockDoor implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodDoor(WoodType type) {
    super(BlockSettings.of(Material.WOOD));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("door"))
      .customResource(type.getResource("door"))
      .sound(SoundType.WOOD)
      .fireInfo(5, 20)
      .addOreDict("door", "wood")
      .addOreDict("door", "wood", type);
  }

}
