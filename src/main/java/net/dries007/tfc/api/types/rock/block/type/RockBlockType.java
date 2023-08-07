package net.dries007.tfc.api.types.rock.block.type;

import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.util.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.api.util.TriFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Базовый класс для типа каменного блока, хранит в себе имя,
 * дефолтный класс блока этого типа и Set из вариаций блоков с собственными классами для генерации блоков.
 * */
public class RockBlockType {

    private static final Set<RockBlockType> rockBlockTypes = new HashSet<>();

    @Nonnull
    private final String rockBlockTypeName;
    @Nonnull
    private final TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> defaultFactory;
    @Nonnull
    private final Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock>>> rockBlockVariantToSupplierMap;

    private RockBlockType(@Nonnull String rockBlockTypeName, @Nonnull TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> defaultFactory, @Nonnull Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock>>> rockBlockVariantToSupplierMap) {
        this.rockBlockTypeName = rockBlockTypeName;
        this.defaultFactory = defaultFactory;
        this.rockBlockVariantToSupplierMap = rockBlockVariantToSupplierMap;

        if (rockBlockTypeName.isEmpty()) {
            throw new RuntimeException(String.format("RockBlockType name must contain any character: [%s]", rockBlockTypeName));
        }

        if (!rockBlockTypes.add(this)) {
            throw new RuntimeException(String.format("RockType: [%s] already exists!", rockBlockTypeName));
        }

        var a = rockBlockTypes.toArray()[3];
    }

    public static Set<RockBlockType> getRockBlockTypes() {
        return rockBlockTypes;
    }

    @Override
    public String toString() {
        return rockBlockTypeName;
    }

    @Nonnull
    public TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> getDefaultFactory() {
        return defaultFactory;
    }

    @Nonnull
    public Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock>>> getRockBlockVariantToSupplierMap() {
        return rockBlockVariantToSupplierMap;
    }

    public static class Builder {
        @Nonnull
        private final String rockBlockTypeName;
        @Nonnull
        private final TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock> defaultFactory;
        @Nonnull
        private final Set<Pair<RockBlockVariant, TriFunction<RockBlockType, RockBlockVariant, RockType, IRockBlock>>> rockBlockVariantToSupplierMap = new HashSet<>();

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
            rockBlockVariantToSupplierMap.add(new Pair<>(rockBlockVariant, overridingFactory));
            return this;
        }

        /**
         * Метод добавляющий дополнительную вариацию блока для текущего типа.
         * При этом будет использоваться дефолтный класс генерации блока указанный в билдере.
         * @param rockBlockVariant сюда указывается вариация блока для текущего типа.
         * */
        public Builder addBlockVariation(@Nonnull RockBlockVariant rockBlockVariant) {
            rockBlockVariantToSupplierMap.add(new Pair<>(rockBlockVariant, null));
            return this;
        }

        public RockBlockType build() {
            return new RockBlockType(rockBlockTypeName, defaultFactory, rockBlockVariantToSupplierMap);
        }
    }
}
