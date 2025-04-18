package net.dries007.tfc.objects;

import su.terrafirmagreg.api.library.collection.WeightedCollection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public enum Gem {
  AGATE(true),
  AMETHYST(true),
  BERYL(true),
  DIAMOND(false),
  EMERALD(true),
  GARNET(true),
  JADE(true),
  JASPER(true),
  OPAL(true),
  RUBY(true),
  SAPPHIRE(true),
  TOPAZ(true),
  TOURMALINE(true),
  AMBER(true);

  // list of gems that can drop
  private static final Gem[] RANDOM_DROP_GEMS = Arrays.stream(values()).filter(x -> x.canDrop).toArray(Gem[]::new);
  // whether this gem can be found as a drop from raw stone
  private final boolean canDrop;

  Gem(boolean canDrop) {
    this.canDrop = canDrop;
  }

  /**
   * Returns a random gem type according to gem type availabilities
   *
   * @param random Random generator for rolling odds
   * @return a random drop gem type
   */
  public static Gem getRandomDropGem(Random random) {
    return RANDOM_DROP_GEMS[random.nextInt(RANDOM_DROP_GEMS.length)];
  }

  public enum Grade {
    CHIPPED(16),
    FLAWED(8),
    NORMAL(4),
    FLAWLESS(2),
    EXQUISITE(1);

    private static final Grade[] VALUES = values();
    private static final WeightedCollection<Grade> GRADE_ODDS = new WeightedCollection<>(Arrays.stream(VALUES)
      .collect(Collectors.toMap(k -> k, v -> v.dropWeight)));
    private final double dropWeight;

    Grade(int dropWeight) {
      this.dropWeight = dropWeight;
    }

    /**
     * Returns a random gem grade according to gem grade weights
     *
     * @param random Random generator for rolling the odds
     * @return a random drop gem grade
     */
    @Nonnull
    public static Grade randomGrade(Random random) {
      return GRADE_ODDS.getRandomEntry(random);
    }

    @Nullable
    public static Grade valueOf(int index) {
      return index >= 0 && index < VALUES.length ? VALUES[index] : null;
    }
  }
}
