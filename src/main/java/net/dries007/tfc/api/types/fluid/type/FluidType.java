package net.dries007.tfc.api.types.fluid.type;

import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.api.types.fluid.properties.DrinkableProperty;
import net.dries007.tfc.api.types.fluid.properties.FluidProperty;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static net.dries007.tfc.api.types.fluid.properties.DrinkableProperty.DRINKABLE;

public class FluidType {

    private static final Set<FluidType> FLUID_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    private final int color;
    private final int temperature;
    private final Map<FluidProperty<?>, Object> properties;
    private EnumRarity rarity = EnumRarity.COMMON;

    public FluidType(@Nonnull String name, int color, int temperature, EnumRarity rarity) {
        this.name = name;
        this.color = color;
        this.temperature = temperature;
        this.properties = new HashMap<>();
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
        private final Map<FluidProperty<?>, Object> properties;
        private EnumRarity rarity;

        public Builder(@Nonnull String name, int color) {
            this.name = name;
            this.color = color;
            this.temperature = 300;
            this.properties = new HashMap<>();
            this.rarity = EnumRarity.COMMON;
        }

        // Установка температуры
        public Builder setTemperature(int temperature) {
            this.temperature = temperature;
            return this;
        }

        // Это Алкоголь
        public Builder isAlcohol() {
            DrinkableProperty alcoholProperty = player -> {
                IPlayerData playerData = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
                if (player.getFoodStats() instanceof FoodStatsTFC && playerData != null) {
                    ((FoodStatsTFC) player.getFoodStats()).addThirst(10);
                    playerData.addIntoxicatedTime(4 * ICalendar.TICKS_IN_HOUR);
                    if (playerData.getIntoxicatedTime() > 24 * ICalendar.TICKS_IN_HOUR && Constants.RNG.nextFloat() < 0.5f) {
                        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 1200, 1));
                    }
                }
            };
            properties.put(DRINKABLE, alcoholProperty);
            return this;
        }

        // Установка свойств // не работает пока что
        public Builder setProperty(FluidProperty propertyType, FluidType propertyValue) {
            properties.put(propertyType, propertyValue);
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
}
