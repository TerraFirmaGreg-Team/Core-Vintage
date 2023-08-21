package net.dries007.tfc.api.types.bush.type;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.bush.IBerryBush;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class BushType implements IBerryBush {

    private static final Set<BushType> BUSH_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    private final FoodType fruit;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    private final IBerryBush.Size size;
    private final boolean spiky;

    /**
     * Создает экземпляр класса BushType с указанным именем.
     *
     * @param name Имя типа куста.
     */
    public BushType(@Nonnull String name, FoodType fruit,
                    Month harvestMonthStart, int harvestingMonths,
                    float minTemp, float maxTemp,
                    float minRain, float maxRain,
                    float growthTime, Size size, boolean spiky) {
        this.name = name;
        this.fruit = fruit;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        this.size = size;
        this.spiky = spiky;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("BushType name must contain any character: [%s]", name));
        }

        if (!BUSH_TYPES.add(this)) {
            throw new RuntimeException(String.format("BushType: [%s] already exists!", name));
        }
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

    public FoodType getFruit() {
        return this.fruit;
    }

    @Override
    public float getGrowthTime() {
        return this.growthTime;
    }

    @Override
    public boolean isHarvestMonth(Month month) {
        Month testing = this.harvestMonthStart;
        for (int i = 0; i < this.harvestingMonths; i++) {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    @Override
    public boolean isValidConditions(float temperature, float rainfall) {
        return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall && rainfall < maxRain + 50;
    }

    @Override
    public boolean isValidForGrowth(float temperature, float rainfall) {
        return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
    }

    @Override
    public ItemStack getFoodDrop() {
        return new ItemStack(TFCStorage.getFoodItem(getFruit()));
    }

    public IBerryBush.Size getSize() {
        return this.size;
    }

    @Override
    public boolean isSpiky() {
        return spiky;
    }
}
