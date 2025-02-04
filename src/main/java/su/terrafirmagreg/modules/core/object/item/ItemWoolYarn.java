package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemWoolYarn extends BaseItem {

  public ItemWoolYarn() {

    getSettings()
      .registryKey("product/wool_yarn")
      .size(Size.VERY_SMALL)
      .weight(Weight.VERY_LIGHT)
      .oreDict("string");
  }

}
