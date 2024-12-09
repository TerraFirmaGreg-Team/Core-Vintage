package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemWoodAsh extends BaseItem {

  public ItemWoodAsh() {

    getSettings()
      .registryKey("core/wood_ash")
      .size(Size.VERY_SMALL)
      .weight(Weight.VERY_LIGHT)
      .oreDict("dustAsh");
  }
}
