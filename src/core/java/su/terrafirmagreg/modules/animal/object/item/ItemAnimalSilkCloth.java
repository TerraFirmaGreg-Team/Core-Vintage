package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemAnimalSilkCloth extends BaseItem {

  public ItemAnimalSilkCloth() {
    super(ItemSettings.of()
      .registryKey("product/silk_cloth")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
      .addOreDict("cloth_high_quality")
    );
  }

}
