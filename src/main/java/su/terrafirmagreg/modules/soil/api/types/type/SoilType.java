package su.terrafirmagreg.modules.soil.api.types.type;

import su.terrafirmagreg.api.base.types.type.Type;


import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Set;

@Getter
public class SoilType extends Type<SoilType> {

    @Getter
    private static final Set<SoilType> types = new ObjectOpenHashSet<>();

    private final String name;

    private SoilType(Builder builder) {
        super(builder.name);
        this.name = builder.name;

        if (!types.add(this)) throw new RuntimeException(String.format("SoilType: [%s] already exists!", name));

    }

    /**
     * Возвращает тип почвы по индексу.
     *
     * @param i Индекс типа почвы.
     * @return Тип почвы.
     */
    @NotNull
    public static SoilType valueOf(int i) {
        var values = new SoilType[types.size()];
        values = types.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }

    public static Builder builder(String name) {

        return new Builder(name);
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
