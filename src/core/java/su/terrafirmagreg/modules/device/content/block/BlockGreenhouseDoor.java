package su.terrafirmagreg.modules.device.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockDoor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockGreenhouseDoor extends BaseBlockDoor {

  public BlockGreenhouseDoor() {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .registryKey("greenhouse/door")
      .addOreDict("greenhouse")
      .sound(SoundType.METAL)
    );
  }
}
