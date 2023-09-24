package net.dries007.tfc.module.rock.api.variant.item;

import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.rock.api.type.RockType;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.module.rock.common.RockStorage.ROCK_ITEMS;

/**
 * Класс, представляющий тип блока породы.
 */
public class RockItemVariant {
    private static final Set<RockItemVariant> ROCK_ITEM_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;


    @Nonnull
    private final BiFunction<RockItemVariant, RockType, IRockItem> factory;

    /**
     * Создает экземпляр класса RockType с указанными параметрами.
     *
     * @param name    Название типа блока породы.
     * @param factory Функция-фабрика для создания блока породы по умолчанию.
     */
    public RockItemVariant(@Nonnull String name, @Nonnull BiFunction<RockItemVariant, RockType, IRockItem> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("MetalItemVariant name must contain any character: [%s]", name));
        }

        if (!ROCK_ITEM_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("MetalItemVariant: [%s] already exists!", name));
        }

        for (var type : RockType.getRockTypes()) {
            if (ROCK_ITEMS.put(new Pair<>(this, type), this.create(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
    }

    /**
     * Возвращает набор всех типов блоков породы.
     *
     * @return Набор всех типов блоков породы.
     */
    public static Set<RockItemVariant> getRockItemVariants() {
        return ROCK_ITEM_VARIANTS;
    }

    /**
     * Возвращает строковое представление типа блока породы.
     *
     * @return Строковое представление типа блока породы.
     */
    @Override
    public String toString() {
        return name;
    }


    /**
     * Применяет функцию-фабрику к типу блока породы и возвращает созданный блок породы.
     *
     * @param type Тип породы.
     * @return Созданный блок породы.
     */
    @Nonnull
    public IRockItem create(RockType type) {
        return factory.apply(this, type);
    }
}
