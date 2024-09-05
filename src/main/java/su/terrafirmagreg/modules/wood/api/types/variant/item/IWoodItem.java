package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.api.registry.provider.IProviderItemColor;
import su.terrafirmagreg.api.registry.provider.IProviderModel;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.util.ResourceLocation;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface IWoodItem extends IType<WoodType>, IVariant<WoodItemVariant>, IItemSettings,
    IProviderModel, IProviderItemColor {

  /**
   * Возвращает расположение в реестре для данного подтипа предмета.
   *
   * @return Расположение в реестре
   */
  @NotNull
  default String getRegistryKey() {
    return String.format("wood/%s/%s", getVariant(), getType());
  }

  /**
   * Возвращает расположение ресурса для данного подтипа предмета.
   *
   * @return Расположение ресурса
   */
  default ResourceLocation getResourceLocation() {
    return ModUtils.resource(String.format("wood/%s", getVariant()));
  }
}
