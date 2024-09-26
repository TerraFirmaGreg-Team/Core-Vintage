package su.terrafirmagreg.modules.wood.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemWoodMisc extends BaseItem {

  public ItemWoodMisc(String name, Size size, Weight weight, Object... oreNameParts) {

    getSettings()
      .registryKey("wood/" + name)
      .size(size)
      .weight(weight)
      .oreDict(oreNameParts);
  }

}
