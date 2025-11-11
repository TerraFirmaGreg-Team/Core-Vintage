package su.terrafirmagreg.modules.wood.content.item;


import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.framework.manager.content.provider.IProviderItemColor;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

import net.minecraft.client.renderer.color.IItemColor;

import lombok.Getter;

@Getter
public class ItemWoodLumber extends BaseItem implements IWoodEntry, IProviderItemColor {

  protected final WoodType type;

  public ItemWoodLumber(WoodType type) {
    super(ItemSettings.of()
      .customResource(type.getResource("lumber"))
      .addOreDict("lumber")
      .maxDamage(0)
    );

    this.type = type;
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
