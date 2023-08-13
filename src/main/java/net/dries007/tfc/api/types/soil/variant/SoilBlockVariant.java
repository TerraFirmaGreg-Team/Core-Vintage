package net.dries007.tfc.api.types.soil.variant;

import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.util.FallingBlockManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.types.soil.variant.SoilBlockVariants.*;

/**
 * Класс, представляющий вариант блока почвы.
 */
public class SoilBlockVariant {

    private static final Set<SoilBlockVariant> SOIL_BLOCK_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<SoilBlockVariant, SoilType, ISoilBlock> factory;
    @Nullable
    private final FallingBlockManager.Specification fallingSpecification;

    /**
     * Создает экземпляр класса SoilBlockVariant с указанными параметрами.
     *
     * @param name                 Имя варианта блока почвы.
     * @param factory              Фабричная функция для создания блока почвы.
     * @param fallingSpecification Спецификация падающего блока (может быть null).
     */
    public SoilBlockVariant(@Nonnull String name, @Nonnull BiFunction<SoilBlockVariant, SoilType, ISoilBlock> factory, @Nullable FallingBlockManager.Specification fallingSpecification) {
        this.name = name;
        this.factory = factory;
        this.fallingSpecification = fallingSpecification;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("SoilBlockVariant name must contain any character: [%s]", name));
        }

        if (!SOIL_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("SoilBlockVariant: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех вариантов блока почвы.
     *
     * @return Набор всех вариантов блока почвы.
     */
    public static Set<SoilBlockVariant> getSoilBlockVariants() {
        return SOIL_BLOCK_VARIANTS;
    }

    /**
     * Возвращает строковое представление варианта блока почвы.
     *
     * @return Строковое представление варианта блока почвы.
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает спецификацию падающего блока.
     *
     * @return Спецификация падающего блока (может быть null).
     */
    @Nullable
    public FallingBlockManager.Specification getFallingSpecification() {
        return fallingSpecification;
    }

    /**
     * Проверяет, может ли блок почвы падать.
     *
     * @return true, если блок почвы может падать, иначе false.
     */
    public boolean canFall() {
        return fallingSpecification != null;
    }

    /**
     * Возвращает вариант блока почвы без травы.
     *
     * @return Вариант блока почвы без травы.
     */
    public SoilBlockVariant getNonGrassVersion() {
        if (this == GRASS || this == DRY_GRASS)
            return DIRT;

        if (this == CLAY_GRASS)
            return CLAY;

        throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    /**
     * Возвращает вариант блока почвы с травой.
     *
     * @param spreader Вариант блока почвы, который распространяет траву.
     * @return Вариант блока почвы с травой.
     */
    public SoilBlockVariant getGrassVersion(SoilBlockVariant spreader) {
        if (this == DIRT) {
            return CLAY_GRASS;
        }

        if (this == CLAY) {
            return spreader == DRY_GRASS ? DRY_GRASS : GRASS;
        }

        throw new IllegalArgumentException(String.format("You cannot get grass from [%s] types.", spreader.toString()));
    }

    /**
     * Проверяет, является ли вариант блока почвы с травой.
     *
     * @return true, если вариант блока почвы с травой, иначе false.
     */
    public boolean isGrass() {
        return this == GRASS || this == DRY_GRASS || this == CLAY_GRASS;
    }

    /**
     * Применяет вариант блока почвы к фабрике для создания блока почвы.
     *
     * @param soilType Тип почвы.
     * @return Блок почвы.
     */
    @Nonnull
    public ISoilBlock create(SoilType soilType) {
        return factory.apply(this, soilType);
    }
}
