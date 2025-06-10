package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemAnimalWoolCloth extends BaseItem {

  public ItemAnimalWoolCloth() {

    getSettings()
      .registryKey("product/wool_cloth")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
      .oreDict("cloth_high_quality");
  }

}
