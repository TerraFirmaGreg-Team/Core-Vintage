package su.terrafirmagreg.modules.wood.content.item;


import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

import lombok.Getter;

@Getter
public class ItemWoodLumber extends BaseItem implements IWoodEntry {

  protected final WoodType type;

  public ItemWoodLumber(WoodType type) {
    super(ItemSettings.of()
      .customResource(type.getResource("lumber"))
      .addOreDict("lumber")
      .maxDamage(0)
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT))
      .itemColor((s, i) -> type.getColor())
    );

    this.type = type;
  }

}
