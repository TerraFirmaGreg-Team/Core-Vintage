package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemCoreMisc extends BaseItem {

  public ItemCoreMisc(String name, Size size, Weight weight, Object... oreNameParts) {

    getSettings()
            .registryKey("core/" + name)
            .size(size)
            .weight(weight)
            .oreDict(oreNameParts);
  }

}
