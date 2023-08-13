package net.dries007.tfc.api.types.food.type;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.types.food.category.FoodCategory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;

public class FoodType {
    private static final Set<FoodType> FOOD_TYPES = new LinkedHashSet<>();

    private final String name;
    private final FoodCategory category;
    private final FoodData foodData;

    private final boolean heatable;
    private final float heatCapacity;
    private final float cookingTemp;

    private final String[] oreDictNames;

    FoodType(String name, @Nonnull FoodCategory category, FoodData foodData, float heatCapacity, float cookingTemp, String... oreNames) {
        this.name = name;
        this.category = category;
        this.foodData = foodData;

        this.heatable = cookingTemp >= 0;
        this.heatCapacity = heatCapacity;
        this.cookingTemp = cookingTemp;

        this.oreDictNames = oreNames == null || oreNames.length == 0 ? null : oreNames;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("FoodType name must contain any character: [%s]", name));
        }

        if (!FOOD_TYPES.add(this)) {
            throw new RuntimeException(String.format("FoodType: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех типов еды.
     *
     * @return Набор всех типов еды.
     */
    public static Set<FoodType> getFoodTypes() {
        return FOOD_TYPES;
    }

    /**
     * Возвращает строковое представление типов еды.
     *
     * @return Строковое представление типов еды.
     */
    @Override
    public String toString() {
        return name;
    }

    @Nonnull
    public FoodCategory getFoodCategory() {
        return category;
    }

    @Nonnull
    public FoodData getData() {
        return foodData;
    }

    public boolean isHeatable() {
        return heatable;
    }

    public float getHeatCapacity() {
        return heatCapacity;
    }

    public float getCookingTemp() {
        return cookingTemp;
    }

    @Nullable
    public String[] getOreDictNames() {
        return oreDictNames;
    }

    public static class Builder {

        private final String name;
        private FoodCategory category;
        private FoodData foodData;
        private float heatCapacity;
        private float cookingTemp;
        private String[] oreDictNames;

        public Builder(String name) {
            this.name = name;
            this.heatCapacity = 0;
            this.cookingTemp = -1;
        }

        public Builder setCategory(FoodCategory category) {
            this.category = category;
            return this;
        }

        public Builder setFoodData(int hunger, float saturation, float water, float grain, float veg, float fruit, float meat, float dairy, float decayModifier) {
            this.foodData = new FoodData(hunger, water, saturation, grain, fruit, veg, meat, dairy, decayModifier);
            return this;
        }

        public Builder setHeatCapacity(float heatCapacity) {
            this.heatCapacity = heatCapacity;
            return this;
        }

        public Builder setCookingTemp(float cookingTemp) {
            this.cookingTemp = cookingTemp;
            return this;
        }

        public Builder setOreDictNames(String... oreDictNames) {
            this.oreDictNames = oreDictNames;
            return this;
        }

        public FoodType build() {
            return new FoodType(name, category, foodData, heatCapacity, cookingTemp, oreDictNames);
        }
    }
}
