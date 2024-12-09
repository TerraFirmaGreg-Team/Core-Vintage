package su.terrafirmagreg.modules.animal.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemAnimalSilkCloth extends BaseItem {

  public ItemAnimalSilkCloth() {

    getSettings()
      .registryKey("animal/product/silk_cloth")
      .size(Size.SMALL)
      .weight(Weight.LIGHT)
      .oreDict("cloth_high_quality");
  }

}
