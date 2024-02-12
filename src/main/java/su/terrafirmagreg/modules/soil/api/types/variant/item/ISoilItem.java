package su.terrafirmagreg.modules.soil.api.types.variant.item;

import com.ibm.icu.impl.URLHandler;
import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoRegistry;
import su.terrafirmagreg.api.registry.IHasModel;
import su.terrafirmagreg.modules.soil.api.types.type.ISoilType;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface ISoilItem extends ISoilType, IItemSize, IAutoRegistry, IHasModel {

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
