package net.dries007.tfc.api.types.crop.type;

import net.dries007.tfc.api.types.crop.category.CropTypes;
import net.dries007.tfc.api.types.food.variant.FoodVariants;
import net.dries007.tfc.common.objects.items.ItemsTFC;

public class CropBlockVariantHandler {

    public static void init() {
        CropBlockVariants.BARLEY = new CropBlockVariant.Builder("barley")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.BARLEY)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        CropBlockVariants.MAIZE = new CropBlockVariant.Builder("maize")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.MAIZE)
                .setGrowthStages(6)
                .setGrowthTime(0.6f)
                .setTemp(10f, 19f, 40f, 45f)
                .setRain(110f, 140f, 400f, 450f)
                .build();

        CropBlockVariants.OAT = new CropBlockVariant.Builder("oat")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.OAT)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 3f, 30f, 34f)
                .setRain(50f, 100f, 350f, 400f)
                .build();

        CropBlockVariants.RICE = new CropBlockVariant.Builder("rice")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.RICE)
                .setGrowthStages(8)
                .setGrowthTime(0.6f)
                .setTemp(20f, 22f, 40f, 45f)
                .setRain(250f, 300f, 450f, 500f)
                .build();

        CropBlockVariants.RYE = new CropBlockVariant.Builder("rye")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.RYE)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 4f, 35f, 40f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropBlockVariants.WHEAT = new CropBlockVariant.Builder("wheat")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.WHEAT)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(0f, 3f, 30f, 34f)
                .setRain(50f, 100f, 350f, 400f)
                .build();

        CropBlockVariants.BEET = new CropBlockVariant.Builder("beet")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.BEET)
                .setGrowthStages(7)
                .setGrowthTime(0.6f)
                .setTemp(-5f, 0f, 20f, 25f)
                .setRain(50f, 70f, 300f, 320f)
                .build();

        CropBlockVariants.CABBAGE = new CropBlockVariant.Builder("cabbage")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.CABBAGE)
                .setGrowthStages(6)
                .setGrowthTime(0.6f)
                .setTemp(-10f, 0f, 27f, 33f)
                .setRain(50f, 60f, 280f, 300f)
                .build();

        CropBlockVariants.CARROT = new CropBlockVariant.Builder("carrot")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.CARROT)
                .setGrowthStages(5)
                .setGrowthTime(0.6f)
                .setTemp(3f, 10f, 30f, 36f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropBlockVariants.GARLIC = new CropBlockVariant.Builder("garlic")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.GARLIC)
                .setGrowthStages(5)
                .setGrowthTime(0.65f)
                .setTemp(-20f, -1f, 18f, 26f)
                .setRain(50f, 60f, 310f, 340f)
                .build();

        CropBlockVariants.GREEN_BEAN = new CropBlockVariant.Builder("green_bean")
                .setType(CropTypes.PICKABLE)
                .setDrop(FoodVariants.GREEN_BEAN)
                .setGrowthStages(7)
                .setGrowthTime(0.45f)
                .setTemp(2f, 9f, 35f, 41f)
                .setRain(70f, 150f, 410f, 450f)
                .build();

        CropBlockVariants.ONION = new CropBlockVariant.Builder("onion")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.ONION)
                .setGrowthStages(7)
                .setGrowthTime(0.4f)
                .setTemp(-1f, 10f, 37f, 40f)
                .setRain(70f, 200f, 410f, 450f)
                .build();

        CropBlockVariants.POTATO = new CropBlockVariant.Builder("potato")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.POTATO)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(0f, 4f, 30f, 35f)
                .setRain(50f, 100f, 390f, 440f)
                .build();

        CropBlockVariants.SOYBEAN = new CropBlockVariant.Builder("soybean")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.SOYBEAN)
                .setGrowthStages(7)
                .setGrowthTime(0.5f)
                .setTemp(8f, 12f, 30f, 36f)
                .setRain(55f, 160f, 410f, 450f)
                .build();

        CropBlockVariants.SQUASH = new CropBlockVariant.Builder("squash")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.SQUASH)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(5f, 14f, 33f, 37f)
                .setRain(45f, 90f, 390f, 440f)
                .build();

        CropBlockVariants.SUGARCANE = new CropBlockVariant.Builder("sugarcane")
                .setType(CropTypes.SIMPLE)
                .setDrop(FoodVariants.SUGARCANE)
                .setGrowthStages(8)
                .setGrowthTime(0.5f)
                .setTemp(12f, 20f, 38f, 45f)
                .setRain(50f, 160f, 410f, 450f)
                .build();

        CropBlockVariants.TOMATO = new CropBlockVariant.Builder("tomato")
                .setType(CropTypes.PICKABLE)
                .setDrop(FoodVariants.TOMATO)
                .setGrowthStages(8)
                .setGrowthTime(0.45f)
                .setTemp(0f, 8f, 36f, 40f)
                .setRain(50f, 120f, 390f, 430f)
                .build();

        CropBlockVariants.RED_BELL_PEPPER = new CropBlockVariant.Builder("red_bell_pepper")
                .setType(CropTypes.PICKABLE)
                .setDrop(FoodVariants.RED_BELL_PEPPER)
                .setDropEarly(FoodVariants.GREEN_BELL_PEPPER)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(4f, 12f, 32f, 38f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropBlockVariants.YELLOW_BELL_PEPPER = new CropBlockVariant.Builder("yellow_bell_pepper")
                .setType(CropTypes.PICKABLE)
                .setDrop(FoodVariants.YELLOW_BELL_PEPPER)
                .setDropEarly(FoodVariants.GREEN_BELL_PEPPER)
                .setGrowthStages(7)
                .setGrowthTime(0.55f)
                .setTemp(4f, 12f, 32f, 38f)
                .setRain(50f, 100f, 400f, 450f)
                .build();

        CropBlockVariants.JUTE = new CropBlockVariant.Builder("jute")
                .setType(CropTypes.SIMPLE)
                .setDrop(ItemsTFC.JUTE)
                .setGrowthStages(6)
                .setGrowthTime(0.5f)
                .setTemp(5f, 11f, 37f, 42f)
                .setRain(50f, 100f, 410f, 450f)
                .build();
    }
}
