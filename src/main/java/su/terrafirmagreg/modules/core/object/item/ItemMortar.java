package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemMortar extends BaseItem {

  public ItemMortar() {

    getSettings()
      .registryKey("core/mortar")
      .size(Size.TINY)
      .weight(Weight.VERY_LIGHT)
      .oreDict("mortar");
  }
}
