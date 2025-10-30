package su.terrafirmagreg.modules.device.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemFlaskUnfinished extends BaseItem {

  public ItemFlaskUnfinished() {
    super(ItemSettings.of()
      .registryKey("flask/metal/unfinished")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
    );
  }
}
