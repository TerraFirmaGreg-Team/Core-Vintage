package su.terrafirmagreg.modules.soil.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

import lombok.Getter;

@Getter
public class ItemSoilPile extends BaseItem implements ISoilEntry {

  protected final SoilType type;

  public ItemSoilPile(SoilType type) {
    super(ItemSettings.of()
      .registryKey(type.getRegistryKey("pile"))
      .addOreDict("pile")
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
    );

    this.type = type;
  }

}
