package net.dries007.tfc.api.types.agriculture.crop.type;

import net.dries007.tfc.api.types.agriculture.crop.category.CropCategories;
import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.objects.items.ItemsTFC;

import static net.dries007.tfc.api.types.agriculture.crop.category.CropCategories.*;
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
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        OAT = new CropType.Builder("oat")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.OAT)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        RICE = new CropType.Builder("rice")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.RICE)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        RYE = new CropType.Builder("rye")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.RYE)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        WHEAT = new CropType.Builder("wheat")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.WHEAT)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        BEET = new CropType.Builder("beet")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.BEET)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        CABBAGE = new CropType.Builder("cabbage")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.CABBAGE)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        CARROT = new CropType.Builder("carrot")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.CARROT)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        GARLIC = new CropType.Builder("garlic")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.GARLIC)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        GREEN_BEAN = new CropType.Builder("green_bean")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.GREEN_BEAN)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        ONION = new CropType.Builder("onion")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.ONION)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        POTATO = new CropType.Builder("potato")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.POTATO)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        SOYBEAN = new CropType.Builder("soybean")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.SOYBEAN)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        SQUASH = new CropType.Builder("squash")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.SQUASH)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        SUGARCANE = new CropType.Builder("sugarcane")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.SUGARCANE)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        TOMATO = new CropType.Builder("tomato")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.TOMATO)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        RED_BELL_PEPPER = new CropType.Builder("red_bell_pepper")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.RED_BELL_PEPPER)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        YELLOW_BELL_PEPPER = new CropType.Builder("yellow_bell_pepper")
                .setCategory(SIMPLE)
                .setDrop(FoodTypes.YELLOW_BELL_PEPPER)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();

        JUTE = new CropType.Builder("jute")
                .setCategory(SIMPLE)
                .setDrop(ItemsTFC.JUTE)
                .setGrowthStages(8)
                .setGrowthTime(0.4f)
                .setTemp(0f, 1f, 26f, 33f)
                .setRain(50f, 70f, 310f, 330f)
                .build();
    }
}
