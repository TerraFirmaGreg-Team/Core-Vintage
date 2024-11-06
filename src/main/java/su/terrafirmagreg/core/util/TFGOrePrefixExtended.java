package su.terrafirmagreg.core.util;

import gregtech.api.unification.ore.OrePrefix;

public class TFGOrePrefixExtended {

  private final OrePrefix orePrefix;
  private final String[] stoneKnappingRecipe;


  public TFGOrePrefixExtended(OrePrefix orePrefix, String[] knappingRecipe) {
    this.orePrefix = orePrefix;
    this.stoneKnappingRecipe = knappingRecipe;
  }

  public OrePrefix getOrePrefix() {
    return orePrefix;
  }

  public String[] getStoneKnappingRecipe() {
    return stoneKnappingRecipe;
  }
}
