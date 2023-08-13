package net.dries007.tfc.api.types.bush.type;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class BushType {

    private static final Set<BushType> BUSH_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;

    /**
     * Создает экземпляр класса BushType с указанным именем.
     *
     * @param name Имя типа куста.
     */
    public BushType(@Nonnull String name) {
        this.name = name;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("BushType name must contain any character: [%s]", name));
        }

        if (!BUSH_TYPES.add(this)) {
            throw new RuntimeException(String.format("BushType: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех типов кустов.
     *
     * @return Набор всех типов кустов.
     */
    public static Set<BushType> getBushTypes() {
        return BUSH_TYPES;
    }

    /**
     * Возвращает строковое представление типа куста.
     *
     * @return Строковое представление типа куста.
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }
}
