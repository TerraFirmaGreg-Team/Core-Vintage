package su.terrafirmagreg.modules.core.content.block;


import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockDebug extends BaseBlock {

  public BlockDebug() {
    super(BlockSettings.of()
      .material(Material.SPONGE)
      .registryKey("debug")
    );

  }

}
