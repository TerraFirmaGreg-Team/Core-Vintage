package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.api.spi.item.IItemSettings;
import su.terrafirmagreg.api.spi.types.type.IType;
import su.terrafirmagreg.api.spi.types.variant.IVariant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem extends IType<RockType>, IVariant<RockItemVariant>, IItemSettings {

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @NotNull
    default String getRegistryKey() {
        return String.format("rock/%s/%s", getVariant(), getType());
    }

}
