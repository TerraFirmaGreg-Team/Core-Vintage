package su.terrafirmagreg.modules.core.object.item;


import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemStickBundle extends BaseItem {

  public ItemStickBundle() {
    super(ItemSettings.of()
      .registryKey("stick_bundle")
      .addOreDict("log_wood")
      .capability(CapabilityProviderSize.of(Size.VERY_LARGE, Weight.MEDIUM))
    );
  }
}
