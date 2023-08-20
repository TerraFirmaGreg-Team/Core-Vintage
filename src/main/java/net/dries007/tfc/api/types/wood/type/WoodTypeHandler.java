package net.dries007.tfc.api.types.wood.type;

import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.util.calendar.Month;

import static net.dries007.tfc.api.types.trees.TreeGenerators.*;
import static net.dries007.tfc.api.types.wood.type.WoodTypes.*;

public class WoodTypeHandler {

    public static void init() {
        ACACIA = new WoodType
                .Builder("acacia")
                .setColor(0x8B3929).setTemp(19f, 31f).setRain(30f, 210f)
                .setBurnInfo(650f, 1000)
                .setHeight(12).setMinGrowthTime(11).setDensity(0.1f, 0.6f)
                .setGenerator(GEN_ACACIA)
                .build();

        ASH = new WoodType
                .Builder("ash")
                .setColor(0xAE604E).setTemp(-6f, 12f).setRain(60f, 140f)
                .setBurnInfo(696f, 1250)
                .setGenerator(GEN_NORMAL)
                .build();

        ASPEN = new WoodType
                .Builder("aspen")
                .setColor(0x373727).setTemp(-10f, 16f).setRain(10f, 80f)
                .setBurnInfo(611f, 1000)
                .setMinGrowthTime(8)
                .setGenerator(GEN_MEDIUM)
                .build();

        BIRCH = new WoodType
                .Builder("birch")
                .setColor(0x897658).setTemp(-15f, 7f).setRain(20f, 180f)
                .setBurnInfo(652f, 1750)
                .setGenerator(GEN_TALL)
                .setTannin()
                .build();

        BLACKWOOD = new WoodType
                .Builder("blackwood")
                .setColor(0x1A1A1A).setTemp(4f, 33f).setRain(0f, 120f)
                .setBurnInfo(720f, 1750)
                .setHeight(12).setMinGrowthTime(8)
                .setGenerator(GEN_MEDIUM)
                .build();

        CHESTNUT = new WoodType
                .Builder("chestnut")
                .setColor(0x642C1E).setTemp(11f, 35f).setRain(160f, 320f)
                .setBurnInfo(651f, 1500)
                .setGenerator(GEN_NORMAL)
                .setTannin()
                .build();

        DOUGLAS_FIR = new WoodType
                .Builder("douglas_fir")
                .setColor(0xD7BC8D).setTemp(-2f, 14f).setRain(280f, 480f)
                .setBurnInfo(707f, 1500)
                .setDominance(5.2f).setHeight(16).setDensity(0.25f, 2f)
                .setGenerator(GEN_TALL)
                .setBushes().setTannin()
                .build();

        HICKORY = new WoodType
                .Builder("hickory")
                .setColor(0x4E3418).setTemp(7f, 29f).setRain(80f, 250f)
                .setBurnInfo(762f, 2000)
                .setMinGrowthTime(10)
                .setGenerator(GEN_TALL)
                .setTannin()
                .build();

        MAPLE = new WoodType
                .Builder("maple")
                .setColor(0xC3782F).setTemp(3f, 20f).setRain(140f, 360f)
                .setBurnInfo(745f, 2000)
                .setDominance(6.3f)
                .setGenerator(GEN_MEDIUM)
                .setTannin()
                .build();

        OAK = new WoodType
                .Builder("oak")
                .setColor(0xC29D62).setTemp(-8f, 12f).setRain(180f, 430f)
                .setBurnInfo(728f, 2250)
                .setHeight(16).setMinGrowthTime(10)
                .setGenerator(GEN_TALL)
                .setTannin()
                .build();

        PALM = new WoodType
                .Builder("palm")
                .setColor(0xB56F38).setTemp(16f, 35f).setRain(280f, 500f)
                .setBurnInfo(730f, 1250)
                .setDecayDist(6)
                .setGenerator(GEN_TROPICAL)
                .build();

        PINE = new WoodType
                .Builder("pine")
                .setColor(0xD1BD9A).setTemp(-15f, 7f).setRain(60f, 250f)
                .setBurnInfo(627f, 1250)
                .setDensity(0.1f, 0.8f)
                .setGenerator(GEN_CONIFER)
                .setConifer()
                .build();

        ROSEWOOD = new WoodType
                .Builder("rosewood")
                .setColor(0x912222).setTemp(8f, 18f).setRain(10f, 190f)
                .setBurnInfo(640f, 1500)
                .setHeight(12).setMinGrowthTime(8)
                .setGenerator(GEN_MEDIUM)
                .build();

        SEQUOIA = new WoodType
                .Builder("sequoia")
                .setColor(0x965B3B).setTemp(-5f, 12f).setRain(250f, 420f)
                .setBurnInfo(612f, 1750)
                .setRadius(3).setHeight(24).setDecayDist(6).setMinGrowthTime(18).setDensity(0.4f, 0.9f)
                .setGenerator(GEN_SEQUOIA)
                .setConifer().setBushes().setTannin()
                .build();

        SPRUCE = new WoodType
                .Builder("spruce")
                .setColor(0xBF806F).setTemp(-11f, 6f).setRain(120f, 380f)
                .setBurnInfo(608f, 1500)
                .setDensity(0.1f, 0.8f)
                .setGenerator(GEN_CONIFER)
                .setConifer()
                .build();

        SYCAMORE = new WoodType
                .Builder("sycamore")
                .setColor(0xDCA448).setTemp(17f, 33f).setRain(120f, 290f)
                .setBurnInfo(653f, 1750)
                .setMinGrowthTime(8).setDensity(0.25f, 2f)
                .setGenerator(GEN_MEDIUM)
                .setBushes()
                .build();

        WHITE_CEDAR = new WoodType
                .Builder("white_cedar")
                .setColor(0xD4D4D4).setTemp(-8f, 17f).setRain(10f, 240f)
                .setBurnInfo(625f, 1500)
                .setHeight(16)
                .setGenerator(GEN_TALL)
                .build();

        WILLOW = new WoodType
                .Builder("willow")
                .setColor(0x3A430B).setTemp(15f, 32f).setRain(230f, 400f)
                .setBurnInfo(603f, 1000)
                .setMinGrowthTime(11).setDensity(0.7f, 2f)
                .setGenerator(GEN_WILLOW)
                .setBushes()
                .build();

        KAPOK = new WoodType
                .Builder("kapok")
                .setColor(0xAD879F).setTemp(15f, 35f).setRain(210f, 500f)
                .setBurnInfo(645f, 1000)
                .setDominance(8.5f).setRadius(3).setHeight(24).setDecayDist(6)
                .setMinGrowthTime(18).setDensity(0.6f, 2f)
                .setGenerator(GEN_ACACIA)
                .setBushes()
                .build();

        BANANA = new WoodType
                .Builder("banana")
                .setColor(0xBB9C7D).setTemp(23f, 35f).setRain(280f, 400f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.BANANA, 0.33f)
                .setFlowerMonth(Month.APRIL, 2)
                .setHarvestMonth(Month.SEPTEMBER, 1)
                .build();

        CHERRY = new WoodType
                .Builder("cherry")
                .setColor(0xDC5E2C).setTemp(5f, 21f).setRain(100f, 350f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.CHERRY, 0.33f)
                .setFlowerMonth(Month.APRIL, 1)
                .setHarvestMonth(Month.JUNE, 1)
                .build();

        GREEN_APPLE = new WoodType
                .Builder("green_apple")
                .setColor(0xCFC498).setTemp(8f, 25f).setRain(110f, 280f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.GREEN_APPLE, 0.33f)
                .setFlowerMonth(Month.MAY, 2)
                .setHarvestMonth(Month.OCTOBER, 2)
                .build();

        LEMON = new WoodType
                .Builder("lemon")
                .setColor(0xE8DD8C).setTemp(10f, 30f).setRain(180f, 400f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.LEMON, 0.33f)
                .setFlowerMonth(Month.MAY, 2)
                .setHarvestMonth(Month.AUGUST, 1)
                .build();

        OLIVE = new WoodType
                .Builder("olive")
                .setColor(0x97492B).setTemp(13f, 30f).setRain(150f, 380f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.OLIVE, 0.33f)
                .setFlowerMonth(Month.JUNE, 1)
                .setHarvestMonth(Month.OCTOBER, 2)
                .build();

        ORANGE = new WoodType
                .Builder("orange")
                .setColor(0xC18C52).setTemp(23f, 36f).setRain(250f, 400f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.ORANGE, 0.33f)
                .setFlowerMonth(Month.FEBRUARY, 3)
                .setHarvestMonth(Month.NOVEMBER, 1)
                .build();

        PEACH = new WoodType
                .Builder("peach")
                .setColor(0x9A5B37).setTemp(9f, 27f).setRain(60f, 230f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.PEACH, 0.33f)
                .setFlowerMonth(Month.APRIL, 2)
                .setHarvestMonth(Month.SEPTEMBER, 1)
                .build();

        PLUM = new WoodType
                .Builder("plum")
                .setColor(0x9F573B).setTemp(18f, 31f).setRain(250f, 400f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.PLUM, 0.33f)
                .setFlowerMonth(Month.MAY, 2)
                .setHarvestMonth(Month.JULY, 2)
                .build();

        RED_APPLE = new WoodType
                .Builder("red_apple")
                .setColor(0x6E150E).setTemp(9f, 25f).setRain(100f, 280f)
                .setBurnInfo(720f, 1750)
                .setHeight(5).setDecayDist(2)
                .setFruitTree(FoodTypes.RED_APPLE, 0.33f)
                .setFlowerMonth(Month.MAY, 2)
                .setHarvestMonth(Month.OCTOBER, 2)
                .build();
    }
}
