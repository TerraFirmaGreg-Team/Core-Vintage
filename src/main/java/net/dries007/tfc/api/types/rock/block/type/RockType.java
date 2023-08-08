package net.dries007.tfc.api.types.rock.block.type;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.block.variant.RockVariant;
import net.dries007.tfc.api.types.rock.type.Rock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.TriFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс, представляющий тип блока породы.
 */
public class RockType {
    private static final Set<RockType> ROCK_BLOCK_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final TriFunction<RockType, RockVariant, Rock, IRockBlock> defaultFactory;
    @Nonnull
    private final Set<Pair<RockVariant, TriFunction<RockType, RockVariant, Rock, IRockBlock>>> rockBlockFactoryMap;

    /**
     * Создает экземпляр класса RockType с указанными параметрами.
     *
     * @param name                Название типа блока породы.
     * @param defaultFactory      Функция-фабрика для создания блока породы по умолчанию.
     * @param rockBlockFactoryMap Карта вариантов блоков породы и соответствующих функций-фабрик.
     */
    private RockType(@Nonnull String name, @Nonnull TriFunction<RockType, RockVariant, Rock, IRockBlock> defaultFactory, @Nonnull Set<Pair<RockVariant, TriFunction<RockType, RockVariant, Rock, IRockBlock>>> rockBlockFactoryMap) {
        this.name = name;
        this.defaultFactory = defaultFactory;
        this.rockBlockFactoryMap = rockBlockFactoryMap;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("RockType name must contain any character: [%s]", name));
        }

        if (!ROCK_BLOCK_TYPES.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех типов блоков породы.
     *
     * @return Набор всех типов блоков породы.
     */
    public static Set<RockType> getAllRockTypes() {
        return ROCK_BLOCK_TYPES;
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
     * Возвращает карту вариантов блоков породы и соответствующих функций-фабрик.
     *
     * @return Карта вариантов блоков породы и соответствующих функций-фабрик.
     */
    @Nonnull
    public Set<Pair<RockVariant, TriFunction<RockType, RockVariant, Rock, IRockBlock>>> getBlockFactoryMap() {
        return rockBlockFactoryMap;
    }

    /**
     * Внутренний класс Builder для создания экземпляров RockType.
     */
    public static class Builder {
        @Nonnull
        private final String name;
        @Nonnull
        private final TriFunction<RockType, RockVariant, Rock, IRockBlock> defaultFactory;
        @Nonnull
        private final Set<Pair<RockVariant, TriFunction<RockType, RockVariant, Rock, IRockBlock>>> rockBlockFactoryMap = new HashSet<>();

        /**
         * Создает экземпляр Builder с указанными параметрами.
         *
         * @param name           Название типа блока породы.
         * @param defaultFactory Функция-фабрика для создания блока породы по умолчанию.
         */
        public Builder(@Nonnull String name, @Nonnull TriFunction<RockType, RockVariant, Rock, IRockBlock> defaultFactory) {
            this.name = name;
            this.defaultFactory = defaultFactory;
        }

        /**
         * Добавляет вариацию блока для текущего типа.
         *
         * @param rockVariant       Вариант блока породы.
         * @param overridingFactory Функция-фабрика для создания блока породы для данного варианта.
         *                          Если null, то будет использоваться функция-фабрика по умолчанию.
         * @return Экземпляр Builder.
         */
        public Builder addBlockVariation(@Nonnull RockVariant rockVariant, @Nullable TriFunction<RockType, RockVariant, Rock, IRockBlock> overridingFactory) {
            rockBlockFactoryMap.add(new Pair<>(rockVariant, overridingFactory));
            return this;
        }

        /**
         * Добавляет вариацию блока для текущего типа с использованием функции-фабрики по умолчанию.
         *
         * @param rockVariant Вариант блока породы.
         * @return Экземпляр Builder.
         */
        public Builder addBlockVariation(@Nonnull RockVariant rockVariant) {
            rockBlockFactoryMap.add(new Pair<>(rockVariant, defaultFactory));
            return this;
        }

        /**
         * Создает экземпляр RockType с заданными параметрами.
         *
         * @return Экземпляр RockType.
         */
        public RockType build() {
            if (rockBlockFactoryMap.isEmpty()) {
                rockBlockFactoryMap.add(new Pair<>(null, defaultFactory));
            }
            return new RockType(name, defaultFactory, rockBlockFactoryMap);
        }
    }
}
