package su.terrafirmagreg.modules.wood.objects.items;

import su.terrafirmagreg.api.base.item.BaseItem;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;

import net.minecraft.client.renderer.color.IItemColor;


import lombok.Getter;

@Getter
public class ItemWoodLumber extends BaseItem implements IWoodItem {

  private final WoodItemVariant variant;
  private final WoodType type;

  public ItemWoodLumber(WoodItemVariant variant, WoodType type) {
    this.type = type;
    this.variant = variant;

    getSettings()
        .size(Size.SMALL)
        .weight(Weight.VERY_LIGHT)
        .maxDamage(0)
        .addOreDict(variant)
        .addOreDict(variant, type);
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
