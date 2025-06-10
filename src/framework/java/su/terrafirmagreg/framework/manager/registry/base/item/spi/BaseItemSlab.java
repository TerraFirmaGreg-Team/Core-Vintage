package su.terrafirmagreg.framework.manager.registry.base.item.spi;


import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockSlab;

import net.minecraft.item.ItemSlab;

public class BaseItemSlab extends ItemSlab {

  public BaseItemSlab(BaseBlockSlab blockSlab) {
    super(blockSlab.getHalfSlab(), blockSlab.getHalfSlab(), blockSlab.getDoubleSlab());

  }
}
