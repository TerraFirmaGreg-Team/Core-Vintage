package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.api.base.object.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemGlassShard extends BaseItem {

  public ItemGlassShard() {

    getSettings()
      .registryKey("shard/glass")
      .oreDict("shard")
      .capability(CapabilityProviderSize.of(Size.TINY, Weight.LIGHT));
  }
}
