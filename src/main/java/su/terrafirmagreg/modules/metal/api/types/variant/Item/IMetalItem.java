package su.terrafirmagreg.modules.metal.api.types.variant.Item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.data.lib.types.variant.item.IVariantItem;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;

import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IMetalItem extends IVariantItem<MetalItemVariant, MetalType>, IItemSettings {

  /**
   * Возвращает расположение в реестре для данного подтипа предмета.
   *
   * @return Расположение в реестре
   */
  @NotNull
  default String getRegistryKey() {
    return String.format("metal/%s/%s", getVariant(), getType());
  }

}
