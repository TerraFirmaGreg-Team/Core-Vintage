package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemStraw extends BaseItem {

  public ItemStraw() {

    getSettings()
      .registryKey("core/straw")
      .size(Size.SMALL)
      .weight(Weight.VERY_LIGHT)
      .oreDict("kindling", "straw");
  }
}
