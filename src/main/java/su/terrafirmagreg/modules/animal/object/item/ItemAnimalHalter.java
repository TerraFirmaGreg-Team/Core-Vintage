package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalHalter extends BaseItem {

  public ItemAnimalHalter() {

    getSettings()
      .registryKey("animal/halter")
      .size(Size.SMALL)
      .weight(Weight.LIGHT)
      .oreDict("halter");
  }
}
