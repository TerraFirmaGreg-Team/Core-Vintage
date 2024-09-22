package su.terrafirmagreg.modules.wood.object.item;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;


import lombok.Getter;

@Getter
public class ItemWoodWheel extends BaseItem implements IWoodItem {

  protected final WoodItemVariant variant;
  protected final WoodType type;

  public ItemWoodWheel(WoodItemVariant variant, WoodType type) {
    this.variant = variant;
    this.type = type;

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .customResource(variant.getCustomResource())
            .weight(Weight.HEAVY)
            .size(Size.NORMAL)
            .oreDict(variant)
            .oreDict(variant, type);
  }

}
