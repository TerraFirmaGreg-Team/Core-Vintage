package net.dries007.tfc.api.types.agriculture.crop.type;

import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.objects.items.ItemsTFC;

import static net.dries007.tfc.api.types.agriculture.crop.category.CropCategories.PICKABLE;
import static net.dries007.tfc.api.types.agriculture.crop.category.CropCategories.SIMPLE;
import static net.dries007.tfc.api.types.agriculture.crop.type.CropTypes.*;

public class CropTypeHandler {

    public static void init() {
        BARLEY = new CropType.Builder("barley")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.BARLEY)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        MAIZE = new CropType.Builder("maize")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.MAIZE)
                .setGrowthStages(6)
                .setGrowthTime(0.6f)
                .setTemp(10f, 19f, 40f, 45f)
                .setRain(110f, 140f, 400f, 450f)
                .build();

        OAT = new CropType.Builder("oat")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.OAT)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 3f, 30f, 34f)
                .setRain(50f, 100f, 350f, 400f)
                .build();

        RICE = new CropType.Builder("rice")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.RICE)
                .setGrowthStages(8)
                .setGrowthTime(0.6f)
                .setTemp(20f, 22f, 40f, 45f)
                .setRain(250f, 300f, 450f, 500f)
                .build();

        RYE = new CropType.Builder("rye")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.RYE)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 4f, 35f, 40f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        WHEAT = new CropType.Builder("wheat")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.WHEAT)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 3f, 30f, 34f)
                .setRain(50f, 100f, 350f, 400f)
                .build();

        BEET = new CropType.Builder("beet")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.BEET)
                .setGrowthStages(7)
                .setGrowthTime(0.6f)
                .setTemp(-5f, 0f, 20f, 25f)
                .setRain(50f, 70f, 300f, 320f)
                .build();

        CABBAGE = new CropType.Builder("cabbage")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.CABBAGE)
                .setGrowthStages(6)
                .setGrowthTime(0.6f)
                .setTemp(-10f, 0f, 27f, 33f)
                .setRain(50f, 60f, 280f, 300f)
                .build();

        CARROT = new CropType.Builder("carrot")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.CARROT)
                .setGrowthStages(5)
                .setGrowthTime(0.6f)
                .setTemp(3f, 10f, 30f, 36f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        GARLIC = new CropType.Builder("garlic")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.GARLIC)
                .setGrowthStages(5)
                .setGrowthTime(0.65f)
                .setTemp(-20f, -1f, 18f, 26f)
                .setRain(50f, 60f, 310f, 340f)
                .build();

        GREEN_BEAN = new CropType.Builder("green_bean")
                .setCategory(PICKABLE)
                .setDrop(FoodTypes.GREEN_BEAN)
                .setGrowthStages(7)
                .setGrowthTime(0.45f)
                .setTemp(2f, 9f, 35f, 41f)
                .setRain(70f, 150f, 410f, 450f)
                .build();

        ONION = new CropType.Builder("onion")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.ONION)
                .setGrowthStages(7)
                .setGrowthTime(0.4f)
                .setTemp(-1f, 10f, 37f, 40f)
                .setRain(70f, 200f, 410f, 450f)
                .build();

        POTATO = new CropType.Builder("potato")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.POTATO)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(0f, 4f, 30f, 35f)
                .setRain(50f, 100f, 390f, 440f)
                .build();

        SOYBEAN = new CropType.Builder("soybean")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.SOYBEAN)
                .setGrowthStages(7)
                .setGrowthTime(0.5f)
                .setTemp(8f, 12f, 30f, 36f)
                .setRain(55f, 160f, 410f, 450f)
                .build();

        SQUASH = new CropType.Builder("squash")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.SQUASH)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(5f, 14f, 33f, 37f)
                .setRain(45f, 90f, 390f, 440f)
                .build();

        SUGARCANE = new CropType.Builder("sugarcane")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.SUGARCANE)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(12f, 20f, 38f, 45f)
                .setRain(50f, 160f, 410f, 450f)
                .build();

        TOMATO = new CropType.Builder("tomato")
                .setCategory(PICKABLE)
                .setDrop(FoodTypes.TOMATO)
                .setGrowthStages(8)
                .setGrowthTime(0.45f)
                .setTemp(0f, 8f, 36f, 40f)
                .setRain(50f, 120f, 390f, 430f)
                .build();

        RED_BELL_PEPPER = new CropType.Builder("red_bell_pepper")
                .setCategory(PICKABLE)
                .setDrop(FoodTypes.RED_BELL_PEPPER)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(4f, 12f, 32f, 38f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        YELLOW_BELL_PEPPER = new CropType.Builder("yellow_bell_pepper")
                .setCategory(PICKABLE)
                .setDrop(FoodTypes.YELLOW_BELL_PEPPER)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(4f, 12f, 32f, 38f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        JUTE = new CropType.Builder("jute")
                .setCategory(SIMPLE)
                .setDrop(ItemsTFC.JUTE)
                .setGrowthStages(6)
                .setGrowthTime(0.5f)
                .setTemp(5f, 11f, 37f, 42f)
                .setRain(50f, 100f, 410f, 450f)
                .build();
    }
}
