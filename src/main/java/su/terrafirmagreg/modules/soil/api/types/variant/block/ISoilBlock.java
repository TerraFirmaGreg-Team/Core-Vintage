package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.api.spi.block.IBlockSettings;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок почвы.
 */
public interface ISoilBlock extends IType<SoilType>, IVariant<SoilBlockVariant>, IBlockSettings {

    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @NotNull
    @Override
    default String getName() {
        return String.format("soil/%s/%s", getVariant(), getType());
    }

}
