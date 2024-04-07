package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.modules.soil.api.types.type.ISoilType;

import net.dries007.tfc.api.capability.size.IItemSize;

import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок почвы.
 */
public interface ISoilBlock extends ISoilType, IAutoReg, IItemSize {

    /**
     * Возвращает вариант блока почвы.
     *
     * @return Вариант блока почвы.
     */
    @NotNull
    SoilBlockVariant getBlockVariant();

    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @NotNull
    @Override
    default String getName() {
        return String.format("soil/%s/%s", getBlockVariant(), getType());
    }
}
