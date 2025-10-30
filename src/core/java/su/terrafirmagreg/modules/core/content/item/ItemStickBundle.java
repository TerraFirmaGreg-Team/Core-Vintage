package su.terrafirmagreg.modules.core.content.item;


import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemStickBundle extends BaseItem {

  public ItemStickBundle() {
    super(ItemSettings.of()
      .addOreDict("log_wood")
      .capability(CapabilityProviderSize.of(Size.VERY_LARGE, Weight.MEDIUM))
    );
  }
}
