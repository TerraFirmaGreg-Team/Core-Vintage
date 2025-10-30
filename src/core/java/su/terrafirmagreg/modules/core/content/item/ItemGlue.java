package su.terrafirmagreg.modules.core.content.item;


import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

public class ItemGlue extends BaseItem {

  public ItemGlue() {
    super(ItemSettings.of()
      .capability(CapabilityProviderSize.of(Size.TINY, Weight.LIGHT))
      .addOreDict("slimeball")
    );
  }
}
