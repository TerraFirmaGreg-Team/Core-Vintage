package su.terrafirmagreg.modules.core.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemIceShard extends BaseItem {

  public ItemIceShard() {

    getSettings()
      .registryKey("shard/ice")
      .oreDict("shard")
      .capability(CapabilityProviderSize.of(Size.TINY, Weight.LIGHT));
  }
}
