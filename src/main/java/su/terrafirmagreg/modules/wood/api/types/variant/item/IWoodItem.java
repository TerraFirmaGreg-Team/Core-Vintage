package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.api.library.types.variant.item.IVariantItem;
import su.terrafirmagreg.api.registry.provider.IProviderItemColor;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.client.renderer.color.IItemColor;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface IWoodItem extends IVariantItem<WoodItemVariant, WoodType>, IItemSettings, IProviderItemColor {

  @Override
  default IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
