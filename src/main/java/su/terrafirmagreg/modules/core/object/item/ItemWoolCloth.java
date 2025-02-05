package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemWoolCloth extends BaseItem {

  public ItemWoolCloth() {

    getSettings()
      .registryKey("product/wool_cloth")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
      .oreDict("cloth_high_quality");
  }

}
