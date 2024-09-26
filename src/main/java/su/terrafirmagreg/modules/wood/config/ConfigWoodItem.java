package su.terrafirmagreg.modules.wood.config;

import net.minecraftforge.common.config.Config;

public final class ConfigWoodItem {

  public final SupplyCart SUPPLY_CART = new SupplyCart();
  public final PlowCart PLOW_CART = new PlowCart();
  public final AnimalCart ANIMAL_CART = new AnimalCart();
  public final WoodBucket BUCKET = new WoodBucket();

  public static class SupplyCart {

    public String[] canPull = {
      "minecraft:donkey",
      "minecraft:horse",
      "minecraft:mule",
      "minecraft:pig",
      "minecraft:player",
      "tfg:camel",
      "tfg:donkey",
      "tfg:horse",
      "tfg:mule"
    };
  }

  public static class PlowCart {

    public String[] canPull = {
      "minecraft:donkey",
      "minecraft:horse",
      "minecraft:mule",
      "minecraft:pig",
      "minecraft:player",
      "tfg:camel",
      "tfg:donkey",
      "tfg:horse",
      "tfg:mule"
    };
  }

  public static class AnimalCart {

    public String[] canPull = {
      "minecraft:donkey",
      "minecraft:horse",
      "minecraft:mule",
      "minecraft:pig",
      "minecraft:player",
      "tfg:camel",
      "tfg:donkey",
      "tfg:horse",
      "tfg:mule"
    };
  }

  public static class WoodBucket {

    @Config.Comment("List of fluids allowed to be picked up by wooden bucket")
    public String[] whitelist = new String[]{
      "fresh_water", "hot_water", "salt_water", "water", "limewater", "tannin",
      "olive_oil", "olive_oil_water", "vinegar", "rum", "beer", "whiskey", "rye_whiskey", "corn_whiskey", "sake", "vodka", "cider",
      "brine", "milk", "milk_curdled", "milk_vinegar", "white_dye", "orange_dye", "magenta_dye", "light_blue_dye", "yellow_dye",
      "lime_dye", "pink_dye", "gray_dye", "light_gray_dye", "cyan_dye", "purple_dye", "blue_dye", "brown_dye", "green_dye", "red_dye",
      "black_dye", "distilled_water", "waste", "base_potash_liquor", "white_tea", "green_tea", "black_tea", "chamomile_tea",
      "dandelion_tea", "labrador_tea", "coffee", "agave_wine", "barley_wine", "banana_wine", "berry_wine", "cherry_wine",
      "juniper_wine", "lemon_wine", "orange_wine", "papaya_wine", "peach_wine", "pear_wine", "plum_wine", "mead", "red_wine",
      "wheat_wine", "white_wine", "calvados", "gin", "tequila", "shochu", "grappa", "banana_brandy", "cherry_brandy", "lemon_brandy",
      "orange_brandy", "papaya_brandy", "peach_brandy", "pear_brandy", "plum_brandy", "berry_brandy", "brandy", "cognac", "beer_barley",
      "beer_corn", "beer_rye", "beer_wheat", "beer_amaranth", "beer_buckwheat", "beer_fonio", "beer_millet", "beer_quinoa",
      "beer_spelt", "sugar_water", "honey_water", "rice_water", "soybean_water", "linseed_water", "rape_seed_water",
      "sunflower_seed_water", "opium_poppy_seed_water", "sugar_beet_water", "soy_milk", "linseed_oil", "rape_seed_oil",
      "sunflower_seed_oil", "opium_poppy_seed_oil", "wort", "firma_cola", "juice_blackberry", "juice_blueberry", "juice_bunch_berry",
      "juice_cloud_berry", "juice_cranberry", "juice_elderberry", "juice_gooseberry", "juice_raspberry", "juice_snow_berry",
      "juice_strawberry", "juice_wintergreen_berry", "juice_agave", "juice_apple", "juice_banana", "juice_cherry", "juice_lemon",
      "juice_orange", "juice_papaya", "juice_peach", "juice_pear", "juice_plum", "juice_juniper", "juice_green_grape",
      "juice_purple_grape", "juice_barrel_cactus", "yeast_starter", "coconut_milk", "yak_milk", "zebu_milk", "goat_milk",
      "curdled_goat_milk", "curdled_yak_milk", "pina_colada"
    };
  }

}
