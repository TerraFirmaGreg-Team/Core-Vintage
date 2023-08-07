package net.dries007.tfc.api.types.rock.block.variant;

import net.dries007.tfc.api.util.FallingBlockManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс, представляющий вариант блока породы.
 */
public class RockBlockVariant {
    private static final Set<RockBlockVariant> rockBlockVariants = new LinkedHashSet<>();

    @Nonnull
    private final String rockBlockVariantName;
    private final float baseHardness;
    @Nullable
    private final FallingBlockManager.Specification fallingSpecification;
    /**
     * Создает экземпляр класса RockBlockVariant с указанными параметрами.
     *
     * @param rockBlockVariantName Название варианта блока породы.
     * @param baseHardness         Базовая прочность блока породы.
     */
    public RockBlockVariant(@Nonnull String rockBlockVariantName, float baseHardness, @Nullable FallingBlockManager.Specification fallingSpecification) {
        this.rockBlockVariantName = rockBlockVariantName;
        this.baseHardness = baseHardness;
        this.fallingSpecification = fallingSpecification;

        if (rockBlockVariantName.isEmpty()) {
            throw new RuntimeException(String.format("RockBlockVariant name must contain any character: [%s]", rockBlockVariantName));
        }

        if (!rockBlockVariants.add(this)) {
            throw new RuntimeException(String.format("RockBlockVariant: [%s] already exists!", rockBlockVariantName));
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
        return rockBlockVariantName;
    }

    /**
     * Возвращает набор всех вариантов блоков породы.
     *
     * @return Набор всех вариантов блоков породы.
     */
    public static Set<RockBlockVariant> getRockBlockVariants() {
        return rockBlockVariants;
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
