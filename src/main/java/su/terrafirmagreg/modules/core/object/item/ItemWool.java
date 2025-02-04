package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemWool extends BaseItem {

  public ItemWool() {

    getSettings()
      .registryKey("product/wool")
      .size(Size.SMALL)
      .weight(Weight.LIGHT);
  }

}
