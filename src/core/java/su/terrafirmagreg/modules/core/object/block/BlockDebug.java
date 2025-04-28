package su.terrafirmagreg.modules.core.object.block;


import su.terrafirmagreg.api.base.object.block.spi.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockDebug extends BaseBlock {

  public BlockDebug() {
    super(Settings.of(Material.SPONGE));

    getSettings()
      .registryKey("debug");
  }

}
