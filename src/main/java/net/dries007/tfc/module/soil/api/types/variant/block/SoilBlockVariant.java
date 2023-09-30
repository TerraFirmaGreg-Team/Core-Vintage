package net.dries007.tfc.module.soil.api.types.variant.block;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.soil.StorageSoil;
import net.dries007.tfc.module.soil.api.types.type.SoilType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Класс, представляющий вариант блока почвы.
 */
public class SoilBlockVariant {

    private static final Set<SoilBlockVariant> SOIL_BLOCK_VARIANTS = new ObjectLinkedOpenHashSet<>();

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

        for (var type : SoilType.getSoilTypes()) {
            if (StorageSoil.SOIL_BLOCKS.put(new Pair<>(this, type), this.create(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
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
        if (this == SoilBlockVariants.GRASS || this == SoilBlockVariants.DRY_GRASS)
            return SoilBlockVariants.DIRT;

        if (this == SoilBlockVariants.CLAY_GRASS)
            return SoilBlockVariants.CLAY;

        throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    /**
     * Возвращает вариант блока почвы с травой.
     *
     * @param spreader Вариант блока почвы, который распространяет траву.
     * @return Вариант блока почвы с травой.
     */
    public SoilBlockVariant getGrassVersion(SoilBlockVariant spreader) {
        if (this == SoilBlockVariants.DIRT) {
            return SoilBlockVariants.CLAY_GRASS;
        }

        if (this == SoilBlockVariants.CLAY) {
            return spreader == SoilBlockVariants.DRY_GRASS ? SoilBlockVariants.DRY_GRASS : SoilBlockVariants.GRASS;
        }

        throw new IllegalArgumentException(String.format("You cannot get grass from [%s] types.", spreader.toString()));
    }

    /**
     * Проверяет, является ли вариант блока почвы с травой.
     *
     * @return true, если вариант блока почвы с травой, иначе false.
     */
    public boolean isGrass() {
        return this == SoilBlockVariants.GRASS || this == SoilBlockVariants.DRY_GRASS || this == SoilBlockVariants.CLAY_GRASS;
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
