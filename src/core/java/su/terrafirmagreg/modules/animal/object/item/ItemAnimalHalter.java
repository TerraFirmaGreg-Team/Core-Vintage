package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemAnimalHalter extends BaseItem {

  public ItemAnimalHalter() {

    getSettings()
      .registryKey("halter")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT));
  }
}
