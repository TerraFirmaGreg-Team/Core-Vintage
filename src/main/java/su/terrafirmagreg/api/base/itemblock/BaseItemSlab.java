package su.terrafirmagreg.api.base.itemblock;

import su.terrafirmagreg.api.base.block.BaseBlockSlab;

import net.minecraft.item.ItemSlab;

public class BaseItemSlab extends ItemSlab {

  public BaseItemSlab(BaseBlockSlab halfSlab, BaseBlockSlab doubleSlab) {
    super(halfSlab, halfSlab, doubleSlab);

  }

}
