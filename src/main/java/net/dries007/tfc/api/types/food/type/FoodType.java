package net.dries007.tfc.api.types.food.type;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.types.food.category.FoodCategory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс, представляющий типы еды.
 */
public class FoodType {
    private static final Set<FoodType> FOOD_TYPES = new LinkedHashSet<>();

    private final String name;
    private final FoodCategory category;
    private final FoodData foodData;

    private final boolean heatable;
    private final float heatCapacity;
    private final float cookingTemp;

    private final String[] oreDictNames;

    /**
     * Конструктор класса FoodType.
     *
     * @param name          название типа еды
     * @param category      категория еды
     * @param foodData      данные о пищевой ценности
     * @param heatCapacity  теплоемкость
     * @param cookingTemp   температура приготовления
     * @param oreNames      имена в словаре руд
     */
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

    /**
     * Возвращает категорию еды.
     *
     * @return Категория еды.
     */
    @Nonnull
    public FoodCategory getFoodCategory() {
        return category;
    }

    /**
     * Возвращает данные о пищевой ценности.
     *
     * @return Данные о пищевой ценности.
     */
    @Nonnull
    public FoodData getData() {
        return foodData;
    }

    /**
     * Проверяет, может ли тип еды быть нагреваемым.
     *
     * @return true, если тип еды может быть нагреваемым, иначе false.
     */
    public boolean isHeatable() {
        return heatable;
    }

    /**
     * Возвращает теплоемкость типа еды.
     *
     * @return Теплоемкость типа еды.
     */
    public float getHeatCapacity() {
        return heatCapacity;
    }

    /**
     * Возвращает температуру приготовления типа еды.
     *
     * @return Температура приготовления типа еды.
     */
    public float getCookingTemp() {
        return cookingTemp;
    }

    /**
     * Возвращает имена в словаре руд для типа еды.
     *
     * @return Имена в словаре руд для типа еды.
     */
    @Nullable
    public String[] getOreDictNames() {
        return oreDictNames;
    }

    /**
     * Внутренний класс Builder для создания экземпляров класса FoodType.
     */
    public static class Builder {

        private final String name;
        private FoodCategory category;
        private FoodData foodData;
        private float heatCapacity;
        private float cookingTemp;
        private String[] oreDictNames;

        /**
         * Конструктор класса Builder.
         *
         * @param name название типа еды
         */
        public Builder(String name) {
            this.name = name;
            this.heatCapacity = 0;
            this.cookingTemp = -1;
        }

        /**
         * Устанавливает категорию еды.
         *
         * @param category категория еды
         * @return объект Builder для цепочки вызовов
         */
        public Builder setCategory(FoodCategory category) {
            this.category = category;
            return this;
        }

        /**
         * Устанавливает данные о пищевой ценности.
         *
         * @param hunger        уровень насыщения
         * @param saturation    уровень сытости
         * @param water         количество воды
         * @param grain         количество зерна
         * @param veg           количество овощей
         * @param fruit         количество фруктов
         * @param meat          количество мяса
         * @param dairy         количество молочных продуктов
         * @param decayModifier модификатор распада
         * @return объект Builder для цепочки вызовов
         */
        public Builder setFoodData(int hunger, float saturation, float water, float grain, float veg, float fruit, float meat, float dairy, float decayModifier) {
            this.foodData = new FoodData(hunger, water, saturation, grain, fruit, veg, meat, dairy, decayModifier);
            return this;
        }

        /**
         * Устанавливает теплоемкость типа еды.
         *
         * @param heatCapacity теплоемкость
         * @return объект Builder для цепочки вызовов
         */
        public Builder setHeatCapacity(float heatCapacity) {
            this.heatCapacity = heatCapacity;
            return this;
        }

        /**
         * Устанавливает температуру приготовления типа еды.
         *
         * @param cookingTemp температура приготовления
         * @return объект Builder для цепочки вызовов
         */
        public Builder setCookingTemp(float cookingTemp) {
            this.cookingTemp = cookingTemp;
            return this;
        }

        /**
         * Устанавливает имена в словаре руд для типа еды.
         *
         * @param oreDictNames имена в словаре руд
         * @return объект Builder для цепочки вызовов
         */
        public Builder setOreDictNames(String... oreDictNames) {
            this.oreDictNames = oreDictNames;
            return this;
        }

        /**
         * Создает и возвращает экземпляр класса FoodType с заданными параметрами.
         *
         * @return экземпляр класса FoodType
         */
        public FoodType build() {
            return new FoodType(name, category, foodData, heatCapacity, cookingTemp, oreDictNames);
        }
    }
}
