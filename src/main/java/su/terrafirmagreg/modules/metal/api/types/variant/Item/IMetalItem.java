package su.terrafirmagreg.modules.metal.api.types.variant.Item;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.api.lib.types.type.IType;
import su.terrafirmagreg.api.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IMetalItem extends IType<MetalType>, IVariant<MetalItemVariant>, IItemSettings {

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
