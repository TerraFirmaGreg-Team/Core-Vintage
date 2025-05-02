package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.capability.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.capability.spi.Weight;

public class ItemFlaskMetalBroken extends BaseItem {

  public ItemFlaskMetalBroken() {
    getSettings()
      .registryKey("flask/metal/broken")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT));
  }
}
