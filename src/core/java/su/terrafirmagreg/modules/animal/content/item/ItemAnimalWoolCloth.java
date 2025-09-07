package su.terrafirmagreg.modules.animal.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemAnimalWoolCloth extends BaseItem {

  public ItemAnimalWoolCloth() {
    super(ItemSettings.of()
      .registryKey("product/wool_cloth")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
      .addOreDict("cloth_high_quality")
    );
  }

}
