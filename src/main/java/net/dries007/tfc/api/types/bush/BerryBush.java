package net.dries007.tfc.api.types.bush;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.WorldGenBerryBushes;
import net.minecraft.item.ItemStack;

public enum BerryBush implements IBerryBush {
    BLACKBERRY(FoodTypes.BLACKBERRY, Month.MAY, 4, 7f, 20f, 100f, 400f, 0.8f, Size.LARGE, true),
    BLUEBERRY(FoodTypes.BLUEBERRY, Month.JUNE, 3, 7f, 25f, 100f, 400f, 0.8f, Size.LARGE, false),
    BUNCH_BERRY(FoodTypes.BUNCH_BERRY, Month.JUNE, 3, 15f, 30f, 100f, 400f, 0.8f, Size.SMALL, false),
    CLOUD_BERRY(FoodTypes.CLOUD_BERRY, Month.JUNE, 2, 3f, 17f, 100f, 400f, 0.8f, Size.MEDIUM, false),
    CRANBERRY(FoodTypes.CRANBERRY, Month.AUGUST, 3, 1f, 19f, 100f, 400f, 0.8f, Size.MEDIUM, false),
    ELDERBERRY(FoodTypes.ELDERBERRY, Month.JULY, 2, 10f, 29f, 100f, 400f, 0.8f, Size.LARGE, false),
    GOOSEBERRY(FoodTypes.GOOSEBERRY, Month.MARCH, 4, 5f, 27f, 100f, 400f, 0.8f, Size.MEDIUM, false),
    RASPBERRY(FoodTypes.RASPBERRY, Month.JUNE, 2, 5f, 20f, 100f, 400f, 0.8f, Size.LARGE, true),
    SNOW_BERRY(FoodTypes.SNOW_BERRY, Month.JULY, 2, -5f, 18f, 100f, 400f, 0.8f, Size.SMALL, false),
    STRAWBERRY(FoodTypes.STRAWBERRY, Month.MARCH, 3, 5f, 28f, 100f, 400f, 0.8f, Size.SMALL, false),
    WINTERGREEN_BERRY(FoodTypes.WINTERGREEN_BERRY, Month.AUGUST, 2, -5f, 17f, 100f, 400f, 0.8f, Size.SMALL, false);

    static {
        for (IBerryBush bush : values()) {
            WorldGenBerryBushes.register(bush);
        }
    }

    private final FoodType fruit;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    private final Size size;
    private final boolean hasSpikes;

    BerryBush(FoodType fruit, Month harvestMonthStart, int harvestingMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, Size size, boolean spiky) {
        this.fruit = fruit;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        this.size = size;
        this.hasSpikes = spiky;
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

    public Size getSize() {
        return this.size;
    }

    @Override
    public boolean isSpiky() {
        return hasSpikes;
    }
}
