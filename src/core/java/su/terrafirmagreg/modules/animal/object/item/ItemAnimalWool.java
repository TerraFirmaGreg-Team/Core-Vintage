package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemAnimalWool extends BaseItem {

  public ItemAnimalWool() {
    super(ItemSettings.of()
      .registryKey("product/wool")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
    );
  }

}
