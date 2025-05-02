package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemFlaskMetalUnfinished extends BaseItem {

  public ItemFlaskMetalUnfinished() {
    getSettings()
      .registryKey("flask/metal/unfinished")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT));
  }
}
