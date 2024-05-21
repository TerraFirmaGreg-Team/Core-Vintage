package su.terrafirmagreg.api.spi.item;

import su.terrafirmagreg.api.spi.block.BaseBlockSlab;

import net.minecraft.item.ItemSlab;

public class BaseItemSlab extends ItemSlab {

    public BaseItemSlab(BaseBlockSlab halfSlab, BaseBlockSlab doubleSlab) {
        super(halfSlab, halfSlab, doubleSlab);

    }

}
