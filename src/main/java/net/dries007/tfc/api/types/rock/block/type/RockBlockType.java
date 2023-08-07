package net.dries007.tfc.api.types.rock.block.type;

import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.TriFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Базовый класс для типа каменного блока, хранит в себе имя,
 * дефолтный класс блока этого типа и Set из вариаций блоков с собственными классами для генерации блоков.
 * */
public class RockBlockType {

    private static final Set<RockBlockType> rockBlockTypes = new LinkedHashSet<>();

    @Nonnull
    private final String rockBlockTypeName;
    @Nonnull
    private final TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> defaultFactory;
    @Nonnull
    private final Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock>>> rockBlockFactoryMap;

    private RockBlockType(@Nonnull String rockBlockTypeName, @Nonnull TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> defaultFactory, @Nonnull Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock>>> rockBlockFactoryMap) {
        this.rockBlockTypeName = rockBlockTypeName;
        this.defaultFactory = defaultFactory;
        this.rockBlockFactoryMap = rockBlockFactoryMap;

        if (rockBlockTypeName.isEmpty()) {
            throw new RuntimeException(String.format("RockBlockType name must contain any character: [%s]", rockBlockTypeName));
        }

        if (!rockBlockTypes.add(this)) {
            throw new RuntimeException(String.format("RockType: [%s] already exists!", rockBlockTypeName));
        }
    }

    public static Set<RockBlockType> getRockBlockTypes() {
        return rockBlockTypes;
    }

    @Override
    public String toString() {
        return rockBlockTypeName;
    }

    @Nonnull
    public Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock>>> getBlockFactoryMap() {
        return rockBlockFactoryMap;
    }

    public static class Builder {
        @Nonnull
        private final String rockBlockTypeName;
        @Nonnull
        private final TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> defaultFactory;
        @Nonnull
        private final Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock>>> rockBlockFactoryMap = new HashSet<>();

        public Builder(@Nonnull String rockBlockTypeName, @Nonnull TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> defaultFactory) {
            this.rockBlockTypeName = rockBlockTypeName;
            this.defaultFactory = defaultFactory;
        }

        /**
         * Метод добавляющий дополнительную вариацию блока для текущего типа.
         * @param overridingFactory сюда можно указать класс блока, которым вы хотите
         *                          заменить дефолтный класс блока при его создании.
         * @param rockBlockVariant сюда указывается вариация блока для текущего типа.
         * */
        public Builder addBlockVariation(@Nonnull RockBlockVariant rockBlockVariant, @Nullable TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> overridingFactory) {
            rockBlockFactoryMap.add(new Pair<>(rockBlockVariant, overridingFactory));
            return this;
        }

        /**
         * Метод добавляющий дополнительную вариацию блока для текущего типа.
         * При этом будет использоваться дефолтный класс генерации блока указанный в билдере.
         * @param rockBlockVariant сюда указывается вариация блока для текущего типа.
         * */
        public Builder addBlockVariation(@Nonnull RockBlockVariant rockBlockVariant) {
            rockBlockFactoryMap.add(new Pair<>(rockBlockVariant, defaultFactory));
            return this;
        }

        public RockBlockType build() {
            if (rockBlockFactoryMap.isEmpty()) {
                rockBlockFactoryMap.add(new Pair<>(null, defaultFactory));
            }
            return new RockBlockType(rockBlockTypeName, defaultFactory, rockBlockFactoryMap);
        }
    }
}
