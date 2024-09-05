package su.terrafirmagreg.modules.wood.objects.items;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemWoodMisc extends BaseItem {

  public ItemWoodMisc(String name, Size size, Weight weight, Object... oreNameParts) {

    getSettings()
        .registryKey("wood/" + name)
        .size(size)
        .weight(weight)
        .addOreDict(oreNameParts);
  }

}
