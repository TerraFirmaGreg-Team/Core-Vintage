package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemDeviceMisc extends BaseItem {

  public ItemDeviceMisc(String name, Size size, Weight weight, Object... oreNameParts) {

    getSettings()
            .registryKey("device/" + name)
            .size(size)
            .weight(weight)
            .addOreDict(oreNameParts);
  }

}
