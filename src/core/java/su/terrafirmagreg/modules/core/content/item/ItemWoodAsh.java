package su.terrafirmagreg.modules.core.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemWoodAsh extends BaseItem {

  public ItemWoodAsh() {
    super(ItemSettings.of()
      .registryKey("wood_ash")
      .addOreDict("dustAsh")
      .capability(CapabilityProviderSize.of(Size.VERY_SMALL, Weight.VERY_LIGHT))
    );
  }
}
