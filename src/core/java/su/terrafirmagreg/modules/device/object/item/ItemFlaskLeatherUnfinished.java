package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemFlaskLeatherUnfinished extends BaseItem {

  public ItemFlaskLeatherUnfinished() {
    getSettings()
      .registryKey("flask/leather/unfinished")
      .size(Size.SMALL)
      .weight(Weight.LIGHT);
  }
}
