package su.terrafirmagreg.modules.food.api.types.type;

import su.terrafirmagreg.modules.food.api.types.category.FoodCategories;

public final class FoodTypeHandler {

  public static void init() {

    FoodTypes.BANANA = FoodType
      .builder("banana")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f)
      .build();

    FoodTypes.BLACKBERRY = FoodType
      .builder("blackberry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.BLUEBERRY = FoodType
      .builder("blueberry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.BUNCH_BERRY = FoodType
      .builder("bunch_berry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.CHERRY = FoodType
      .builder("cherry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f)
      .build();

    FoodTypes.CLOUD_BERRY = FoodType
      .builder("cloud_berry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.CRANBERRY = FoodType
      .builder("cranberry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 1.8f)
      .build();

    FoodTypes.ELDERBERRY = FoodType
      .builder("elderberry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.GOOSEBERRY = FoodType
      .builder("gooseberry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.GREEN_APPLE = FoodType
      .builder("green_apple")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 2.5f)
      .addOreDict("apple")
      .build();

    FoodTypes.LEMON = FoodType
      .builder("lemon")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f)
      .build();

    FoodTypes.OLIVE = FoodType
      .builder("olive")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 1.6f)
      .build();

    FoodTypes.ORANGE = FoodType
      .builder("orange")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f)
      .build();

    FoodTypes.PEACH = FoodType
      .builder("peach")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f)
      .build();

    FoodTypes.PLUM = FoodType
      .builder("plum")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f)
      .build();

    FoodTypes.RASPBERRY = FoodType
      .builder("raspberry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.RED_APPLE = FoodType
      .builder("red_apple")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f)
      .addOreDict("apple")
      .build();

    FoodTypes.SNOW_BERRY = FoodType
      .builder("snow_berry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.STRAWBERRY = FoodType
      .builder("strawberry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.WINTERGREEN_BERRY = FoodType
      .builder("wintergreen_berry")
      .category(FoodCategories.FRUIT)
      .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
      .build();

    FoodTypes.BARLEY = FoodType
      .builder("barley")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
      .addOreDict("barley")
      .build();

    FoodTypes.BARLEY_GRAIN = FoodType
      .builder("barley_grain")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
      .addOreDict("grain_barley", "grain")
      .build();

    FoodTypes.BARLEY_FLOUR = FoodType
      .builder("barley_flour")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
      .addOreDict("flour_barley", "flour")
      .build();

    FoodTypes.BARLEY_DOUGH = FoodType
      .builder("barley_dough")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.BARLEY_BREAD = FoodType
      .builder("barley_bread")
      .category(FoodCategories.BREAD)
      .foodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
      .heatCapacity(1f)
      .cookingTemp(480f)
      .build();

    FoodTypes.MAIZE = FoodType
      .builder("maize")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
      .addOreDict("maize")
      .build();

    FoodTypes.MAIZE_GRAIN = FoodType
      .builder("maize_grain")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
      .addOreDict("grain_maize", "grain")
      .build();

    FoodTypes.CORNBREAD = FoodType
      .builder("cornbread")
      .category(FoodCategories.BREAD)
      .foodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
      .heatCapacity(1f)
      .cookingTemp(480f)
      .build();

    FoodTypes.CORNMEAL_FLOUR = FoodType
      .builder("cornmeal_flour")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
      .addOreDict("flour_maize", "flour")
      .build();

    FoodTypes.CORNMEAL_DOUGH = FoodType
      .builder("cornmeal_dough")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.OAT = FoodType
      .builder("oat")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
      .addOreDict("oat")
      .build();

    FoodTypes.OAT_GRAIN = FoodType
      .builder("oat_grain")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
      .addOreDict("grain_oat", "grain")
      .build();

    FoodTypes.OAT_FLOUR = FoodType
      .builder("oat_flour")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
      .addOreDict("flour_oat", "flour")
      .build();

    FoodTypes.OAT_DOUGH = FoodType
      .builder("oat_dough")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.OAT_BREAD = FoodType
      .builder("oat_bread")
      .category(FoodCategories.BREAD)
      .foodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
      .heatCapacity(1f)
      .cookingTemp(480f)
      .build();

    FoodTypes.RICE = FoodType
      .builder("rice")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
      .addOreDict("rice")
      .build();

    FoodTypes.RICE_GRAIN = FoodType
      .builder("rice_grain")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
      .addOreDict("grain_rice", "grain")
      .build();

    FoodTypes.RICE_FLOUR = FoodType
      .builder("rice_flour")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
      .addOreDict("flour_rice", "flour")
      .build();

    FoodTypes.RICE_DOUGH = FoodType
      .builder("rice_dough")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.RICE_BREAD = FoodType
      .builder("rice_bread")
      .category(FoodCategories.BREAD)
      .foodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
      .heatCapacity(1f)
      .cookingTemp(480f)
      .build();

    FoodTypes.RYE = FoodType
      .builder("rye")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
      .addOreDict("rye")
      .build();

    FoodTypes.RYE_GRAIN = FoodType
      .builder("rye_grain")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
      .addOreDict("grain_rye", "grain")
      .build();

    FoodTypes.RYE_FLOUR = FoodType
      .builder("rye_flour")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
      .addOreDict("flour_rye", "flour")
      .build();

    FoodTypes.RYE_DOUGH = FoodType
      .builder("rye_dough")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.RYE_BREAD = FoodType
      .builder("rye_bread")
      .category(FoodCategories.BREAD)
      .foodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
      .heatCapacity(1f)
      .cookingTemp(480f)
      .build();

    FoodTypes.WHEAT = FoodType
      .builder("wheat")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
      .addOreDict("wheat")
      .build();

    FoodTypes.WHEAT_GRAIN = FoodType
      .builder("wheat_grain")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
      .addOreDict("grain_wheat", "grain")
      .build();

    FoodTypes.WHEAT_FLOUR = FoodType
      .builder("wheat_flour")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
      .addOreDict("flour_wheat", "flour")
      .build();

    FoodTypes.WHEAT_DOUGH = FoodType
      .builder("wheat_dough")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.WHEAT_BREAD = FoodType
      .builder("wheat_bread")
      .category(FoodCategories.BREAD)
      .foodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
      .heatCapacity(1f)
      .cookingTemp(480f)
      .build();

    FoodTypes.BEET = FoodType
      .builder("beet")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 2f, 0f, 0f, 1f, 0f, 0f, 0f, 0.7f)
      .build();

    FoodTypes.CABBAGE = FoodType
      .builder("cabbage")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 1.2f)
      .build();

    FoodTypes.CARROT = FoodType
      .builder("carrot")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 2f, 0f, 0f, 1f, 0f, 0f, 0f, 0.7f)
      .addOreDict("carrot")
      .build();

    FoodTypes.GARLIC = FoodType
      .builder("garlic")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 0.5f, 0f, 0f, 2f, 0f, 0f, 0f, 0.4f)
      .build();

    FoodTypes.GREEN_BEAN = FoodType
      .builder("green_bean")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 3.5f)
      .build();

    FoodTypes.GREEN_BELL_PEPPER = FoodType
      .builder("green_bell_pepper")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 2.7f)
      .build();

    FoodTypes.ONION = FoodType
      .builder("onion")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 0.5f)
      .build();

    FoodTypes.POTATO = FoodType
      .builder("potato")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 2f, 0f, 0f, 1.5f, 0f, 0f, 0f, 0.666f)
      .build();

    FoodTypes.RED_BELL_PEPPER = FoodType
      .builder("red_bell_pepper")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
      .build();

    FoodTypes.SEAWEED = FoodType
      .builder("seaweed")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
      .build();

    FoodTypes.SOYBEAN = FoodType
      .builder("soybean")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 2f, 0f, 0f, 0.5f, 0f, 1f, 0f, 2.5f)
      .build();

    FoodTypes.SQUASH = FoodType
      .builder("squash")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 1f, 0f, 0f, 1.5f, 0f, 0f, 0f, 1.67f)
      .build();

    FoodTypes.TOMATO = FoodType
      .builder("tomato")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 0.5f, 5f, 0f, 1.5f, 0f, 0f, 0f, 3.5f)
      .build();

    FoodTypes.YELLOW_BELL_PEPPER = FoodType
      .builder("yellow_bell_pepper")
      .category(FoodCategories.VEGETABLE)
      .foodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
      .build();

    FoodTypes.CHEESE = FoodType
      .builder("cheese")
      .category(FoodCategories.DAIRY)
      .foodData(4, 2f, 0f, 0f, 0f, 0f, 0f, 3f, 0.3f)
      .build();

    FoodTypes.COOKED_EGG = FoodType
      .builder("cooked_egg")
      .category(FoodCategories.OTHER)
      .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0.75f, 0.25f, 4f)
      .build();

    FoodTypes.SUGARCANE = FoodType
      .builder("sugarcane")
      .category(FoodCategories.GRAIN)
      .foodData(4, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 1.6f)
      .build();

    FoodTypes.BEEF = FoodType
      .builder("beef")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 2f, 0f, 2f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.PORK = FoodType
      .builder("pork")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.CHICKEN = FoodType
      .builder("chicken")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.MUTTON = FoodType
      .builder("mutton")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.FISH = FoodType
      .builder("fish")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.BEAR = FoodType
      .builder("bear")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.CALAMARI = FoodType
      .builder("calamari")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.HORSE_MEAT = FoodType
      .builder("horse_meat")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.PHEASANT = FoodType
      .builder("pheasant")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.VENISON = FoodType
      .builder("venison")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 2f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.WOLF = FoodType
      .builder("wolf")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.RABBIT = FoodType
      .builder("rabbit")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.MONGOOSE = FoodType
      .builder("mongoose")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.GRAN_FELINE = FoodType
      .builder("gran_feline")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.CAMELIDAE = FoodType
      .builder("camelidae")
      .category(FoodCategories.MEAT)
      .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
      .heatCapacity(1f)
      .cookingTemp(200f)
      .build();

    FoodTypes.COOKED_BEEF = FoodType
      .builder("cooked_beef")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
      .build();

    FoodTypes.COOKED_PORK = FoodType
      .builder("cooked_pork")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
      .build();

    FoodTypes.COOKED_CHICKEN = FoodType
      .builder("cooked_chicken")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_MUTTON = FoodType
      .builder("cooked_mutton")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_FISH = FoodType
      .builder("cooked_fish")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 1f, 0f, 0f, 0f, 0f, 2f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_BEAR = FoodType
      .builder("cooked_bear")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 1f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
      .build();

    FoodTypes.COOKED_CALAMARI = FoodType
      .builder("cooked_calamari")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_HORSE_MEAT = FoodType
      .builder("cooked_horse_meat")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
      .build();

    FoodTypes.COOKED_PHEASANT = FoodType
      .builder("cooked_pheasant")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 1f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_VENISON = FoodType
      .builder("cooked_venison")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 1f, 0f, 0f, 0f, 0f, 2f, 0f, 1.5f)
      .build();

    FoodTypes.COOKED_WOLF = FoodType
      .builder("cooked_wolf")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_RABBIT = FoodType
      .builder("cooked_rabbit")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_MONGOOSE = FoodType
      .builder("cooked_mongoose")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_GRAN_FELINE = FoodType
      .builder("cooked_gran_feline")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
      .build();

    FoodTypes.COOKED_CAMELIDAE = FoodType
      .builder("cooked_camelidae")
      .category(FoodCategories.COOKED_MEAT)
      .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
      .build();

    FoodTypes.BARLEY_BREAD_SANDWICH = FoodType
      .builder("barley_bread_sandwich")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
      .addOreDict("sandwich")
      .build();

    FoodTypes.CORNBREAD_SANDWICH = FoodType
      .builder("cornbread_sandwich")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
      .addOreDict("sandwich")
      .build();

    FoodTypes.OAT_BREAD_SANDWICH = FoodType
      .builder("oat_bread_sandwich")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
      .addOreDict("sandwich")
      .build();

    FoodTypes.RICE_BREAD_SANDWICH = FoodType
      .builder("rice_bread_sandwich")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
      .addOreDict("sandwich")
      .build();

    FoodTypes.RYE_BREAD_SANDWICH = FoodType
      .builder("rye_bread_sandwich")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
      .addOreDict("sandwich")
      .build();

    FoodTypes.WHEAT_BREAD_SANDWICH = FoodType
      .builder("wheat_bread_sandwich")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
      .addOreDict("sandwich")
      .build();

    FoodTypes.SOUP_GRAIN = FoodType
      .builder("soup_grain")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
      .addOreDict("soup")
      .build();

    FoodTypes.SOUP_FRUIT = FoodType
      .builder("soup_fruit")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
      .addOreDict("soup")
      .build();

    FoodTypes.SOUP_VEGETABLE = FoodType
      .builder("soup_vegetable")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
      .addOreDict("soup")
      .build();

    FoodTypes.SOUP_MEAT = FoodType
      .builder("soup_meat")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
      .addOreDict("soup")
      .build();

    FoodTypes.SOUP_DAIRY = FoodType
      .builder("soup_dairy")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
      .addOreDict("soup")
      .build();

    FoodTypes.SALAD_GRAIN = FoodType
      .builder("salad_grain")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
      .addOreDict("salad")
      .build();

    FoodTypes.SALAD_FRUIT = FoodType
      .builder("salad_fruit")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
      .addOreDict("salad")
      .build();

    FoodTypes.SALAD_VEGETABLE = FoodType
      .builder("salad_vegetable")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
      .addOreDict("salad")
      .build();

    FoodTypes.SALAD_MEAT = FoodType
      .builder("salad_meat")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
      .addOreDict("salad")
      .build();

    FoodTypes.SALAD_DAIRY = FoodType
      .builder("salad_dairy")
      .category(FoodCategories.MEAL)
      .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
      .addOreDict("salad")
      .build();

  }
}
