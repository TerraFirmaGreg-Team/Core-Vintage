package su.terrafirmagreg.modules.animal.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.base.item.BaseItem;

public class ItemAnimalMisc extends BaseItem {

    public ItemAnimalMisc(String name, Size size, Weight weight, Object... oreNameParts) {

        getSettings()
                .registryKey("animal/" + name)
                .size(size)
                .weight(weight)
                .addOreDict(oreNameParts);
    }

}
