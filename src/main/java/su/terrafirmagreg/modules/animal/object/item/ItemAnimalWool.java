package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalWool extends BaseItem {

  public ItemAnimalWool() {

    getSettings()
      .registryKey("animal/product/wool")
      .size(Size.SMALL)
      .weight(Weight.LIGHT);
  }

}
