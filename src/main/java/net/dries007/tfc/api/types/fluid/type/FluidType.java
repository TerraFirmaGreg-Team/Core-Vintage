package net.dries007.tfc.api.types.fluid.type;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class FluidType {

    private static final ResourceLocation STILL = new ResourceLocation(MOD_ID, "blocks/fluid_still");
    private static final ResourceLocation FLOW = new ResourceLocation(MOD_ID, "blocks/fluid_flow");
    private static final Set<FluidType> FLUID_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    private final int color;
    private final int temperature;
    private EnumRarity rarity;

    public FluidType(@Nonnull String name, int color, int temperature, EnumRarity rarity) {
        this.name = name;
        this.color = color;
        this.temperature = temperature;
        this.rarity = rarity;

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
        private EnumRarity rarity;

        public Builder(@Nonnull String name, int color) {
            this.name = name;
            this.color = color;
            this.temperature = 300;
            this.rarity = EnumRarity.COMMON;
        }

        // Установка температуры
        public Builder setTemperature(int temperature) {
            this.temperature = temperature;
            return this;
        }

        // Установка редкости
        public Builder setRarity(EnumRarity rarity) {
            this.rarity = rarity;
            return this;
        }

        // Метод для создания объекта FluidType с использованием заданных значений
        public FluidType build() {
            return new FluidType(name, color, temperature, rarity);
        }
    }

    private static Fluid registerFluid(String name, int color, int temperature, EnumRarity rarity) {


        boolean isDefault = !FluidRegistry.isFluidRegistered(name);
        Fluid fluid = FluidRegistry.getFluid(name);

        if (!isDefault) {
            // Fluid was already registered with this name, default to that fluid
            fluid = FluidRegistry.getFluid(name);
        } else {
            // No fluid found we are safe to register our default
            FluidRegistry.registerFluid(fluid);
        }
        FluidRegistry.addBucketForFluid(fluid);
        return fluid;
    }
}
