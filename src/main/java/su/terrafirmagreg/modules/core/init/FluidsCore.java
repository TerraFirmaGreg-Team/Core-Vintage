package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;

import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;

public final class FluidsCore {


  // Water variants
  public static FluidWrapper FRESH_WATER;

  public static FluidWrapper HOT_WATER;
  public static FluidWrapper SALT_WATER;
  public static FluidWrapper DISTILLED_WATER;

  // Other fluids
  public static FluidWrapper LATEX;
  public static FluidWrapper GLASS;
  public static FluidWrapper LIMEWATER;
  public static FluidWrapper TANNIN;
  public static FluidWrapper VINEGAR;
  public static FluidWrapper BRINE;
  public static FluidWrapper MILK;
  public static FluidWrapper CURDLED_MILK;
  public static FluidWrapper MILK_VINEGAR;
  public static FluidWrapper OLIVE_OIL;
  public static FluidWrapper OLIVE_OIL_WATER;
  public static FluidWrapper LYE;
  public static FluidWrapper YEAST_STARTER;
  public static FluidWrapper COCONUT_MILK;
  public static FluidWrapper YAK_MILK;
  public static FluidWrapper GOAT_MILK;
  public static FluidWrapper ZEBU_MILK;
  public static FluidWrapper CURDLED_YAK_MILK;
  public static FluidWrapper CURDLED_GOAT_MILK;
  public static FluidWrapper PINA_COLADA;
  public static FluidWrapper COFFEE;
  public static FluidWrapper SWEET_COFFEE;
  public static FluidWrapper TEA;
  public static FluidWrapper SWEET_TEA;

  // Alcohols
  public static FluidWrapper CIDER;
  public static FluidWrapper VODKA;
  public static FluidWrapper SAKE;
  public static FluidWrapper CORN_WHISKEY;
  public static FluidWrapper RYE_WHISKEY;
  public static FluidWrapper WHISKEY;
  public static FluidWrapper BEER;
  public static FluidWrapper RUM;

  public static FluidWrapper AGED_CIDER;
  public static FluidWrapper AGED_VODKA;
  public static FluidWrapper AGED_SAKE;
  public static FluidWrapper AGED_CORN_WHISKEY;
  public static FluidWrapper AGED_RYE_WHISKEY;
  public static FluidWrapper AGED_WHISKEY;
  public static FluidWrapper AGED_BEER;
  public static FluidWrapper AGED_RUM;

  // Other
  public static FluidWrapper WASTE;
  public static FluidWrapper BASE_POTASH_LIQUOR;

  // Tea
  public static FluidWrapper WHITE_TEA;
  public static FluidWrapper GREEN_TEA;
  public static FluidWrapper BLACK_TEA;
  public static FluidWrapper CHAMOMILE_TEA;
  public static FluidWrapper DANDELION_TEA;
  public static FluidWrapper LABRADOR_TEA;

  // Coffee & Coke
  public static FluidWrapper FIRMA_COLA; //Obviously a reference to Coca Cola.

  // Fermented Alcohols
  public static FluidWrapper AGAVE_WINE;
  public static FluidWrapper BARLEY_WINE;
  public static FluidWrapper BANANA_WINE;
  public static FluidWrapper BERRY_WINE;
  public static FluidWrapper CHERRY_WINE;
  public static FluidWrapper JUNIPER_WINE;
  public static FluidWrapper LEMON_WINE;
  public static FluidWrapper ORANGE_WINE;
  public static FluidWrapper PAPAYA_WINE;
  public static FluidWrapper PEACH_WINE;
  public static FluidWrapper PEAR_WINE;
  public static FluidWrapper PLUM_WINE;
  public static FluidWrapper MEAD;
  public static FluidWrapper RED_WINE;
  public static FluidWrapper WHEAT_WINE;
  public static FluidWrapper WHITE_WINE;

  // Alcohols
  public static FluidWrapper CALVADOS;
  public static FluidWrapper GIN;
  public static FluidWrapper TEQUILA;
  public static FluidWrapper SHOCHU;
  public static FluidWrapper BANANA_BRANDY;
  public static FluidWrapper CHERRY_BRANDY;
  public static FluidWrapper LEMON_BRANDY;
  public static FluidWrapper ORANGE_BRANDY;
  public static FluidWrapper PAPAYA_BRANDY;
  public static FluidWrapper PEACH_BRANDY;
  public static FluidWrapper PEAR_BRANDY;
  public static FluidWrapper PLUM_BRANDY;
  public static FluidWrapper BERRY_BRANDY;
  public static FluidWrapper BRANDY;
  public static FluidWrapper COGNAC;
  public static FluidWrapper GRAPPA;

  // Beer
  public static FluidWrapper BEER_BARLEY;
  public static FluidWrapper BEER_CORN;
  public static FluidWrapper BEER_RYE;
  public static FluidWrapper BEER_WHEAT;
  public static FluidWrapper BEER_AMARANTH;
  public static FluidWrapper BEER_BUCKWHEAT;
  public static FluidWrapper BEER_FONIO;
  public static FluidWrapper BEER_MILLET;
  public static FluidWrapper BEER_QUINOA;
  public static FluidWrapper BEER_SPELT;

  // Misc
  public static FluidWrapper SUGAR_WATER;
  public static FluidWrapper HONEY_WATER;
  public static FluidWrapper RICE_WATER;
  public static FluidWrapper SOYBEAN_WATER;
  public static FluidWrapper LINSEED_WATER;
  public static FluidWrapper RAPE_SEED_WATER;
  public static FluidWrapper SUNFLOWER_SEED_WATER;
  public static FluidWrapper OPIUM_POPPY_SEED_WATER;
  public static FluidWrapper SUGAR_BEET_WATER;
  public static FluidWrapper SOY_MILK;
  public static FluidWrapper LINSEED_OIL;
  public static FluidWrapper RAPE_SEED_OIL;
  public static FluidWrapper SUNFLOWER_SEED_OIL;
  public static FluidWrapper OPIUM_POPPY_SEED_OIL;
  public static FluidWrapper WORT;

  // Juice - Berries
  public static FluidWrapper JUICE_BLACKBERRY;
  public static FluidWrapper JUICE_BLUEBERRY;
  public static FluidWrapper JUICE_BUNCH_BERRY;
  public static FluidWrapper JUICE_CLOUD_BERRY;
  public static FluidWrapper JUICE_CRANBERRY;
  public static FluidWrapper JUICE_ELDERBERRY;
  public static FluidWrapper JUICE_GOOSEBERRY;
  public static FluidWrapper JUICE_RASPBERRY;
  public static FluidWrapper JUICE_SNOW_BERRY;
  public static FluidWrapper JUICE_STRAWBERRY;
  public static FluidWrapper JUICE_WINTERGREEN_BERRY;

  // Juice - Fruits
  public static FluidWrapper JUICE_AGAVE;
  public static FluidWrapper JUICE_APPLE;
  public static FluidWrapper JUICE_BANANA;
  public static FluidWrapper JUICE_CHERRY;
  public static FluidWrapper JUICE_GREEN_GRAPE;
  public static FluidWrapper JUICE_JUNIPER;
  public static FluidWrapper JUICE_LEMON;
  public static FluidWrapper JUICE_ORANGE;
  public static FluidWrapper JUICE_PAPAYA;
  public static FluidWrapper JUICE_PEACH;
  public static FluidWrapper JUICE_PEAR;
  public static FluidWrapper JUICE_PLUM;
  public static FluidWrapper JUICE_PURPLE_GRAPE;
  public static FluidWrapper JUICE_BARREL_CACTUS;

  public static void onRegister(IRegistryManager registry) {
    FluidsTFC.registerFluids();
    //==== Water Variants ======================================================================================================================//

    //FRESH_WATER.get() = registry.fluid(new FreshWater());
  }

}
