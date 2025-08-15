package su.terrafirmagreg.modules.wood.object.item;


import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderItemColor;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.client.renderer.color.IItemColor;

import lombok.Getter;

@Getter
public class ItemWoodLumber extends BaseItem implements IWoodEntry, IProviderItemColor {

  protected final WoodType type;

  public ItemWoodLumber(WoodType type) {
    super(ItemSettings.of()
      .registryKey(type.getRegistryKey("lumber"))
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
