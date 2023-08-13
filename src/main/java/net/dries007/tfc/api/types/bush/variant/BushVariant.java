package net.dries007.tfc.api.types.bush.variant;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class BushVariant {

    private static final Set<BushVariant> BUSH_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;

    /**
     * Создает экземпляр класса BushVariant с указанным именем.
     *
     * @param name Имя типа куста.
     */
    public BushVariant(@Nonnull String name) {
        this.name = name;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("BushVariant name must contain any character: [%s]", name));
        }

        if (!BUSH_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("BushVariant: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех вариантов кустов.
     *
     * @return Набор всех вариантов кустов.
     */
    public static Set<BushVariant> getBushVariants() {
        return BUSH_VARIANTS;
    }

    /**
     * Возвращает строковое представление варианта куста.
     *
     * @return Строковое представление варианта куста.
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }
}
