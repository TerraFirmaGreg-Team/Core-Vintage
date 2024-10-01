package su.terrafirmagreg.modules.wood.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

import lombok.Getter;

@Getter
public class ItemWoodLumber extends BaseItem implements IWoodItem {

  protected final WoodItemVariant variant;
  protected final WoodType type;

  public ItemWoodLumber(WoodItemVariant variant, WoodType type) {
    this.type = type;
    this.variant = variant;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(variant.getCustomResource())
      .size(Size.SMALL)
      .weight(Weight.VERY_LIGHT)
      .maxDamage(0)
      .oreDict(variant)
      .oreDict(variant, type);
  }
}
