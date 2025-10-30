package su.terrafirmagreg.modules.device.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockCellarWall extends BaseBlock {

  public BlockCellarWall() {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .hardness(3F)
      .resistance(25F)
    );
  }
}
