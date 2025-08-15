package su.terrafirmagreg.modules.core.object.block;


import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;

import net.minecraft.block.material.Material;

public class BlockDebug extends BaseBlock {

  public BlockDebug() {
    super(BlockSettings.of()
      .material(Material.SPONGE)
      .registryKey("debug")
    );
    
  }

}
