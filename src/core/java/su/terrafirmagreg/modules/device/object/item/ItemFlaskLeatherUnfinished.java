package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemFlaskLeatherUnfinished extends BaseItem {

  public ItemFlaskLeatherUnfinished() {
    super(ItemSettings.of()
      .registryKey("flask/leather/unfinished")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
    );
  }
}
