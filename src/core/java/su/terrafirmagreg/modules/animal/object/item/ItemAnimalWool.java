package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.capability.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.capability.spi.Weight;

public class ItemAnimalWool extends BaseItem {

  public ItemAnimalWool() {

    getSettings()
      .registryKey("product/wool")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT));
  }

}
