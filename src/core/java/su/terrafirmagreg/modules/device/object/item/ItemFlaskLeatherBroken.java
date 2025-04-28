package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemFlaskLeatherBroken extends BaseItem {

  public ItemFlaskLeatherBroken() {
    getSettings()
      .registryKey("flask/leather/broken")
      .size(Size.SMALL)
      .weight(Weight.LIGHT);
  }
}
