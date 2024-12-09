package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemGlue extends BaseItem {

  public ItemGlue() {

    getSettings()
      .registryKey("core/glue")
      .size(Size.TINY)
      .weight(Weight.LIGHT)
      .oreDict("slimeball", "glue");
  }
}
