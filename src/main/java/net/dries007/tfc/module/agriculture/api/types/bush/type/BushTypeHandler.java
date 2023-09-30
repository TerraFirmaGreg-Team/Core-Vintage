package net.dries007.tfc.module.agriculture.api.types.bush.type;

import net.dries007.tfc.module.agriculture.api.types.bush.IBushBlock;
import net.dries007.tfc.module.food.api.types.type.FoodTypes;
import net.dries007.tfc.util.calendar.Month;

import static net.dries007.tfc.module.agriculture.api.types.bush.type.BushTypes.*;

public class BushTypeHandler {

    public static void init() {
        BLACKBERRY = new BushType.Builder("blackberry")
                .setFruitDrop(FoodTypes.BLACKBERRY)
                .setHarvestMonth(Month.MAY, 4)
                .setGrowthTime(0.8f).setTemp(7f, 20f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .setSpikes()
                .build();

        BLUEBERRY = new BushType.Builder("blueberry")
                .setFruitDrop(FoodTypes.BLUEBERRY)
                .setHarvestMonth(Month.JUNE, 3)
                .setGrowthTime(0.8f).setTemp(7f, 25f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

        BUNCH_BERRY = new BushType.Builder("bunch_berry")
                .setFruitDrop(FoodTypes.BUNCH_BERRY)
                .setHarvestMonth(Month.JUNE, 3)
                .setGrowthTime(0.8f).setTemp(15f, 30f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

        CLOUD_BERRY = new BushType.Builder("cloud_berry")
                .setFruitDrop(FoodTypes.CLOUD_BERRY)
                .setHarvestMonth(Month.JUNE, 2)
                .setGrowthTime(0.8f).setTemp(3f, 17f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

        CRANBERRY = new BushType.Builder("cranberry")
                .setFruitDrop(FoodTypes.CRANBERRY)
                .setHarvestMonth(Month.AUGUST, 3)
                .setGrowthTime(0.8f).setTemp(1f, 19f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

        ELDERBERRY = new BushType.Builder("elderberry")
                .setFruitDrop(FoodTypes.ELDERBERRY)
                .setHarvestMonth(Month.JULY, 2)
                .setGrowthTime(0.8f).setTemp(10f, 29f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

        GOOSEBERRY = new BushType.Builder("gooseberry")
                .setFruitDrop(FoodTypes.GOOSEBERRY)
                .setHarvestMonth(Month.MARCH, 4)
                .setGrowthTime(0.8f).setTemp(5f, 27f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

        RASPBERRY = new BushType.Builder("raspberry")
                .setFruitDrop(FoodTypes.RASPBERRY)
                .setHarvestMonth(Month.JUNE, 2)
                .setGrowthTime(0.8f).setTemp(5f, 20f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .setSpikes()
                .build();

        SNOW_BERRY = new BushType.Builder("snow_berry")
                .setFruitDrop(FoodTypes.SNOW_BERRY)
                .setHarvestMonth(Month.JULY, 2)
                .setGrowthTime(0.8f).setTemp(-5f, 18f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

        STRAWBERRY = new BushType.Builder("strawberry")
                .setFruitDrop(FoodTypes.STRAWBERRY)
                .setHarvestMonth(Month.MARCH, 3)
                .setGrowthTime(0.8f).setTemp(5f, 28f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

        WINTERGREEN_BERRY = new BushType.Builder("wintergreen_berry")
                .setFruitDrop(FoodTypes.WINTERGREEN_BERRY)
                .setHarvestMonth(Month.AUGUST, 2)
                .setGrowthTime(0.8f).setTemp(-5f, 17f).setRain(100f, 400f)
                .setSize(IBushBlock.Size.LARGE)
                .build();

    }
}
