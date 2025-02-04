package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalBladder extends BaseItem {

  public ItemAnimalBladder() {

    getSettings()
      .registryKey("product/bladder")
      .size(Size.SMALL)
      .weight(Weight.LIGHT);
  }

}
