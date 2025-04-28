package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemFlaskMetalBroken extends BaseItem {

  public ItemFlaskMetalBroken() {
    getSettings()
      .registryKey("flask/metal/broken")
      .size(Size.SMALL)
      .weight(Weight.LIGHT);
  }
}
