package su.terrafirmagreg.modules.soil.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.ISoilItem;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

import lombok.Getter;

@Getter
public abstract class ItemSoil extends BaseItem implements ISoilItem {

  protected final SoilItemVariant variant;
  protected final SoilType type;

  public ItemSoil(SoilItemVariant variant, SoilType type) {
    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .weight(Weight.LIGHT)
      .size(Size.SMALL)
      .oreDict(variant, type)
      .oreDict(variant);
  }

}
