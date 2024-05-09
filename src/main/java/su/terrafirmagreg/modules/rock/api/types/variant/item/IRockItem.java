package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.api.spi.item.ISettingsItem;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem extends IType<RockType>, IVariant<RockItemVariant>, ISettingsItem {

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @NotNull
    default String getName() {
        return String.format("rock/%s/%s", getVariant(), getType());
    }

}
