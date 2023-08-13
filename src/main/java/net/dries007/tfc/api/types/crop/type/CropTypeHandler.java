package net.dries007.tfc.api.types.crop.type;

import net.dries007.tfc.api.types.crop.category.CropCategories;
import net.dries007.tfc.api.types.food.type.FoodVariants;
import net.dries007.tfc.common.objects.items.ItemsTFC;

public class CropTypeHandler {

    public static void init() {
        CropTypes.BARLEY = new CropType.Builder("barley")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.BARLEY)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        CropTypes.MAIZE = new CropType.Builder("maize")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.MAIZE)
                .setGrowthStages(6)
                .setGrowthTime(0.6f)
                .setTemp(10f, 19f, 40f, 45f)
                .setRain(110f, 140f, 400f, 450f)
                .build();

        CropTypes.OAT = new CropType.Builder("oat")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.OAT)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 3f, 30f, 34f)
                .setRain(50f, 100f, 350f, 400f)
                .build();

        CropTypes.RICE = new CropType.Builder("rice")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.RICE)
                .setGrowthStages(8)
                .setGrowthTime(0.6f)
                .setTemp(20f, 22f, 40f, 45f)
                .setRain(250f, 300f, 450f, 500f)
                .build();

        CropTypes.RYE = new CropType.Builder("rye")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.RYE)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 4f, 35f, 40f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropTypes.WHEAT = new CropType.Builder("wheat")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.WHEAT)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 3f, 30f, 34f)
                .setRain(50f, 100f, 350f, 400f)
                .build();

        CropTypes.BEET = new CropType.Builder("beet")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.BEET)
                .setGrowthStages(7)
                .setGrowthTime(0.6f)
                .setTemp(-5f, 0f, 20f, 25f)
                .setRain(50f, 70f, 300f, 320f)
                .build();

        CropTypes.CABBAGE = new CropType.Builder("cabbage")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.CABBAGE)
                .setGrowthStages(6)
                .setGrowthTime(0.6f)
                .setTemp(-10f, 0f, 27f, 33f)
                .setRain(50f, 60f, 280f, 300f)
                .build();

        CropTypes.CARROT = new CropType.Builder("carrot")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.CARROT)
                .setGrowthStages(5)
                .setGrowthTime(0.6f)
                .setTemp(3f, 10f, 30f, 36f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropTypes.GARLIC = new CropType.Builder("garlic")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.GARLIC)
                .setGrowthStages(5)
                .setGrowthTime(0.65f)
                .setTemp(-20f, -1f, 18f, 26f)
                .setRain(50f, 60f, 310f, 340f)
                .build();

        CropTypes.GREEN_BEAN = new CropType.Builder("green_bean")
                .setCategory(CropCategories.PICKABLE)
                .setDrop(FoodVariants.GREEN_BEAN)
                .setGrowthStages(7)
                .setGrowthTime(0.45f)
                .setTemp(2f, 9f, 35f, 41f)
                .setRain(70f, 150f, 410f, 450f)
                .build();

        CropTypes.ONION = new CropType.Builder("onion")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.ONION)
                .setGrowthStages(7)
                .setGrowthTime(0.4f)
                .setTemp(-1f, 10f, 37f, 40f)
                .setRain(70f, 200f, 410f, 450f)
                .build();

        CropTypes.POTATO = new CropType.Builder("potato")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.POTATO)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(0f, 4f, 30f, 35f)
                .setRain(50f, 100f, 390f, 440f)
                .build();

        CropTypes.SOYBEAN = new CropType.Builder("soybean")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.SOYBEAN)
                .setGrowthStages(7)
                .setGrowthTime(0.5f)
                .setTemp(8f, 12f, 30f, 36f)
                .setRain(55f, 160f, 410f, 450f)
                .build();

        CropTypes.SQUASH = new CropType.Builder("squash")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.SQUASH)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(5f, 14f, 33f, 37f)
                .setRain(45f, 90f, 390f, 440f)
                .build();

        CropTypes.SUGARCANE = new CropType.Builder("sugarcane")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(FoodVariants.SUGARCANE)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(12f, 20f, 38f, 45f)
                .setRain(50f, 160f, 410f, 450f)
                .build();

        CropTypes.TOMATO = new CropType.Builder("tomato")
                .setCategory(CropCategories.PICKABLE)
                .setDrop(FoodVariants.TOMATO)
                .setGrowthStages(8)
                .setGrowthTime(0.45f)
                .setTemp(0f, 8f, 36f, 40f)
                .setRain(50f, 120f, 390f, 430f)
                .build();

        CropTypes.RED_BELL_PEPPER = new CropType.Builder("red_bell_pepper")
                .setCategory(CropCategories.PICKABLE)
                .setDrop(FoodVariants.RED_BELL_PEPPER)
                .setDropEarly(FoodVariants.GREEN_BELL_PEPPER)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(4f, 12f, 32f, 38f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropTypes.YELLOW_BELL_PEPPER = new CropType.Builder("yellow_bell_pepper")
                .setCategory(CropCategories.PICKABLE)
                .setDrop(FoodVariants.YELLOW_BELL_PEPPER)
                .setDropEarly(FoodVariants.GREEN_BELL_PEPPER)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(4f, 12f, 32f, 38f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropTypes.JUTE = new CropType.Builder("jute")
                .setCategory(CropCategories.SIMPLE)
                .setDrop(ItemsTFC.JUTE)
                .setGrowthStages(6)
                .setGrowthTime(0.5f)
                .setTemp(5f, 11f, 37f, 42f)
                .setRain(50f, 100f, 410f, 450f)
                .build();
    }
}
