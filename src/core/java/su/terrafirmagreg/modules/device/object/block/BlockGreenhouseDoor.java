package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockDoor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockGreenhouseDoor extends BaseBlockDoor {

  public BlockGreenhouseDoor() {
    super(BlockSettings.of(Material.WOOD));

    getSettings()
      .registryKey("greenhouse/door")
      .addOreDict("greenhouse")
      .sound(SoundType.METAL);
  }
}
