package su.terrafirmagreg.modules.wood.object.item;


import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.item.spi.ItemWood;

import lombok.Getter;

@Getter
public class ItemWoodLumber extends ItemWood {


  public ItemWoodLumber(WoodType type) {
    super(type, "lumber");

    getSettings()
      .maxDamage(0);
  }
}
