package su.terrafirmagreg.modules.animal.objects.items;

import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.spi.item.BaseItem;

public class ItemAnimalMisc extends BaseItem {

    public ItemAnimalMisc(String name, Size size, Weight weight, Object... oreNameParts) {

        getSettings()
                .registryKey("animal/" + name)
                .size(size)
                .weight(weight)
                .addOreDict(oreNameParts);
    }

}
