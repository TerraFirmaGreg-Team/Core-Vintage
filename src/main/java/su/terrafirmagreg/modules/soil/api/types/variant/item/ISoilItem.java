package su.terrafirmagreg.modules.soil.api.types.variant.item;

import su.terrafirmagreg.api.spi.item.IItemSettings;
import su.terrafirmagreg.api.spi.types.type.IType;
import su.terrafirmagreg.api.spi.types.variant.IVariant;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;


import org.jetbrains.annotations.NotNull;

public interface ISoilItem extends IType<SoilType>, IVariant<SoilItemVariant>, IItemSettings {

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @return расположение в реестре
     */
    @NotNull
    default String getRegistryKey() {
        return String.format("soil/%s/%s", getVariant(), getType());
    }
}
