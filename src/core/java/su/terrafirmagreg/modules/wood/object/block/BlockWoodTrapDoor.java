package su.terrafirmagreg.modules.wood.object.block;


import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockTrapDoor;
import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodTrapDoor extends BaseBlockTrapDoor implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodTrapDoor(WoodType type) {
    super(Settings.of(Material.WOOD));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("trapdoor"))
      .customResource(type.getResource("trapdoor"))
      .sound(SoundType.WOOD)
      .hardness(0.5F)
      .fireInfo(5, 20)
      .oreDict("trapdoor", "wood")
      .oreDict("trapdoor", "wood", type);

  }
}
