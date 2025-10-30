package su.terrafirmagreg.modules.device.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockDoor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCellarDoor extends BaseBlockDoor {

  public BlockCellarDoor() {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .sound(SoundType.WOOD)
      .hardness(2F)
      .fireInfo(5, 20)
    );
  }
}
