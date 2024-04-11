package su.terrafirmagreg.modules.soil.api.types.variant.item;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.modules.soil.api.types.type.ISoilType;


import org.jetbrains.annotations.NotNull;

public interface ISoilItem extends ISoilType, IAutoReg {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @NotNull
    SoilItemVariant getItemVariant();

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @return расположение в реестре
     */
    @NotNull
    default String getName() {
        return String.format("soil/%s/%s", getItemVariant(), getType());
    }
}
