package su.terrafirmagreg.modules.food.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.food.api.types.category.FoodCategories;
import su.terrafirmagreg.modules.food.api.types.variant.item.FoodItemVariant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;

import net.minecraft.item.Item;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public class ItemsFood {

  public static final Map<Pair<RockItemVariant, RockType>, Item> FOOD_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

  public static FoodItemVariant BANANA;
  public static FoodItemVariant BLACKBERRY;
  public static FoodItemVariant BLUEBERRY;
  public static FoodItemVariant BUNCH_BERRY;
  public static FoodItemVariant CHERRY;
  public static FoodItemVariant CLOUD_BERRY;
  public static FoodItemVariant CRANBERRY;
  public static FoodItemVariant ELDERBERRY;
  public static FoodItemVariant GOOSEBERRY;
  public static FoodItemVariant GREEN_APPLE;
  public static FoodItemVariant LEMON;
  public static FoodItemVariant OLIVE;
  public static FoodItemVariant ORANGE;
  public static FoodItemVariant PEACH;
  public static FoodItemVariant PLUM;
  public static FoodItemVariant RASPBERRY;
  public static FoodItemVariant RED_APPLE;
  public static FoodItemVariant SNOW_BERRY;
  public static FoodItemVariant STRAWBERRY;
  public static FoodItemVariant WINTERGREEN_BERRY;
  public static FoodItemVariant BARLEY;
  public static FoodItemVariant BARLEY_GRAIN;
  public static FoodItemVariant BARLEY_FLOUR;
  public static FoodItemVariant BARLEY_DOUGH;
  public static FoodItemVariant BARLEY_BREAD;
  public static FoodItemVariant MAIZE;
  public static FoodItemVariant MAIZE_GRAIN;
  public static FoodItemVariant CORNBREAD;
  public static FoodItemVariant CORNMEAL_FLOUR;
  public static FoodItemVariant CORNMEAL_DOUGH;
  public static FoodItemVariant OAT;
  public static FoodItemVariant OAT_GRAIN;
  public static FoodItemVariant OAT_FLOUR;
  public static FoodItemVariant OAT_DOUGH;
  public static FoodItemVariant OAT_BREAD;
  public static FoodItemVariant RICE;
  public static FoodItemVariant RICE_GRAIN;
  public static FoodItemVariant RICE_FLOUR;
  public static FoodItemVariant RICE_DOUGH;
  public static FoodItemVariant RICE_BREAD;
  public static FoodItemVariant RYE;
  public static FoodItemVariant RYE_GRAIN;
  public static FoodItemVariant RYE_FLOUR;
  public static FoodItemVariant RYE_DOUGH;
  public static FoodItemVariant RYE_BREAD;
  public static FoodItemVariant WHEAT;
  public static FoodItemVariant WHEAT_GRAIN;
  public static FoodItemVariant WHEAT_FLOUR;
  public static FoodItemVariant WHEAT_DOUGH;
  public static FoodItemVariant WHEAT_BREAD;
  public static FoodItemVariant BEET;
  public static FoodItemVariant CABBAGE;
  public static FoodItemVariant CARROT;
  public static FoodItemVariant GARLIC;
  public static FoodItemVariant GREEN_BEAN;
  public static FoodItemVariant GREEN_BELL_PEPPER;
  public static FoodItemVariant ONION;
  public static FoodItemVariant POTATO;
  public static FoodItemVariant RED_BELL_PEPPER;
  public static FoodItemVariant SEAWEED;
  public static FoodItemVariant SOYBEAN;
  public static FoodItemVariant SQUASH;
  public static FoodItemVariant TOMATO;
  public static FoodItemVariant YELLOW_BELL_PEPPER;
  public static FoodItemVariant CHEESE;
  public static FoodItemVariant COOKED_EGG;
  public static FoodItemVariant SUGARCANE;
  public static FoodItemVariant BEEF;
  public static FoodItemVariant PORK;
  public static FoodItemVariant CHICKEN;
  public static FoodItemVariant MUTTON;
  public static FoodItemVariant FISH;
  public static FoodItemVariant BEAR;
  public static FoodItemVariant CALAMARI;
  public static FoodItemVariant HORSE_MEAT;
  public static FoodItemVariant PHEASANT;
  public static FoodItemVariant VENISON;
  public static FoodItemVariant WOLF;
  public static FoodItemVariant RABBIT;
  public static FoodItemVariant MONGOOSE;
  public static FoodItemVariant GRAN_FELINE;
  public static FoodItemVariant CAMELIDAE;
  public static FoodItemVariant COOKED_BEEF;
  public static FoodItemVariant COOKED_PORK;
  public static FoodItemVariant COOKED_CHICKEN;
  public static FoodItemVariant COOKED_MUTTON;
  public static FoodItemVariant COOKED_FISH;
  public static FoodItemVariant COOKED_BEAR;
  public static FoodItemVariant COOKED_CALAMARI;
  public static FoodItemVariant COOKED_HORSE_MEAT;
  public static FoodItemVariant COOKED_PHEASANT;
  public static FoodItemVariant COOKED_VENISON;
  public static FoodItemVariant COOKED_WOLF;
  public static FoodItemVariant COOKED_RABBIT;
  public static FoodItemVariant COOKED_MONGOOSE;
  public static FoodItemVariant COOKED_GRAN_FELINE;
  public static FoodItemVariant COOKED_CAMELIDAE;
  public static FoodItemVariant BARLEY_BREAD_SANDWICH;
  public static FoodItemVariant CORNBREAD_SANDWICH;
  public static FoodItemVariant OAT_BREAD_SANDWICH;
  public static FoodItemVariant RICE_BREAD_SANDWICH;
  public static FoodItemVariant RYE_BREAD_SANDWICH;
  public static FoodItemVariant WHEAT_BREAD_SANDWICH;
  public static FoodItemVariant SOUP_GRAIN;
  public static FoodItemVariant SOUP_FRUIT;
  public static FoodItemVariant SOUP_VEGETABLE;
  public static FoodItemVariant SOUP_MEAT;
  public static FoodItemVariant SOUP_DAIRY;
  public static FoodItemVariant SALAD_GRAIN;
  public static FoodItemVariant SALAD_FRUIT;
  public static FoodItemVariant SALAD_VEGETABLE;
  public static FoodItemVariant SALAD_MEAT;
  public static FoodItemVariant SALAD_DAIRY;

  public static void onRegister(RegistryManager registry) {
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
