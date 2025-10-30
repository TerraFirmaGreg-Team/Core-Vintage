package su.terrafirmagreg.modules.core.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemGlassShard extends BaseItem {

  public ItemGlassShard() {
    super(ItemSettings.of()
      .addOreDict("shard")
      .capability(CapabilityProviderSize.of(Size.TINY, Weight.LIGHT))
    );
  }
}
