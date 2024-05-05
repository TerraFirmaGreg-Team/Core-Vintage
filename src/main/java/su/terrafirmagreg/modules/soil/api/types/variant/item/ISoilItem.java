package su.terrafirmagreg.modules.soil.api.types.variant.item;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;


import org.jetbrains.annotations.NotNull;

public interface ISoilItem extends IType<SoilType>, IVariant<SoilItemVariant>, IAutoReg {

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @return расположение в реестре
     */
    @NotNull
    default String getName() {
        return String.format("soil/%s/%s", getVariant(), getType());
    }
}
