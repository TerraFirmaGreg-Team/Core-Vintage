package su.terrafirmagreg.modules.core.object.item;


import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemMortar extends BaseItem {

  public ItemMortar() {
    super(ItemSettings.of()
      .registryKey("mortar")
      .capability(CapabilityProviderSize.of(Size.TINY, Weight.VERY_LIGHT))
    );
  }
}
