package su.terrafirmagreg.modules.soil.api.types.type;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Класс, представляющий тип почвы.
 */
public class SoilType implements Comparable<SoilType> {

    private static final Set<SoilType> SOIL_TYPES = new ObjectOpenHashSet<>();

    @NotNull
    private final String name;

    private SoilType(Builder builder) {
        this.name = builder.name;

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
    public static Set<SoilType> getTypes() {
        return SOIL_TYPES;
    }

    /**
     * Возвращает тип почвы по индексу.
     *
     * @param i Индекс типа почвы.
     * @return Тип почвы.
     */
    @NotNull
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
    @NotNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NotNull SoilType type) {
        return this.name.compareTo(type.toString());
    }

    public static class Builder {

        private final String name;

        /**
         * Создает экземпляр Builder с указанным именем.
         *
         * @param name Имя породы.
         */
        public Builder(@NotNull String name) {
            this.name = name;
        }

        public SoilType build() {
            return new SoilType(this);
        }
    }
}
