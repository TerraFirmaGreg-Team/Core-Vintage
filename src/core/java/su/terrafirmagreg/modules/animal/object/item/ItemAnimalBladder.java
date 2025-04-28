package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalBladder extends BaseItem {

  public ItemAnimalBladder() {

    getSettings()
      .registryKey("product/bladder")
      .oreDict("bladder")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT));
  }

}
