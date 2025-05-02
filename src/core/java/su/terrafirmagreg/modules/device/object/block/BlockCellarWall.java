package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockCellarWall extends BaseBlock {

  public BlockCellarWall() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .registryKey("cellar/wall")
      .hardness(3F)
      .resistance(25F);
  }
}
