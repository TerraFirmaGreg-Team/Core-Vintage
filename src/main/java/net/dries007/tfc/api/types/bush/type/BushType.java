package net.dries007.tfc.api.types.bush.type;

import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.common.objects.blocks.berrybush.BlockBerryBush;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

import static net.dries007.tfc.api.types.bush.IBushBlock.Size;
import static net.dries007.tfc.api.types.food.variant.Item.FoodItemVariants.INGREDIENT;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.BLOCKS;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.BUSH_BLOCKS;

public class BushType {

    // Набор всех вариантов кустов
    private static final Set<BushType> BUSH_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name; // Название варианта куста
    private final FoodType fruit; // Тип плода
    private final Month harvestMonthStart; // Месяц начала сбора урожая
    private final int harvestingMonths; // Количество месяцев сбора урожая
    private final float growthTime; // Время роста в часах
    private final float minTemp; // Минимальная температура для роста
    private final float maxTemp; // Максимальная температура для роста
    private final float minRain; // Минимальное количество осадков для роста
    private final float maxRain; // Максимальное количество осадков для роста
    private final Size size; // Размер куста
    private final boolean spiky; // Является ли куст колючим

    private BushType(Builder builder) {
        this.name = builder.name;
        this.fruit = builder.fruit;
        this.harvestMonthStart = builder.harvestMonthStart;
        this.harvestingMonths = builder.harvestingMonths;
        this.growthTime = builder.growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = builder.minTemp;
        this.maxTemp = builder.maxTemp;
        this.minRain = builder.minRain;
        this.maxRain = builder.maxRain;

        this.size = builder.size;
        this.spiky = builder.spiky;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("BushType name must contain any character: [%s]", name));
        }

        if (!BUSH_TYPES.add(this)) {
            throw new RuntimeException(String.format("BushType: [%s] already exists!", name));
        }

        var bushBlock = new BlockBerryBush(this);

        if (BUSH_BLOCKS.put(this, bushBlock) != null)
            throw new RuntimeException(String.format("Duplicate registry detected: %s", this));
        BLOCKS.add(bushBlock);
    }

    /**
     * Возвращает набор всех вариантов кустов.
     *
     * @return Набор всех вариантов кустов.
     */
    public static Set<BushType> getBushTypes() {
        return BUSH_TYPES;
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

    /**
     * Возвращает тип плода.
     *
     * @return Тип плода.
     */
    public FoodType getFruit() {
        return this.fruit;
    }

    /**
     * Возвращает время роста в часах.
     *
     * @return Время роста в часах.
     */
    public float getGrowthTime() {
        return this.growthTime;
    }

    /**
     * Проверяет, является ли указанный месяц месяцем сбора урожая.
     *
     * @param month Месяц для проверки.
     * @return true, если указанный месяц является месяцем сбора урожая, иначе false.
     */
    public boolean isHarvestMonth(Month month) {
        Month testing = this.harvestMonthStart;
        for (int i = 0; i < this.harvestingMonths; i++) {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    /**
     * Проверяет, являются ли указанные условия (температура и осадки) подходящими для роста куста.
     *
     * @param temperature Температура.
     * @param rainfall    Количество осадков.
     * @return true, если условия подходят для роста куста, иначе false.
     */
    public boolean isValidConditions(float temperature, float rainfall) {
        return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall && rainfall < maxRain + 50;
    }

    /**
     * Проверяет, являются ли указанные условия (температура и осадки) подходящими для роста и сбора урожая куста.
     *
     * @param temperature Температура.
     * @param rainfall    Количество осадков.
     * @return true, если условия подходят для роста и сбора урожая куста, иначе false.
     */
    public boolean isValidForGrowth(float temperature, float rainfall) {
        return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
    }

    /**
     * Возвращает предмет с плодом, который выпадает с этого куста.
     *
     * @return Предмет с плодом.
     */
    public ItemStack getFoodDrop() {
        return new ItemStack(TFCItems.getFoodItem(INGREDIENT, getFruit()));
    }

    /**
     * Возвращает размер куста.
     *
     * @return Размер куста.
     */
    public Size getSize() {
        return this.size;
    }


    /**
     * Проверяет, является ли куст колючим.
     *
     * @return true, если куст колючий, иначе false.
     */
    public boolean isSpiky() {
        return spiky;
    }

    /**
     * Строитель для создания экземпляра класса BushType.
     */
    public static class Builder {
        private final String name;
        private FoodType fruit;
        private Month harvestMonthStart;
        private int harvestingMonths;
        private float growthTime;
        private float minTemp;
        private float maxTemp;
        private float minRain;
        private float maxRain;
        private Size size;
        private boolean spiky;

        /**
         * Конструктор строителя.
         *
         * @param name Название куста.
         */
        public Builder(String name) {
            this.name = name;
        }

        /**
         * Устанавливает тип плода, который выпадает с куста.
         *
         * @param fruit Тип плода.
         */
        public Builder setFruitDrop(FoodType fruit) {
            this.fruit = fruit;
            return this;
        }

        /**
         * Устанавливает месяц начала сбора урожая и количество месяцев сбора урожая.
         *
         * @param harvestMonthStart Месяц начала сбора урожая.
         * @param harvestingMonths  Количество месяцев сбора урожая.
         */
        public Builder setHarvestMonth(Month harvestMonthStart, int harvestingMonths) {
            this.harvestMonthStart = harvestMonthStart;
            this.harvestingMonths = harvestingMonths;
            return this;
        }

        /**
         * Устанавливает время роста куста в часах.
         *
         * @param growthTime Время роста в часах.
         */
        public Builder setGrowthTime(float growthTime) {
            this.growthTime = growthTime;
            return this;
        }

        /**
         * Устанавливает диапазон температур для роста куста.
         *
         * @param minTemp Минимальная температура.
         * @param maxTemp Максимальная температура.
         */
        public Builder setTemp(float minTemp, float maxTemp) {
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
            return this;
        }

        /**
         * Устанавливает диапазон осадков для роста куста.
         *
         * @param minRain Минимальное количество осадков.
         * @param maxRain Максимальное количество осадков.
         */
        public Builder setRain(float minRain, float maxRain) {
            this.minRain = minRain;
            this.maxRain = maxRain;
            return this;
        }

        /**
         * Устанавливает размер куста.
         *
         * @param size Размер куста.
         */
        public Builder setSize(Size size) {
            this.size = size;
            return this;
        }

        // Устанавливает, является ли куст колючим.
        public Builder setSpikes() {
            this.spiky = true;
            return this;
        }

        public BushType build() {
            return new BushType(this);
        }
    }
}
