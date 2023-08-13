package net.dries007.tfc.api.types.food.type;

import static net.dries007.tfc.api.types.food.category.FoodCategories.*;
import static net.dries007.tfc.api.types.food.type.FoodVariants.*;

public class FoodVariantHandler {

    public static void init() {

        BANANA = new FoodVariant.Builder("banana")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f)
                .build();

        BLACKBERRY = new FoodVariant.Builder("blackberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        BLUEBERRY = new FoodVariant.Builder("blueberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        BUNCH_BERRY = new FoodVariant.Builder("bunch_berry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        CHERRY = new FoodVariant.Builder("cherry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f)
                .build();

        CLOUD_BERRY = new FoodVariant.Builder("cloud_berry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        CRANBERRY = new FoodVariant.Builder("cranberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 1.8f)
                .build();

        ELDERBERRY = new FoodVariant.Builder("elderberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
                .build();

        GOOSEBERRY = new FoodVariant.Builder("gooseberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        GREEN_APPLE = new FoodVariant.Builder("green_apple")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 2.5f)
                .setOreDictNames("apple")
                .build();

        LEMON = new FoodVariant.Builder("lemon")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f)
                .build();

        OLIVE = new FoodVariant.Builder("olive")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 1.6f)
                .build();

        ORANGE = new FoodVariant.Builder("orange")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f)
                .build();

        PEACH = new FoodVariant.Builder("peach")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f)
                .build();

        PLUM = new FoodVariant.Builder("plum")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f)
                .build();

        RASPBERRY = new FoodVariant.Builder("raspberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        RED_APPLE = new FoodVariant.Builder("red_apple")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f)
                .setOreDictNames("apple")
                .build();

        SNOW_BERRY = new FoodVariant.Builder("snow_berry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
                .build();

        STRAWBERRY = new FoodVariant.Builder("strawberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 4.9f)
                .build();

        WINTERGREEN_BERRY = new FoodVariant.Builder("wintergreen_berry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
                .build();

        BARLEY = new FoodVariant.Builder("barley")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("barley")
                .build();

        BARLEY_GRAIN = new FoodVariant.Builder("barley_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_barley", "grain")
                .build();

        BARLEY_FLOUR = new FoodVariant.Builder("barley_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_barley", "flour")
                .build();

        BARLEY_DOUGH = new FoodVariant.Builder("barley_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        BARLEY_BREAD = new FoodVariant.Builder("barley_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        MAIZE = new FoodVariant.Builder("maize")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("maize")
                .build();

        MAIZE_GRAIN = new FoodVariant.Builder("maize_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_maize", "grain")
                .build();

        CORNBREAD = new FoodVariant.Builder("cornbread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        CORNMEAL_FLOUR = new FoodVariant.Builder("cornmeal_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_maize", "flour")
                .build();

        CORNMEAL_DOUGH = new FoodVariant.Builder("cornmeal_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        OAT = new FoodVariant.Builder("oat")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("oat")
                .build();

        OAT_GRAIN = new FoodVariant.Builder("oat_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_oat", "grain")
                .build();

        OAT_FLOUR = new FoodVariant.Builder("oat_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_oat", "flour")
                .build();

        OAT_DOUGH = new FoodVariant.Builder("oat_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        OAT_BREAD = new FoodVariant.Builder("oat_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        RICE = new FoodVariant.Builder("rice")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("rice")
                .build();

        RICE_GRAIN = new FoodVariant.Builder("rice_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_rice", "grain")
                .build();

        RICE_FLOUR = new FoodVariant.Builder("rice_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_rice", "flour")
                .build();

        RICE_DOUGH = new FoodVariant.Builder("rice_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        RICE_BREAD = new FoodVariant.Builder("rice_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        RYE = new FoodVariant.Builder("rye")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("rye")
                .build();

        RYE_GRAIN = new FoodVariant.Builder("rye_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_rye", "grain")
                .build();

        RYE_FLOUR = new FoodVariant.Builder("rye_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_rye", "flour")
                .build();

        RYE_DOUGH = new FoodVariant.Builder("rye_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        RYE_BREAD = new FoodVariant.Builder("rye_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        WHEAT = new FoodVariant.Builder("wheat")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("wheat")
                .build();

        WHEAT_GRAIN = new FoodVariant.Builder("wheat_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_wheat", "grain")
                .build();

        WHEAT_FLOUR = new FoodVariant.Builder("wheat_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_wheat", "flour")
                .build();

        WHEAT_DOUGH = new FoodVariant.Builder("wheat_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        WHEAT_BREAD = new FoodVariant.Builder("wheat_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        BEET = new FoodVariant.Builder("beet")
                .setCategory(VEGETABLE)
                .setFoodData(4, 2f, 0f, 0f, 1f, 0f, 0f, 0f, 0.7f)
                .build();

        CABBAGE = new FoodVariant.Builder("cabbage")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 1.2f)
                .build();

        CARROT = new FoodVariant.Builder("carrot")
                .setCategory(VEGETABLE)
                .setFoodData(4, 2f, 0f, 0f, 1f, 0f, 0f, 0f, 0.7f)
                .setOreDictNames("carrot")
                .build();

        GARLIC = new FoodVariant.Builder("garlic")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 2f, 0f, 0f, 0f, 0.4f)
                .build();

        GREEN_BEAN = new FoodVariant.Builder("green_bean")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 3.5f)
                .build();

        GREEN_BELL_PEPPER = new FoodVariant.Builder("green_bell_pepper")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 2.7f)
                .build();

        ONION = new FoodVariant.Builder("onion")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 0.5f)
                .build();

        POTATO = new FoodVariant.Builder("potato")
                .setCategory(VEGETABLE)
                .setFoodData(4, 2f, 0f, 0f, 1.5f, 0f, 0f, 0f, 0.666f)
                .build();

        RED_BELL_PEPPER = new FoodVariant.Builder("red_bell_pepper")
                .setCategory(VEGETABLE)
                .setFoodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
                .build();

        SEAWEED = new FoodVariant.Builder("seaweed")
                .setCategory(VEGETABLE)
                .setFoodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
                .build();

        SOYBEAN = new FoodVariant.Builder("soybean")
                .setCategory(VEGETABLE)
                .setFoodData(4, 2f, 0f, 0f, 0.5f, 0f, 1f, 0f, 2.5f)
                .build();

        SQUASH = new FoodVariant.Builder("squash")
                .setCategory(VEGETABLE)
                .setFoodData(4, 1f, 0f, 0f, 1.5f, 0f, 0f, 0f, 1.67f)
                .build();

        TOMATO = new FoodVariant.Builder("tomato")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 5f, 0f, 1.5f, 0f, 0f, 0f, 3.5f)
                .build();

        YELLOW_BELL_PEPPER = new FoodVariant.Builder("yellow_bell_pepper")
                .setCategory(VEGETABLE)
                .setFoodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
                .build();

        CHEESE = new FoodVariant.Builder("cheese")
                .setCategory(DAIRY)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 0f, 3f, 0.3f)
                .build();

        COOKED_EGG = new FoodVariant.Builder("cooked_egg")
                .setCategory(OTHER)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0.75f, 0.25f, 4f)
                .build();

        SUGARCANE = new FoodVariant.Builder("sugarcane")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 1.6f)
                .build();

        BEEF = new FoodVariant.Builder("beef")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 2f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        PORK = new FoodVariant.Builder("pork")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        CHICKEN = new FoodVariant.Builder("chicken")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        MUTTON = new FoodVariant.Builder("mutton")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        FISH = new FoodVariant.Builder("fish")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        BEAR = new FoodVariant.Builder("bear")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        CALAMARI = new FoodVariant.Builder("calamari")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        HORSE_MEAT = new FoodVariant.Builder("horse_meat")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        PHEASANT = new FoodVariant.Builder("pheasant")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        VENISON = new FoodVariant.Builder("venison")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        WOLF = new FoodVariant.Builder("wolf")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        RABBIT = new FoodVariant.Builder("rabbit")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        MONGOOSE = new FoodVariant.Builder("mongoose")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        GRAN_FELINE = new FoodVariant.Builder("gran_feline")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        CAMELIDAE = new FoodVariant.Builder("camelidae")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        COOKED_BEEF = new FoodVariant.Builder("cooked_beef")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
                .build();

        COOKED_PORK = new FoodVariant.Builder("cooked_pork")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
                .build();

        COOKED_CHICKEN = new FoodVariant.Builder("cooked_chicken")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        COOKED_MUTTON = new FoodVariant.Builder("cooked_mutton")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        COOKED_FISH = new FoodVariant.Builder("cooked_fish")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 2f, 0f, 2.25f)
                .build();

        COOKED_BEAR = new FoodVariant.Builder("cooked_bear")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
                .build();

        COOKED_CALAMARI = new FoodVariant.Builder("cooked_calamari")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
                .build();

        COOKED_HORSE_MEAT = new FoodVariant.Builder("cooked_horse_meat")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
                .build();

        COOKED_PHEASANT = new FoodVariant.Builder("cooked_pheasant")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        COOKED_VENISON = new FoodVariant.Builder("cooked_venison")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 2f, 0f, 1.5f)
                .build();

        COOKED_WOLF = new FoodVariant.Builder("cooked_wolf")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
                .build();

        COOKED_RABBIT = new FoodVariant.Builder("cooked_rabbit")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
                .build();

        COOKED_MONGOOSE = new FoodVariant.Builder("cooked_mongoose")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
                .build();

        COOKED_GRAN_FELINE = new FoodVariant.Builder("cooked_gran_feline")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        COOKED_CAMELIDAE = new FoodVariant.Builder("cooked_camelidae")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        BARLEY_BREAD_SANDWICH = new FoodVariant.Builder("barley_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        CORNBREAD_SANDWICH = new FoodVariant.Builder("cornbread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        OAT_BREAD_SANDWICH = new FoodVariant.Builder("oat_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        RICE_BREAD_SANDWICH = new FoodVariant.Builder("rice_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        RYE_BREAD_SANDWICH = new FoodVariant.Builder("rye_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        WHEAT_BREAD_SANDWICH = new FoodVariant.Builder("wheat_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        SOUP_GRAIN = new FoodVariant.Builder("soup_grain")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SOUP_FRUIT = new FoodVariant.Builder("soup_fruit")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SOUP_VEGETABLE = new FoodVariant.Builder("soup_vegetable")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SOUP_MEAT = new FoodVariant.Builder("soup_meat")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SOUP_DAIRY = new FoodVariant.Builder("soup_dairy")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SALAD_GRAIN = new FoodVariant.Builder("salad_grain")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

        SALAD_FRUIT = new FoodVariant.Builder("salad_fruit")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

        SALAD_VEGETABLE = new FoodVariant.Builder("salad_vegetable")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

        SALAD_MEAT = new FoodVariant.Builder("salad_meat")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

        SALAD_DAIRY = new FoodVariant.Builder("salad_dairy")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

    }
}
