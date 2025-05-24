package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlockDoor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockGreenhouseDoor extends BaseBlockDoor {

  public BlockGreenhouseDoor() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .registryKey("greenhouse/door")
      .oreDict("greenhouse")
      .sound(SoundType.METAL);
  }
}
