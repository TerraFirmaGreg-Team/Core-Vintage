package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalBladder extends BaseItem {

  public ItemAnimalBladder() {

    getSettings()
      .registryKey("animal/product/bladder")
      .size(Size.SMALL)
      .weight(Weight.LIGHT);
  }

}
