package su.terrafirmagreg.modules.device.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemFlaskLeatherBroken extends BaseItem {

  public ItemFlaskLeatherBroken() {
    super(ItemSettings.of()
      .registryKey("flask/leather/broken")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
    );
  }
}
