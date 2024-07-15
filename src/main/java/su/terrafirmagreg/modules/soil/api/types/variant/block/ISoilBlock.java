package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.api.spi.block.IBlockSettings;
import su.terrafirmagreg.api.spi.types.type.IType;
import su.terrafirmagreg.api.spi.types.variant.IVariant;
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
    default String getRegistryKey() {
        return String.format("soil/%s/%s", getVariant(), getType());
    }

}
