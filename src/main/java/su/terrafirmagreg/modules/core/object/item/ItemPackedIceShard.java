package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemPackedIceShard extends BaseItem {

  public ItemPackedIceShard() {

    getSettings()
      .registryKey("core/packed_ice_shard")
      .size(Size.TINY)
      .weight(Weight.LIGHT);
  }
}
