package su.terrafirmagreg.modules.food.api.types.variant.item;

import su.terrafirmagreg.modules.food.api.types.category.FoodCategories;


import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BANANA;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BARLEY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BARLEY_BREAD;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BARLEY_BREAD_SANDWICH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BARLEY_DOUGH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BARLEY_FLOUR;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BARLEY_GRAIN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BEAR;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BEEF;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BEET;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BLACKBERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BLUEBERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.BUNCH_BERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CABBAGE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CALAMARI;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CAMELIDAE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CARROT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CHEESE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CHERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CHICKEN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CLOUD_BERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_BEAR;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_BEEF;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_CALAMARI;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_CAMELIDAE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_CHICKEN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_EGG;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_FISH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_GRAN_FELINE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_HORSE_MEAT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_MONGOOSE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_MUTTON;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_PHEASANT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_PORK;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_RABBIT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_VENISON;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.COOKED_WOLF;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CORNBREAD;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CORNBREAD_SANDWICH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CORNMEAL_DOUGH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CORNMEAL_FLOUR;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.CRANBERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.ELDERBERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.FISH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.GARLIC;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.GOOSEBERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.GRAN_FELINE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.GREEN_APPLE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.GREEN_BEAN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.GREEN_BELL_PEPPER;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.HORSE_MEAT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.LEMON;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.MAIZE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.MAIZE_GRAIN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.MONGOOSE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.MUTTON;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.OAT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.OAT_BREAD;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.OAT_BREAD_SANDWICH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.OAT_DOUGH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.OAT_FLOUR;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.OAT_GRAIN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.OLIVE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.ONION;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.ORANGE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.PEACH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.PHEASANT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.PLUM;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.PORK;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.POTATO;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RABBIT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RASPBERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RED_APPLE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RED_BELL_PEPPER;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RICE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RICE_BREAD;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RICE_BREAD_SANDWICH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RICE_DOUGH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RICE_FLOUR;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RICE_GRAIN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RYE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RYE_BREAD;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RYE_BREAD_SANDWICH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RYE_DOUGH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RYE_FLOUR;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.RYE_GRAIN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SALAD_DAIRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SALAD_FRUIT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SALAD_GRAIN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SALAD_MEAT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SALAD_VEGETABLE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SEAWEED;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SNOW_BERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SOUP_DAIRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SOUP_FRUIT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SOUP_GRAIN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SOUP_MEAT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SOUP_VEGETABLE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SOYBEAN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SQUASH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.STRAWBERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.SUGARCANE;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.TOMATO;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.VENISON;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.WHEAT;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.WHEAT_BREAD;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.WHEAT_BREAD_SANDWICH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.WHEAT_DOUGH;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.WHEAT_FLOUR;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.WHEAT_GRAIN;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.WINTERGREEN_BERRY;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.WOLF;
import static su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariants.YELLOW_BELL_PEPPER;

public final class FoodItemVariantHandler {

  public static void init() {

    BANANA = new FoodItemVariant.Builder("banana")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 2f)
            .build();

    BLACKBERRY = new FoodItemVariant.Builder("blackberry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
            .build();

    BLUEBERRY = new FoodItemVariant.Builder("blueberry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
            .build();

    BUNCH_BERRY = new FoodItemVariant.Builder("bunch_berry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
            .build();

    CHERRY = new FoodItemVariant.Builder("cherry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4f)
            .build();

    CLOUD_BERRY = new FoodItemVariant.Builder("cloud_berry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
            .build();

    CRANBERRY = new FoodItemVariant.Builder("cranberry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 1.8f)
            .build();

    ELDERBERRY = new FoodItemVariant.Builder("elderberry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
            .build();

    GOOSEBERRY = new FoodItemVariant.Builder("gooseberry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
            .build();

    GREEN_APPLE = new FoodItemVariant.Builder("green_apple")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 2.5f)
            .addOreDict("apple")
            .build();

    LEMON = new FoodItemVariant.Builder("lemon")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2f)
            .build();

    OLIVE = new FoodItemVariant.Builder("olive")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 0f, 0f, 0f, 1f, 0f, 0f, 1.6f)
            .build();

    ORANGE = new FoodItemVariant.Builder("orange")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.2f)
            .build();

    PEACH = new FoodItemVariant.Builder("peach")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 2.8f)
            .build();

    PLUM = new FoodItemVariant.Builder("plum")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 2.8f)
            .build();

    RASPBERRY = new FoodItemVariant.Builder("raspberry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 5f, 0f, 0f, 0.75f, 0f, 0f, 4.9f)
            .build();

    RED_APPLE = new FoodItemVariant.Builder("red_apple")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 0f, 0f, 0f, 1f, 0f, 0f, 1.7f)
            .addOreDict("apple")
            .build();

    SNOW_BERRY = new FoodItemVariant.Builder("snow_berry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
            .build();

    STRAWBERRY = new FoodItemVariant.Builder("strawberry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.5f, 10f, 0f, 0f, 0.5f, 0f, 0f, 4.9f)
            .build();

    WINTERGREEN_BERRY = new FoodItemVariant.Builder("wintergreen_berry")
            .category(FoodCategories.FRUIT)
            .foodData(4, 0.2f, 5f, 0f, 0f, 1f, 0f, 0f, 4.9f)
            .build();

    BARLEY = new FoodItemVariant.Builder("barley")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
            .addOreDict("barley")
            .build();

    BARLEY_GRAIN = new FoodItemVariant.Builder("barley_grain")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
            .addOreDict("grain_barley", "grain")
            .build();

    BARLEY_FLOUR = new FoodItemVariant.Builder("barley_flour")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
            .addOreDict("flour_barley", "flour")
            .build();

    BARLEY_DOUGH = new FoodItemVariant.Builder("barley_dough")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    BARLEY_BREAD = new FoodItemVariant.Builder("barley_bread")
            .category(FoodCategories.BREAD)
            .foodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
            .heatCapacity(1f)
            .cookingTemp(480f)
            .build();

    MAIZE = new FoodItemVariant.Builder("maize")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
            .addOreDict("maize")
            .build();

    MAIZE_GRAIN = new FoodItemVariant.Builder("maize_grain")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
            .addOreDict("grain_maize", "grain")
            .build();

    CORNBREAD = new FoodItemVariant.Builder("cornbread")
            .category(FoodCategories.BREAD)
            .foodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
            .heatCapacity(1f)
            .cookingTemp(480f)
            .build();

    CORNMEAL_FLOUR = new FoodItemVariant.Builder("cornmeal_flour")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
            .addOreDict("flour_maize", "flour")
            .build();

    CORNMEAL_DOUGH = new FoodItemVariant.Builder("cornmeal_dough")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    OAT = new FoodItemVariant.Builder("oat")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
            .addOreDict("oat")
            .build();

    OAT_GRAIN = new FoodItemVariant.Builder("oat_grain")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
            .addOreDict("grain_oat", "grain")
            .build();

    OAT_FLOUR = new FoodItemVariant.Builder("oat_flour")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
            .addOreDict("flour_oat", "flour")
            .build();

    OAT_DOUGH = new FoodItemVariant.Builder("oat_dough")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    OAT_BREAD = new FoodItemVariant.Builder("oat_bread")
            .category(FoodCategories.BREAD)
            .foodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
            .heatCapacity(1f)
            .cookingTemp(480f)
            .build();

    RICE = new FoodItemVariant.Builder("rice")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
            .addOreDict("rice")
            .build();

    RICE_GRAIN = new FoodItemVariant.Builder("rice_grain")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
            .addOreDict("grain_rice", "grain")
            .build();

    RICE_FLOUR = new FoodItemVariant.Builder("rice_flour")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
            .addOreDict("flour_rice", "flour")
            .build();

    RICE_DOUGH = new FoodItemVariant.Builder("rice_dough")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    RICE_BREAD = new FoodItemVariant.Builder("rice_bread")
            .category(FoodCategories.BREAD)
            .foodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
            .heatCapacity(1f)
            .cookingTemp(480f)
            .build();

    RYE = new FoodItemVariant.Builder("rye")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
            .addOreDict("rye")
            .build();

    RYE_GRAIN = new FoodItemVariant.Builder("rye_grain")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
            .addOreDict("grain_rye", "grain")
            .build();

    RYE_FLOUR = new FoodItemVariant.Builder("rye_flour")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
            .addOreDict("flour_rye", "flour")
            .build();

    RYE_DOUGH = new FoodItemVariant.Builder("rye_dough")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    RYE_BREAD = new FoodItemVariant.Builder("rye_bread")
            .category(FoodCategories.BREAD)
            .foodData(4, 1f, 0f, 1.5f, 0f, 0f, 0f, 0f, 1f)
            .heatCapacity(1f)
            .cookingTemp(480f)
            .build();

    WHEAT = new FoodItemVariant.Builder("wheat")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 2f)
            .addOreDict("wheat")
            .build();

    WHEAT_GRAIN = new FoodItemVariant.Builder("wheat_grain")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f, 0.25f)
            .addOreDict("grain_wheat", "grain")
            .build();

    WHEAT_FLOUR = new FoodItemVariant.Builder("wheat_flour")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.5f)
            .addOreDict("flour_wheat", "flour")
            .build();

    WHEAT_DOUGH = new FoodItemVariant.Builder("wheat_dough")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    WHEAT_BREAD = new FoodItemVariant.Builder("wheat_bread")
            .category(FoodCategories.BREAD)
            .foodData(4, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
            .heatCapacity(1f)
            .cookingTemp(480f)
            .build();

    BEET = new FoodItemVariant.Builder("beet")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 2f, 0f, 0f, 1f, 0f, 0f, 0f, 0.7f)
            .build();

    CABBAGE = new FoodItemVariant.Builder("cabbage")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 1.2f)
            .build();

    CARROT = new FoodItemVariant.Builder("carrot")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 2f, 0f, 0f, 1f, 0f, 0f, 0f, 0.7f)
            .addOreDict("carrot")
            .build();

    GARLIC = new FoodItemVariant.Builder("garlic")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 0.5f, 0f, 0f, 2f, 0f, 0f, 0f, 0.4f)
            .build();

    GREEN_BEAN = new FoodItemVariant.Builder("green_bean")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 3.5f)
            .build();

    GREEN_BELL_PEPPER = new FoodItemVariant.Builder("green_bell_pepper")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 2.7f)
            .build();

    ONION = new FoodItemVariant.Builder("onion")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 0.5f, 0f, 0f, 1f, 0f, 0f, 0f, 0.5f)
            .build();

    POTATO = new FoodItemVariant.Builder("potato")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 2f, 0f, 0f, 1.5f, 0f, 0f, 0f, 0.666f)
            .build();

    RED_BELL_PEPPER = new FoodItemVariant.Builder("red_bell_pepper")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
            .build();

    SEAWEED = new FoodItemVariant.Builder("seaweed")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
            .build();

    SOYBEAN = new FoodItemVariant.Builder("soybean")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 2f, 0f, 0f, 0.5f, 0f, 1f, 0f, 2.5f)
            .build();

    SQUASH = new FoodItemVariant.Builder("squash")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 1f, 0f, 0f, 1.5f, 0f, 0f, 0f, 1.67f)
            .build();

    TOMATO = new FoodItemVariant.Builder("tomato")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 0.5f, 5f, 0f, 1.5f, 0f, 0f, 0f, 3.5f)
            .build();

    YELLOW_BELL_PEPPER = new FoodItemVariant.Builder("yellow_bell_pepper")
            .category(FoodCategories.VEGETABLE)
            .foodData(4, 1f, 0f, 0f, 1f, 0f, 0f, 0f, 2.5f)
            .build();

    CHEESE = new FoodItemVariant.Builder("cheese")
            .category(FoodCategories.DAIRY)
            .foodData(4, 2f, 0f, 0f, 0f, 0f, 0f, 3f, 0.3f)
            .build();

    COOKED_EGG = new FoodItemVariant.Builder("cooked_egg")
            .category(FoodCategories.OTHER)
            .foodData(4, 0.5f, 0f, 0f, 0f, 0f, 0.75f, 0.25f, 4f)
            .build();

    SUGARCANE = new FoodItemVariant.Builder("sugarcane")
            .category(FoodCategories.GRAIN)
            .foodData(4, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 1.6f)
            .build();

    BEEF = new FoodItemVariant.Builder("beef")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 2f, 0f, 2f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    PORK = new FoodItemVariant.Builder("pork")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    CHICKEN = new FoodItemVariant.Builder("chicken")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    MUTTON = new FoodItemVariant.Builder("mutton")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    FISH = new FoodItemVariant.Builder("fish")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    BEAR = new FoodItemVariant.Builder("bear")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    CALAMARI = new FoodItemVariant.Builder("calamari")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    HORSE_MEAT = new FoodItemVariant.Builder("horse_meat")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    PHEASANT = new FoodItemVariant.Builder("pheasant")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 1.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    VENISON = new FoodItemVariant.Builder("venison")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 2f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    WOLF = new FoodItemVariant.Builder("wolf")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    RABBIT = new FoodItemVariant.Builder("rabbit")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    MONGOOSE = new FoodItemVariant.Builder("mongoose")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    GRAN_FELINE = new FoodItemVariant.Builder("gran_feline")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    CAMELIDAE = new FoodItemVariant.Builder("camelidae")
            .category(FoodCategories.MEAT)
            .foodData(4, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 3f)
            .heatCapacity(1f)
            .cookingTemp(200f)
            .build();

    COOKED_BEEF = new FoodItemVariant.Builder("cooked_beef")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
            .build();

    COOKED_PORK = new FoodItemVariant.Builder("cooked_pork")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
            .build();

    COOKED_CHICKEN = new FoodItemVariant.Builder("cooked_chicken")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
            .build();

    COOKED_MUTTON = new FoodItemVariant.Builder("cooked_mutton")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
            .build();

    COOKED_FISH = new FoodItemVariant.Builder("cooked_fish")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 1f, 0f, 0f, 0f, 0f, 2f, 0f, 2.25f)
            .build();

    COOKED_BEAR = new FoodItemVariant.Builder("cooked_bear")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 1f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
            .build();

    COOKED_CALAMARI = new FoodItemVariant.Builder("cooked_calamari")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
            .build();

    COOKED_HORSE_MEAT = new FoodItemVariant.Builder("cooked_horse_meat")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 1.5f)
            .build();

    COOKED_PHEASANT = new FoodItemVariant.Builder("cooked_pheasant")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 1f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
            .build();

    COOKED_VENISON = new FoodItemVariant.Builder("cooked_venison")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 1f, 0f, 0f, 0f, 0f, 2f, 0f, 1.5f)
            .build();

    COOKED_WOLF = new FoodItemVariant.Builder("cooked_wolf")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
            .build();

    COOKED_RABBIT = new FoodItemVariant.Builder("cooked_rabbit")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
            .build();

    COOKED_MONGOOSE = new FoodItemVariant.Builder("cooked_mongoose")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 1f, 0f, 0f, 0f, 0f, 1.5f, 0f, 2.25f)
            .build();

    COOKED_GRAN_FELINE = new FoodItemVariant.Builder("cooked_gran_feline")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
            .build();

    COOKED_CAMELIDAE = new FoodItemVariant.Builder("cooked_camelidae")
            .category(FoodCategories.COOKED_MEAT)
            .foodData(4, 2f, 0f, 0f, 0f, 0f, 2.5f, 0f, 2.25f)
            .build();

    BARLEY_BREAD_SANDWICH = new FoodItemVariant.Builder("barley_bread_sandwich")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
            .addOreDict("sandwich")
            .build();

    CORNBREAD_SANDWICH = new FoodItemVariant.Builder("cornbread_sandwich")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
            .addOreDict("sandwich")
            .build();

    OAT_BREAD_SANDWICH = new FoodItemVariant.Builder("oat_bread_sandwich")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
            .addOreDict("sandwich")
            .build();

    RICE_BREAD_SANDWICH = new FoodItemVariant.Builder("rice_bread_sandwich")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
            .addOreDict("sandwich")
            .build();

    RYE_BREAD_SANDWICH = new FoodItemVariant.Builder("rye_bread_sandwich")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
            .addOreDict("sandwich")
            .build();

    WHEAT_BREAD_SANDWICH = new FoodItemVariant.Builder("wheat_bread_sandwich")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 4.5f)
            .addOreDict("sandwich")
            .build();

    SOUP_GRAIN = new FoodItemVariant.Builder("soup_grain")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
            .addOreDict("soup")
            .build();

    SOUP_FRUIT = new FoodItemVariant.Builder("soup_fruit")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
            .addOreDict("soup")
            .build();

    SOUP_VEGETABLE = new FoodItemVariant.Builder("soup_vegetable")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
            .addOreDict("soup")
            .build();

    SOUP_MEAT = new FoodItemVariant.Builder("soup_meat")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
            .addOreDict("soup")
            .build();

    SOUP_DAIRY = new FoodItemVariant.Builder("soup_dairy")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 3.5f)
            .addOreDict("soup")
            .build();

    SALAD_GRAIN = new FoodItemVariant.Builder("salad_grain")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
            .addOreDict("salad")
            .build();

    SALAD_FRUIT = new FoodItemVariant.Builder("salad_fruit")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
            .addOreDict("salad")
            .build();

    SALAD_VEGETABLE = new FoodItemVariant.Builder("salad_vegetable")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
            .addOreDict("salad")
            .build();

    SALAD_MEAT = new FoodItemVariant.Builder("salad_meat")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
            .addOreDict("salad")
            .build();

    SALAD_DAIRY = new FoodItemVariant.Builder("salad_dairy")
            .category(FoodCategories.MEAL)
            .foodData(4, 3f, 0f, 0f, 0f, 0f, 0f, 0f, 5f)
            .addOreDict("salad")
            .build();

  }
}
