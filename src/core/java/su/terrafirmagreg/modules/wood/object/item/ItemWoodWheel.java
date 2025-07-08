package su.terrafirmagreg.modules.wood.object.item;


import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.item.spi.ItemWood;

import lombok.Getter;

@Getter
public class ItemWoodWheel extends ItemWood {


  public ItemWoodWheel(WoodType type) {
    super(type, "wheel");

    getSettings()
      .capability(CapabilityProviderSize.of(Size.NORMAL, Weight.HEAVY));
  }

}
