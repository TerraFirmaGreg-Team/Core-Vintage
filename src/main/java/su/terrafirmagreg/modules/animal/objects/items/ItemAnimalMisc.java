package su.terrafirmagreg.modules.animal.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

public class ItemAnimalMisc extends BaseItem {

    public ItemAnimalMisc(String name, Size size, Weight weight, Object... oreNameParts) {

        getSettings()
                .registryKey("animal/" + name)
                .size(size)
                .weight(weight)
                .addOreDict(oreNameParts);
    }

}
