package su.terrafirmagreg.modules.core.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

public class ItemCoreMisc extends BaseItem {

    public ItemCoreMisc(String name, Size size, Weight weight, Object... oreNameParts) {
        super(Settings.of()
                .registryKey("core/" + name)
                .size(size)
                .weight(weight)
                .addOreDict(oreNameParts));
    }

}
