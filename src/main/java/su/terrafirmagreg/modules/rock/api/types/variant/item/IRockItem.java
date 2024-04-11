package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.modules.rock.api.types.type.IRockType;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem extends IRockType, IAutoReg {

    /**
     * Возвращает вариант предмета.
     *
     * @return Вариант предмета.
     */
    @NotNull
    RockItemVariant getItemVariant();

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @NotNull
    default String getName() {
        return String.format("rock/%s/%s", getItemVariant(), getType());
    }

}
