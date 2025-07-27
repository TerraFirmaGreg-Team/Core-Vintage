package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemStraw extends BaseItem {

  public ItemStraw() {

    getSettings()
      .registryKey("straw")
      .addOreDict("kindling", "straw")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
  }
}
