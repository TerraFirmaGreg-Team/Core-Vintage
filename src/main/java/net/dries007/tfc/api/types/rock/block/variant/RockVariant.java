package net.dries007.tfc.api.types.rock.block.variant;

import net.dries007.tfc.api.util.FallingBlockManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс, представляющий вариант блока породы.
 */
public class RockVariant {
    private static final Set<RockVariant> ROCK_VARIANT = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    private final float baseHardness;
    @Nullable
    private final FallingBlockManager.Specification fallingSpecification;

    /**
     * Создает экземпляр класса RockVariant с указанными параметрами.
     *
     * @param name         Название варианта блока породы.
     * @param baseHardness Базовая прочность блока породы.
     */
    public RockVariant(@Nonnull String name, float baseHardness, @Nullable FallingBlockManager.Specification fallingSpecification) {
        this.name = name;
        this.baseHardness = baseHardness;
        this.fallingSpecification = fallingSpecification;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("RockVariant name must contain any character: [%s]", name));
        }

        if (!ROCK_VARIANT.add(this)) {
            throw new RuntimeException(String.format("RockVariant: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает базовую прочность блока породы.
     *
     * @return Базовая прочность блока породы.
     */
    public float getBaseHardness() {
        return baseHardness;
    }

    /**
     * Возвращает спецификации для обработки физики текущего блока породы.
     *
     * @return Базовая прочность блока породы.
     */
    @Nullable
    public FallingBlockManager.Specification getFallingSpecification() {
        return fallingSpecification;
    }

    /**
     * Возвращает строковое представление варианта блока породы.
     *
     * @return Строковое представление варианта блока породы.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает набор всех вариантов блоков породы.
     *
     * @return Набор всех вариантов блоков породы.
     */
    public static Set<RockVariant> getAllRockVariants() {
        return ROCK_VARIANT;
    }

    /**
     * Возвращает, может ли блок породы падать.
     *
     * @return true, если блок породы может падать, в противном случае - false.
     */
    public boolean canFall() {
        return fallingSpecification != null;
    }
}
