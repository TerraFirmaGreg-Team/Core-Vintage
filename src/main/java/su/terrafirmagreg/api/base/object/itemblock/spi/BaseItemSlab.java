package su.terrafirmagreg.api.base.object.itemblock.spi;


import su.terrafirmagreg.api.base.object.block.spi.BaseBlockSlab;

import net.minecraft.item.ItemSlab;

public class BaseItemSlab extends ItemSlab {

  public BaseItemSlab(BaseBlockSlab blockSlab) {
    super(blockSlab.getHalfSlab(), blockSlab.getHalfSlab(), blockSlab.getDoubleSlab());

  }
}
