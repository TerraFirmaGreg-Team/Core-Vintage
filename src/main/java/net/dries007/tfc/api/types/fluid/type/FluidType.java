package net.dries007.tfc.api.types.fluid.type;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class FluidType {

    private static final Set<FluidType> FLUID_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    private final int color;
    private final int temperature;

    public FluidType(@Nonnull String name, int color, int temperature) {
        this.name = name;
        this.color = color;
        this.temperature = temperature;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("Fluid name must contain any character: [%s]", name));
        }

        if (!FLUID_TYPES.add(this)) {
            throw new RuntimeException(String.format("Fluid: [%s] already exists!", name));
        }
    }

    public static Set<FluidType> getFluidTypes() {
        return FLUID_TYPES;
    }

    @Nonnull
    public static FluidType valueOf(int i) {
        var values = new FluidType[FLUID_TYPES.size()];
        values = FLUID_TYPES.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }

    /**
     * Получить имя этой жидкости.
     *
     * @return имя жидкости
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает цвет данного типа жидкости.
     *
     * @return цвет
     */
    public int getColor() {
        return color;
    }

    public static class Builder {
        @Nonnull
        private final String name;
        private final int color;
        private int temperature;

        public Builder(@Nonnull String name, int color) {
            this.name = name;
            this.color = color;
            this.temperature = 300;
        }

        // Установка температуры
        public Builder setTemperature(int temperature) {
            this.temperature = temperature;
            return this;
        }

        // Установка свойств
        public Builder setProperty(int temperature) {
            this.temperature = temperature;
            return this;
        }



        // Метод для создания объекта FluidType с использованием заданных значений
        public FluidType build() {
            return new FluidType(name, color);
        }
    }
}
