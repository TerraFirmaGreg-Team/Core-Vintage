package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemFlaskMetalUnfinished extends BaseItem {

  public ItemFlaskMetalUnfinished() {
    getSettings()
      .registryKey("flask/metal/unfinished")
      .size(Size.SMALL)
      .weight(Weight.LIGHT);
  }
}
