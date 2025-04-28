package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemWoodAsh extends BaseItem {

  public ItemWoodAsh() {

    getSettings()
      .registryKey("wood_ash")
      .oreDict("dustAsh")
      .capability(CapabilityProviderSize.of(Size.VERY_SMALL, Weight.VERY_LIGHT));
  }
}
