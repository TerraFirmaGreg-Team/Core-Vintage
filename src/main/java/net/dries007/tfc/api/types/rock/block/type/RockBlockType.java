package net.dries007.tfc.api.types.rock.block.type;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
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
public class RockBlockType {
    private static final Set<RockBlockType> rockBlockTypes = new LinkedHashSet<>();

    @Nonnull
    private final String rockBlockTypeName;
    @Nonnull
    private final TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock> defaultFactory;
    @Nonnull
    private final Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock>>> rockBlockFactoryMap;

    /**
     * Создает экземпляр класса RockBlockType с указанными параметрами.
     *
     * @param rockBlockTypeName   Название типа блока породы.
     * @param defaultFactory      Функция-фабрика для создания блока породы по умолчанию.
     * @param rockBlockFactoryMap Карта вариантов блоков породы и соответствующих функций-фабрик.
     */
    private RockBlockType(@Nonnull String rockBlockTypeName, @Nonnull TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock> defaultFactory, @Nonnull Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock>>> rockBlockFactoryMap) {
        this.rockBlockTypeName = rockBlockTypeName;
        this.defaultFactory = defaultFactory;
        this.rockBlockFactoryMap = rockBlockFactoryMap;

        if (rockBlockTypeName.isEmpty()) {
            throw new RuntimeException(String.format("RockBlockType name must contain any character: [%s]", rockBlockTypeName));
        }

        if (!rockBlockTypes.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", rockBlockTypeName));
        }
    }

    /**
     * Возвращает набор всех типов блоков породы.
     *
     * @return Набор всех типов блоков породы.
     */
    public static Set<RockBlockType> getRockBlockTypes() {
        return rockBlockTypes;
    }

    /**
     * Возвращает строковое представление типа блока породы.
     *
     * @return Строковое представление типа блока породы.
     */
    @Override
    public String toString() {
        return rockBlockTypeName;
    }

    /**
     * Возвращает карту вариантов блоков породы и соответствующих функций-фабрик.
     *
     * @return Карта вариантов блоков породы и соответствующих функций-фабрик.
     */
    @Nonnull
    public Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock>>> getBlockFactoryMap() {
        return rockBlockFactoryMap;
    }

    /**
     * Внутренний класс Builder для создания экземпляров RockBlockType.
     */
    public static class Builder {
        @Nonnull
        private final String rockBlockTypeName;
        @Nonnull
        private final TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock> defaultFactory;
        @Nonnull
        private final Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock>>> rockBlockFactoryMap = new HashSet<>();

        /**
         * Создает экземпляр Builder с указанными параметрами.
         *
         * @param rockBlockTypeName Название типа блока породы.
         * @param defaultFactory    Функция-фабрика для создания блока породы по умолчанию.
         */
        public Builder(@Nonnull String rockBlockTypeName, @Nonnull TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock> defaultFactory) {
            this.rockBlockTypeName = rockBlockTypeName;
            this.defaultFactory = defaultFactory;
        }

        /**
         * Добавляет вариацию блока для текущего типа.
         *
         * @param rockBlockVariant  Вариант блока породы.
         * @param overridingFactory Функция-фабрика для создания блока породы для данного варианта.
         *                          Если null, то будет использоваться функция-фабрика по умолчанию.
         * @return Экземпляр Builder.
         */
        public Builder addBlockVariation(@Nonnull RockBlockVariant rockBlockVariant, @Nullable TriFunction<RockBlockType, RockBlockVariant, Rock, IRockBlock> overridingFactory) {
            rockBlockFactoryMap.add(new Pair<>(rockBlockVariant, overridingFactory));
            return this;
        }

        /**
         * Добавляет вариацию блока для текущего типа с использованием функции-фабрики по умолчанию.
         *
         * @param rockBlockVariant Вариант блока породы.
         * @return Экземпляр Builder.
         */
        public Builder addBlockVariation(@Nonnull RockBlockVariant rockBlockVariant) {
            rockBlockFactoryMap.add(new Pair<>(rockBlockVariant, defaultFactory));
            return this;
        }

        /**
         * Создает экземпляр RockBlockType с заданными параметрами.
         *
         * @return Экземпляр RockBlockType.
         */
        public RockBlockType build() {
            if (rockBlockFactoryMap.isEmpty()) {
                rockBlockFactoryMap.add(new Pair<>(null, defaultFactory));
            }
            return new RockBlockType(rockBlockTypeName, defaultFactory, rockBlockFactoryMap);
        }
    }
}
