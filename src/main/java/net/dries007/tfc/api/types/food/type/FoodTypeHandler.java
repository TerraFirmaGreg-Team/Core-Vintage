package net.dries007.tfc.api.types.food.type;

import static net.dries007.tfc.api.types.food.category.FoodCategories.*;
import static net.dries007.tfc.api.types.food.type.FoodTypes.*;

public class FoodTypeHandler {

    public static void init() {

        BANANA = new FoodType
                .Builder("banana")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f)
                .build();

        BLACKBERRY = new FoodType
                .Builder("blackberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        BLUEBERRY = new FoodType
                .Builder("blueberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        BUNCH_BERRY = new FoodType
                .Builder("bunch_berry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        CHERRY = new FoodType
                .Builder("cherry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f)
                .build();

        CLOUD_BERRY = new FoodType
                .Builder("cloud_berry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        CRANBERRY = new FoodType
                .Builder("cranberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 1.8f)
                .build();

        ELDERBERRY = new FoodType
                .Builder("elderberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
                .build();

        GOOSEBERRY = new FoodType
                .Builder("gooseberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        GREEN_APPLE = new FoodType
                .Builder("green_apple")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 2.5f)
                .setOreDictNames("apple")
                .build();

        LEMON = new FoodType
                .Builder("lemon")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f)
                .build();

        OLIVE = new FoodType
                .Builder("olive")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 1.6f)
                .build();

        ORANGE = new FoodType
                .Builder("orange")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f)
                .build();

        PEACH = new FoodType
                .Builder("peach")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f)
                .build();

        PLUM = new FoodType
                .Builder("plum")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f)
                .build();

        RASPBERRY = new FoodType
                .Builder("raspberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
                .build();

        RED_APPLE = new FoodType
                .Builder("red_apple")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f)
                .setOreDictNames("apple")
                .build();

        SNOW_BERRY = new FoodType
                .Builder("snow_berry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
                .build();

        STRAWBERRY = new FoodType
                .Builder("strawberry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 4.9f)
                .build();

        WINTERGREEN_BERRY = new FoodType
                .Builder("wintergreen_berry")
                .setCategory(FRUIT)
                .setFoodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
                .build();

        BARLEY = new FoodType
                .Builder("barley")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("barley")
                .build();

        BARLEY_GRAIN = new FoodType
                .Builder("barley_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_barley", "grain")
                .build();

        BARLEY_FLOUR = new FoodType
                .Builder("barley_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_barley", "flour")
                .build();

        BARLEY_DOUGH = new FoodType
                .Builder("barley_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        BARLEY_BREAD = new FoodType
                .Builder("barley_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        MAIZE = new FoodType
                .Builder("maize")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("maize")
                .build();

        MAIZE_GRAIN = new FoodType
                .Builder("maize_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_maize", "grain")
                .build();

        CORNBREAD = new FoodType
                .Builder("cornbread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        CORNMEAL_FLOUR = new FoodType
                .Builder("cornmeal_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_maize", "flour")
                .build();

        CORNMEAL_DOUGH = new FoodType
                .Builder("cornmeal_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        OAT = new FoodType
                .Builder("oat")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("oat")
                .build();

        OAT_GRAIN = new FoodType
                .Builder("oat_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_oat", "grain")
                .build();

        OAT_FLOUR = new FoodType
                .Builder("oat_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_oat", "flour")
                .build();

        OAT_DOUGH = new FoodType
                .Builder("oat_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        OAT_BREAD = new FoodType
                .Builder("oat_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        RICE = new FoodType
                .Builder("rice")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("rice")
                .build();

        RICE_GRAIN = new FoodType
                .Builder("rice_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_rice", "grain")
                .build();

        RICE_FLOUR  = new FoodType
                .Builder("rice_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_rice", "flour")
                .build();

        RICE_DOUGH = new FoodType
                .Builder("rice_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        RICE_BREAD  = new FoodType
                .Builder("rice_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        RYE = new FoodType
                .Builder("rye")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("rye")
                .build();

        RYE_GRAIN = new FoodType
                .Builder("rye_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_rye", "grain")
                .build();

        RYE_FLOUR = new FoodType
                .Builder("rye_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_rye", "flour")
                .build();

        RYE_DOUGH = new FoodType
                .Builder("rye_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        RYE_BREAD = new FoodType
                .Builder("rye_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        WHEAT = new FoodType
                .Builder("wheat")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
                .setOreDictNames("wheat")
                .build();

        WHEAT_GRAIN = new FoodType
                .Builder("wheat_grain")
                .setCategory(GRAIN)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
                .setOreDictNames("grain_wheat", "grain")
                .build();

        WHEAT_FLOUR = new FoodType
                .Builder("wheat_flour")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
                .setOreDictNames("flour_wheat", "flour")
                .build();

        WHEAT_DOUGH = new FoodType
                .Builder("wheat_dough")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        WHEAT_BREAD = new FoodType
                .Builder("wheat_bread")
                .setCategory(BREAD)
                .setFoodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
                .setHeatCapacity(1f)
                .setCookingTemp(480f)
                .build();

        BEET = new FoodType
                .Builder("beet")
                .setCategory(VEGETABLE)
                .setFoodData(4, 2f, 0f, 0f, 1f, 0f, 0f, 0f, 0.7f)
                .build();

        CABBAGE = new FoodType
                .Builder("cabbage")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 1.2f)
                .build();

        CARROT = new FoodType
                .Builder("carrot")
                .setCategory(VEGETABLE)
                .setFoodData(4, 2f, 0f, 0f, 1f, 0f, 0f, 0f, 0.7f)
                .setOreDictNames("carrot")
                .build();

        GARLIC = new FoodType
                .Builder("garlic")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 2f, 0f, 0f, 0f, 0.4f)
                .build();

        GREEN_BEAN = new FoodType
                .Builder("green_bean")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 3.5f)
                .build();

        GREEN_BELL_PEPPER = new FoodType
                .Builder("green_bell_pepper")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 2.7f)
                .build();

        ONION = new FoodType
                .Builder("onion")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 0.5f)
                .build();

        POTATO = new FoodType
                .Builder("potato")
                .setCategory(VEGETABLE)
                .setFoodData(4, 2f, 0f, 0f, 1.5f, 0f, 0f, 0f, 0.666f)
                .build();

        RED_BELL_PEPPER = new FoodType
                .Builder("red_bell_pepper")
                .setCategory(VEGETABLE)
                .setFoodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
                .build();

        SEAWEED = new FoodType
                .Builder("seaweed")
                .setCategory(VEGETABLE)
                .setFoodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
                .build();

        SOYBEAN = new FoodType
                .Builder("soybean")
                .setCategory(VEGETABLE)
                .setFoodData(4, 2f, 0f, 0f, 0.5f, 0f, 1f, 0f, 2.5f)
                .build();

        SQUASH = new FoodType
                .Builder("squash")
                .setCategory(VEGETABLE)
                .setFoodData(4, 1f, 0f, 0f, 1.5f, 0f, 0f, 0f, 1.67f)
                .build();

        TOMATO = new FoodType
                .Builder("tomato")
                .setCategory(VEGETABLE)
                .setFoodData(4, 0.5f, 5f, 0f, 1.5f, 0f, 0f, 0f, 3.5f)
                .build();

        YELLOW_BELL_PEPPER = new FoodType
                .Builder("yellow_bell_pepper")
                .setCategory(VEGETABLE)
                .setFoodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
                .build();

        CHEESE = new FoodType
                .Builder("cheese")
                .setCategory(DAIRY)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 0f, 3f, 0.3f)
                .build();

        COOKED_EGG = new FoodType
                .Builder("cooked_egg")
                .setCategory(OTHER)
                .setFoodData(4, 0.5f, 0f, 0f, 0f, 0f, 0.75f, 0.25f, 4f)
                .build();

        SUGARCANE = new FoodType
                .Builder("sugarcane")
                .setCategory(GRAIN)
                .setFoodData(4, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 1.6f)
                .build();

        BEEF = new FoodType
                .Builder("beef")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 2f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        PORK = new FoodType
                .Builder("pork")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        CHICKEN = new FoodType
                .Builder("chicken")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        MUTTON = new FoodType
                .Builder("mutton")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        FISH = new FoodType
                .Builder("fish")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        BEAR = new FoodType
                .Builder("bear")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        CALAMARI = new FoodType
                .Builder("calamari")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        HORSE_MEAT = new FoodType
                .Builder("horse_meat")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        PHEASANT = new FoodType
                .Builder("pheasant")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        VENISON = new FoodType
                .Builder("venison")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 2f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        WOLF = new FoodType
                .Builder("wolf")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        RABBIT = new FoodType
                .Builder("rabbit")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        MONGOOSE = new FoodType
                .Builder("mongoose")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        GRAN_FELINE = new FoodType
                .Builder("gran_feline")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        CAMELIDAE = new FoodType
                .Builder("camelidae")
                .setCategory(MEAT)
                .setFoodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
                .setHeatCapacity(1f)
                .setCookingTemp(200f)
                .build();

        COOKED_BEEF = new FoodType
                .Builder("cooked_beef")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
                .build();

        COOKED_PORK = new FoodType
                .Builder("cooked_pork")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
                .build();

        COOKED_CHICKEN = new FoodType
                .Builder("cooked_chicken")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        COOKED_MUTTON = new FoodType
                .Builder("cooked_mutton")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        COOKED_FISH = new FoodType
                .Builder("cooked_fish")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 2f, 0f, 2.25f)
                .build();

        COOKED_BEAR = new FoodType
                .Builder("cooked_bear")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
                .build();

        COOKED_CALAMARI = new FoodType
                .Builder("cooked_calamari")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
                .build();

        COOKED_HORSE_MEAT = new FoodType
                .Builder("cooked_horse_meat")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
                .build();

        COOKED_PHEASANT = new FoodType
                .Builder("cooked_pheasant")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        COOKED_VENISON = new FoodType
                .Builder("cooked_venison")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 2f, 0f, 1.5f)
                .build();

        COOKED_WOLF = new FoodType
                .Builder("cooked_wolf")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
                .build();

        COOKED_RABBIT = new FoodType
                .Builder("cooked_rabbit")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
                .build();

        COOKED_MONGOOSE = new FoodType
                .Builder("cooked_mongoose")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
                .build();

        COOKED_GRAN_FELINE = new FoodType
                .Builder("cooked_gran_feline")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        COOKED_CAMELIDAE = new FoodType
                .Builder("cooked_camelidae")
                .setCategory(COOKED_MEAT)
                .setFoodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
                .build();

        BARLEY_BREAD_SANDWICH = new FoodType
                .Builder("barley_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        CORNBREAD_SANDWICH = new FoodType
                .Builder("cornbread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        OAT_BREAD_SANDWICH = new FoodType
                .Builder("oat_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        RICE_BREAD_SANDWICH = new FoodType
                .Builder("rice_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        RYE_BREAD_SANDWICH = new FoodType
                .Builder("rye_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        WHEAT_BREAD_SANDWICH = new FoodType
                .Builder("wheat_bread_sandwich")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
                .setOreDictNames("sandwich")
                .build();

        SOUP_GRAIN = new FoodType
                .Builder("soup_grain")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SOUP_FRUIT = new FoodType
                .Builder("soup_fruit")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SOUP_VEGETABLE = new FoodType
                .Builder("soup_vegetable")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SOUP_MEAT = new FoodType
                .Builder("soup_meat")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SOUP_DAIRY = new FoodType
                .Builder("soup_dairy")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
                .setOreDictNames("soup")
                .build();

        SALAD_GRAIN = new FoodType
                .Builder("salad_grain")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

        SALAD_FRUIT = new FoodType
                .Builder("salad_fruit")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

        SALAD_VEGETABLE = new FoodType
                .Builder("salad_vegetable")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

        SALAD_MEAT = new FoodType
                .Builder("salad_meat")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

        SALAD_DAIRY = new FoodType
                .Builder("salad_dairy")
                .setCategory(MEAL)
                .setFoodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
                .setOreDictNames("salad")
                .build();

    }
}
