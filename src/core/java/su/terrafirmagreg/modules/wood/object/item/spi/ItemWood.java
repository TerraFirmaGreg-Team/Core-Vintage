package su.terrafirmagreg.modules.wood.object.item.spi;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItem;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderItemColor;
import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.client.renderer.color.IItemColor;

import lombok.Getter;

@Getter
public abstract class ItemWood extends BaseItem implements IWoodEntry, IProviderItemColor {

  protected final WoodType type;

  public ItemWood(WoodType type, String variant) {
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(type.getResource(variant))
      .addOreDict(variant);
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
