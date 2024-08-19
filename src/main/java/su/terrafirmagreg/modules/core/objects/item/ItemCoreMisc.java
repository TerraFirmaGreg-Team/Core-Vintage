package su.terrafirmagreg.modules.core.objects.item;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.base.item.BaseItem;

public class ItemCoreMisc extends BaseItem {

    public ItemCoreMisc(String name, Size size, Weight weight, Object... oreNameParts) {

        getSettings()
                .registryKey("core/" + name)
                .size(size)
                .weight(weight)
                .addOreDict(oreNameParts);
    }

}
