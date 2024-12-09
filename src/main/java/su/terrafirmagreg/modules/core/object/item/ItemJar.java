package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemJar extends BaseItem {

  public ItemJar() {

    getSettings()
      .registryKey("core/jar")
      .size(Size.VERY_SMALL)
      .weight(Weight.VERY_LIGHT);
  }
}
