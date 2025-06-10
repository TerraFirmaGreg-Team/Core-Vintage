package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemWoodAsh extends BaseItem {

  public ItemWoodAsh() {

    getSettings()
      .registryKey("wood_ash")
      .oreDict("dustAsh")
      .capability(CapabilityProviderSize.of(Size.VERY_SMALL, Weight.VERY_LIGHT));
  }
}
