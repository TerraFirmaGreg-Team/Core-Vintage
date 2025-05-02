package net.dries007.tfc.api.recipes.knapping;

public class KnappingType {

  public static final KnappingType STONE = new KnappingType(1, false);
  public static final KnappingType CLAY = new KnappingType(5, true);
  public static final KnappingType FIRE_CLAY = new KnappingType(5, true);
  public static final KnappingType LEATHER = new KnappingType(1, false);
  public static final KnappingType PINEAPPLE_LEATHER = new KnappingType(1, false);
  public static final KnappingType BURLAP_CLOTH = new KnappingType(1, false);
  public static final KnappingType WOOL_CLOTH = new KnappingType(1, false);
  public static final KnappingType SILK_CLOTH = new KnappingType(1, false);
  public static final KnappingType SISAL_CLOTH = new KnappingType(1, false);
  public static final KnappingType COTTON_CLOTH = new KnappingType(1, false);
  public static final KnappingType LINEN_CLOTH = new KnappingType(1, false);
  public static final KnappingType HEMP_CLOTH = new KnappingType(1, false);
  public static final KnappingType YUCCA_CANVAS = new KnappingType(1, false);
  public static final KnappingType MUD = new KnappingType(3, true);
  public static final KnappingType FLINT = new KnappingType(1, false);

  private final int amountToConsume;
  private final boolean consumeAfterComplete;

  public KnappingType(int amountToConsume, boolean consumeAfterComplete) {
    this.amountToConsume = amountToConsume;
    this.consumeAfterComplete = consumeAfterComplete;
  }

  /**
   * How many of the required item this will consume The knapping source (whatever opens the GUI) should check this before starting the recipe
   */
  public int getAmountToConsume() {
    return amountToConsume;
  }

  /**
   * If true, the recipe will only consume it's contents after the player removes the item from the knapping GUI, or closes the GUI (dropping the item into
   * their inventory) If false, the recipe will consume one ingredient as soon as a single square is removed from the knapping grid
   */
  public boolean consumeAfterComplete() {
    return consumeAfterComplete;
  }
}
