package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalSilkCloth extends BaseItem {

    public ItemAnimalSilkCloth() {

        getSettings()
                .registryKey("product/silk_cloth")
                .size(Size.SMALL)
                .weight(Weight.LIGHT)
                .oreDict("cloth_high_quality");
    }

}
