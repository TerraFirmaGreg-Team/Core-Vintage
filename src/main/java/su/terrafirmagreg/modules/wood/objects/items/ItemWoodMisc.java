package su.terrafirmagreg.modules.wood.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

public class ItemWoodMisc extends BaseItem {

    public ItemWoodMisc(String name, Size size, Weight weight, Object... oreNameParts) {

        getSettings()
                .registryKey("wood/" + name)
                .size(size)
                .weight(weight)
                .addOreDict(oreNameParts);
    }

}
