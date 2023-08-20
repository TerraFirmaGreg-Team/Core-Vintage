package net.dries007.tfc.api.types.fruit.type;

import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;

public class FruitTreeType {



    private final FoodType fruit;
    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;

    FruitTreeType(FoodType fruit,
                  Month flowerMonthStart, int floweringMonths,
                  Month harvestMonthStart, int harvestingMonths,
                  float growthTime) {
        this.fruit = fruit;
        this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

    }


}
