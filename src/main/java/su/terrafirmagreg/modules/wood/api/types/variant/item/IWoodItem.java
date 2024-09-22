package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.api.registry.provider.IProviderItemColor;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.client.renderer.color.IItemColor;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface IWoodItem extends IType<WoodType>, IVariant<WoodItemVariant>,
        IItemSettings, IProviderItemColor {

  @Override
  default IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
