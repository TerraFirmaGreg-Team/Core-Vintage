package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.capability.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.capability.spi.Weight;

public class ItemStraw extends BaseItem {

  public ItemStraw() {

    getSettings()
      .registryKey("straw")
      .oreDict("kindling", "straw")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT));
  }
}
