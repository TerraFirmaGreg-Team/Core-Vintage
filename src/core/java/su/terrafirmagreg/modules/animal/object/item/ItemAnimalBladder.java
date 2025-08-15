package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemAnimalBladder extends BaseItem {

  public ItemAnimalBladder() {
    super(ItemSettings.of()
      .registryKey("product/bladder")
      .addOreDict("bladder")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
    );
  }

}
