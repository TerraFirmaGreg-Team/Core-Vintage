package net.dries007.tfc.module.soil.api.type;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Класс, представляющий тип почвы.
 */
public class SoilType {

    private static final Set<SoilType> SOIL_TYPES = new ObjectLinkedOpenHashSet<>();

    @Nonnull
    private final String name;

    /**
     * Создает экземпляр класса SoilType с указанным именем.
     *
     * @param name Имя типа почвы.
     */
    public SoilType(@Nonnull String name) {
        this.name = name;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("SoilType name must contain any character: [%s]", name));
        }

        if (!SOIL_TYPES.add(this)) {
            throw new RuntimeException(String.format("SoilType: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех типов почвы.
     *
     * @return Набор всех типов почвы.
     */
    public static Set<SoilType> getSoilTypes() {
        return SOIL_TYPES;
    }

    /**
     * Возвращает тип почвы по индексу.
     *
     * @param i Индекс типа почвы.
     * @return Тип почвы.
     */
    @Nonnull
    public static SoilType valueOf(int i) {
        var values = new SoilType[SOIL_TYPES.size()];
        values = SOIL_TYPES.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }

    /**
     * Возвращает строковое представление типа почвы.
     *
     * @return Строковое представление типа почвы.
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }
}
