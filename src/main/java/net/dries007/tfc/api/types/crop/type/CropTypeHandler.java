package net.dries007.tfc.api.types.crop.type;

import net.dries007.tfc.api.types.crop.category.CropCategories;
import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.common.objects.items.ItemsTFC;

public class CropTypeHandler {

    public static void init() {
        CropTypes.BARLEY = new CropType.Builder("barley")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.BARLEY)
                .setSeed()
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        CropTypes.MAIZE = new CropType.Builder("maize")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.MAIZE)
                .setGrowthTime(0.6f)
                .setTemp(10f, 19f, 40f, 45f)
                .setRain(110f, 140f, 400f, 450f)
                .build();

        CropTypes.OAT = new CropType.Builder("oat")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.OAT)
                .setGrowthTime(0.5f)
                .setTemp(0f, 3f, 30f, 34f)
                .setRain(50f, 100f, 350f, 400f)
                .build();

        CropTypes.RICE = new CropType.Builder("rice")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.RICE)
                .setGrowthTime(0.6f)
                .setTemp(20f, 22f, 40f, 45f)
                .setRain(250f, 300f, 450f, 500f)
                .build();

        CropTypes.RYE = new CropType.Builder("rye")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.RYE)
                .setGrowthTime(0.5f)
                .setTemp(0f, 4f, 35f, 40f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropTypes.WHEAT = new CropType.Builder("wheat")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.WHEAT)
                .setGrowthTime(0.5f)
                .setTemp(0f, 3f, 30f, 34f)
                .setRain(50f, 100f, 350f, 400f)
                .build();

        CropTypes.BEET = new CropType.Builder("beet")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.BEET)
                .setGrowthTime(0.6f)
                .setTemp(-5f, 0f, 20f, 25f)
                .setRain(50f, 70f, 300f, 320f)
                .build();

        CropTypes.CABBAGE = new CropType.Builder("cabbage")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.CABBAGE)
                .setGrowthTime(0.6f)
                .setTemp(-10f, 0f, 27f, 33f)
                .setRain(50f, 60f, 280f, 300f)
                .build();

        CropTypes.CARROT = new CropType.Builder("carrot")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.CARROT)
                .setGrowthTime(0.6f)
                .setTemp(3f, 10f, 30f, 36f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropTypes.GARLIC = new CropType.Builder("garlic")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.GARLIC)
                .setGrowthTime(0.65f)
                .setTemp(-20f, -1f, 18f, 26f)
                .setRain(50f, 60f, 310f, 340f)
                .build();

        CropTypes.GREEN_BEAN = new CropType.Builder("green_bean")
                .setCategory(CropCategories.PICKABLE)
                .setFoodDrop(FoodTypes.GREEN_BEAN)
                .setGrowthTime(0.45f)
                .setTemp(2f, 9f, 35f, 41f)
                .setRain(70f, 150f, 410f, 450f)
                .build();

        CropTypes.ONION = new CropType.Builder("onion")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.ONION)
                .setGrowthTime(0.4f)
                .setTemp(-1f, 10f, 37f, 40f)
                .setRain(70f, 200f, 410f, 450f)
                .build();

        CropTypes.POTATO = new CropType.Builder("potato")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.POTATO)
                .setGrowthTime(0.55f)
                .setTemp(0f, 4f, 30f, 35f)
                .setRain(50f, 100f, 390f, 440f)
                .build();

        CropTypes.SOYBEAN = new CropType.Builder("soybean")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.SOYBEAN)
                .setGrowthTime(0.5f)
                .setTemp(8f, 12f, 30f, 36f)
                .setRain(55f, 160f, 410f, 450f)
                .build();

        CropTypes.SQUASH = new CropType.Builder("squash")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.SQUASH)
                .setGrowthTime(0.5f)
                .setTemp(5f, 14f, 33f, 37f)
                .setRain(45f, 90f, 390f, 440f)
                .build();

        CropTypes.SUGARCANE = new CropType.Builder("sugarcane")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(FoodTypes.SUGARCANE)
                .setGrowthTime(0.5f)
                .setTemp(12f, 20f, 38f, 45f)
                .setRain(50f, 160f, 410f, 450f)
                .build();

        CropTypes.TOMATO = new CropType.Builder("tomato")
                .setCategory(CropCategories.PICKABLE)
                .setFoodDrop(FoodTypes.TOMATO)
                .setGrowthTime(0.45f)
                .setTemp(0f, 8f, 36f, 40f)
                .setRain(50f, 120f, 390f, 430f)
                .build();

        CropTypes.RED_BELL_PEPPER = new CropType.Builder("red_bell_pepper")
                .setCategory(CropCategories.PICKABLE)
                .setFoodDrop(FoodTypes.RED_BELL_PEPPER)
                .setGrowthTime(0.55f)
                .setTemp(4f, 12f, 32f, 38f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropTypes.YELLOW_BELL_PEPPER = new CropType.Builder("yellow_bell_pepper")
                .setCategory(CropCategories.PICKABLE)
                .setFoodDrop(FoodTypes.YELLOW_BELL_PEPPER)
                .setGrowthTime(0.55f)
                .setTemp(4f, 12f, 32f, 38f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropTypes.JUTE = new CropType.Builder("jute")
                .setCategory(CropCategories.SIMPLE)
                .setFoodDrop(ItemsTFC.JUTE)
                .setGrowthTime(0.5f)
                .setTemp(5f, 11f, 37f, 42f)
                .setRain(50f, 100f, 410f, 450f)
                .build();
    }
}
