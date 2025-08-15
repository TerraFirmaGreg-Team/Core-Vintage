package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockCellarWall extends BaseBlock {

  public BlockCellarWall() {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .registryKey("cellar/wall")
      .hardness(3F)
      .resistance(25F)
    );
  }
}
