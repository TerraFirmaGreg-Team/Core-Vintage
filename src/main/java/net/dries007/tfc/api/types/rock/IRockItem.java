package net.dries007.tfc.api.types.rock;

import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.dries007.tfc.api.types.rock.type.RockType;

import javax.annotation.Nonnull;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem {
    /**
     * Возвращает тип породы.
     *
     * @return Тип породы.
     */
    @Nonnull
    RockType getRock();

    /**
     * Возвращает категорию породы.
     *
     * @return Категория породы.
     */
    @Nonnull
    default RockCategory getRockCategory() {
        return getRock().getRockCategory();
    }
}
