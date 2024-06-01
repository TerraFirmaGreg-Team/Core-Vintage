package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;

import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;

public class ItemDeviceMisc extends BaseItem {

    public ItemDeviceMisc(String name, Size size, Weight weight, Object... oreNameParts) {

        getSettings()
                .registryKey("device/" + name)
                .size(size)
                .weight(weight)
                .addOreDict(oreNameParts);
    }

}
