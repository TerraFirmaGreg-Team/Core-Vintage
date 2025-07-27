package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemAnimalWoolYarn extends BaseItem {

  public ItemAnimalWoolYarn() {

    getSettings()
      .registryKey("product/wool_yarn")
      .capability(CapabilityProviderSize.of(Size.VERY_SMALL, Weight.VERY_LIGHT))
      .addOreDict("string");
  }

}
