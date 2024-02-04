package su.terrafirmagreg.modules.soil.api.types.variant.block;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.IItemProvider;
import su.terrafirmagreg.modules.soil.api.types.type.ISoilType;

/**
 * Интерфейс, представляющий блок почвы.
 */
public interface ISoilBlockVariant extends ISoilType, IItemProvider, IItemSize {

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
    default String getName() {
        return String.format("soil/%s/%s", getBlockVariant(), getType());
    }
}
