package net.dries007.tfc.api.types.fruit.type;

import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.util.calendar.Month;

public class FruitTreeType {



    private final FoodType fruit;
    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;

    public FruitTreeType(FoodType fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, float growthTime, float minTemp, float maxTemp, float minRain, float maxRain) {
        this.fruit = fruit;
        this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.growthTime = growthTime;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;
    }


}
