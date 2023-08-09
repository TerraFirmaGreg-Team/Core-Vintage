package net.dries007.tfc.api.types.rock.variant;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Класс, представляющий тип блока породы.
 */
public class RockBlockVariant {
    private static final Set<RockBlockVariant> ROCK_BLOCK_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;

    private final float baseHardness;

    @Nonnull
    private final BiFunction<RockBlockVariant, RockType, IRockBlock> factory;

    /**
     * Создает экземпляр класса RockType с указанными параметрами.
     *
     * @param name    Название типа блока породы.
     * @param factory Функция-фабрика для создания блока породы по умолчанию.
     */
    public RockBlockVariant(@Nonnull String name, float baseHardness, @Nonnull BiFunction<RockBlockVariant, RockType, IRockBlock> factory) {
        this.name = name;
        this.baseHardness = baseHardness;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("RockType name must contain any character: [%s]", name));
        }

        if (!ROCK_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех типов блоков породы.
     *
     * @return Набор всех типов блоков породы.
     */
    public static Set<RockBlockVariant> getAllRockTypes() {
        return ROCK_BLOCK_VARIANTS;
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

    public String name() {
        return name;
    }

    public float getBaseHardness() {
        return baseHardness;
    }

    /**
     * Возвращает карту вариантов блоков породы и соответствующих функций-фабрик.
     *
     * @return Карта вариантов блоков породы и соответствующих функций-фабрик.
     */
    @Nonnull
    public IRockBlock applyToFactory(RockType rockType) {
        return factory.apply(this, rockType);
    }
}
