package su.terrafirmagreg.api.spi.itemblock;

import su.terrafirmagreg.api.spi.block.BaseBlockSlab;
import su.terrafirmagreg.api.spi.block.ISettingsBlock;

import net.minecraft.item.ItemSlab;


import lombok.Getter;

@Getter
public class BaseItemSlab extends ItemSlab {

    private final ISettingsBlock.Settings settings;

    public BaseItemSlab(BaseBlockSlab halfSlab, BaseBlockSlab doubleSlab) {
        super(halfSlab, halfSlab, doubleSlab);

        this.settings = ISettingsBlock.Settings.copy(halfSlab);

    }

}
