package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

public class ItemDeviceMisc extends BaseItem {

    public ItemDeviceMisc(String name, Size size, Weight weight, Object... oreNameParts) {
        
        getSettings()
                .registryKey("device/" + name)
                .size(size)
                .weight(weight)
                .addOreDict(oreNameParts);
    }

}
