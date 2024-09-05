package su.terrafirmagreg.modules.animal.objects.items;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalMisc extends BaseItem {

  public ItemAnimalMisc(String name, Size size, Weight weight, Object... oreNameParts) {

    getSettings()
        .registryKey("animal/" + name)
        .size(size)
        .weight(weight)
        .addOreDict(oreNameParts);
  }

}
