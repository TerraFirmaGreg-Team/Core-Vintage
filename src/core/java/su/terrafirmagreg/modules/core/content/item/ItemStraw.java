package su.terrafirmagreg.modules.core.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemStraw extends BaseItem {

  public ItemStraw() {
    super(ItemSettings.of()
      .registryKey("straw")
      .addOreDict("kindling", "straw")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT))
    );
  }
}
