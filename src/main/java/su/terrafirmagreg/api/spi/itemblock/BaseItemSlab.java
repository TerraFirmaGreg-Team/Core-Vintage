package su.terrafirmagreg.api.spi.itemblock;

import su.terrafirmagreg.api.spi.block.BaseBlockSlab;

import net.minecraft.item.ItemSlab;


import lombok.Getter;

@Getter
public class BaseItemSlab extends ItemSlab {

    public BaseItemSlab(BaseBlockSlab halfSlab, BaseBlockSlab doubleSlab) {
        super(halfSlab, halfSlab, doubleSlab);

    }

}
