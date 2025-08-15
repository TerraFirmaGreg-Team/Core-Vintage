package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockDoor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCellarDoor extends BaseBlockDoor {

  public BlockCellarDoor() {
    super(BlockSettings.of(Material.WOOD));

    getSettings()
      .registryKey("cellar/door")
      .sound(SoundType.WOOD)
      .hardness(2F)
      .fireInfo(5, 20);
  }
}
