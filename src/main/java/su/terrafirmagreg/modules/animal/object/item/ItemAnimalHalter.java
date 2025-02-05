package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalHalter extends BaseItem {

  public ItemAnimalHalter() {

    getSettings()
      .registryKey("halter")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT));
  }
}
