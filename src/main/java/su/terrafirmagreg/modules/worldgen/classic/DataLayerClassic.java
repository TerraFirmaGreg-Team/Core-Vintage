package su.terrafirmagreg.modules.worldgen.classic;

import net.minecraft.block.Block;

/**
 * Todo: Get rid. PH is already obselete / Siesmic and drainage can be done much easier similar to rainfall / temperature
 */
@SuppressWarnings("WeakerAccess")
public final class DataLayerClassic {

  public static final DataLayerClassic ERROR = new DataLayerClassic(-1, null, "ERROR",
                                                                    Integer.MIN_VALUE, Float.NaN);
  private static final DataLayerClassic[] LAYERS = new DataLayerClassic[256];

  public static final DataLayerClassic SEISMIC_STABLE = newIntDataLayer(110, "Stable", 0);
  public static final DataLayerClassic SEISMIC_UNSTABLE = newIntDataLayer(111, "Unstable", 1);

  public static final DataLayerClassic DRAINAGE_NONE = newIntDataLayer(120, "None", 0);
  public static final DataLayerClassic DRAINAGE_VERY_POOR = newIntDataLayer(121, "Very Poor", 1);
  public static final DataLayerClassic DRAINAGE_POOR = newIntDataLayer(122, "Poor", 2);
  public static final DataLayerClassic DRAINAGE_NORMAL = newIntDataLayer(123, "Normal", 3);
  public static final DataLayerClassic DRAINAGE_GOOD = newIntDataLayer(124, "Good", 4);
  public static final DataLayerClassic DRAINAGE_VERY_GOOD = newIntDataLayer(125, "Very Good", 5);

  public static final DataLayerClassic PH_ACID_HIGH = newIntDataLayer(130, "High Acidity", 0);
  public static final DataLayerClassic PH_ACID_LOW = newIntDataLayer(131, "Low acidity", 1);
  public static final DataLayerClassic PH_NEUTRAL = newIntDataLayer(132, "Neutral", 2);
  public static final DataLayerClassic PH_ALKALINE_LOW = newIntDataLayer(133, "Low Alkalinity", 3);
  public static final DataLayerClassic PH_ALKALINE_HIGH = newIntDataLayer(134, "High Alkalinity",
                                                                          4);
  public static final DataLayerClassic RIVER0 = newIntDataLayer(253, "River0", 1);
  public static final DataLayerClassic RIVER1 = newIntDataLayer(254, "River1", 2);
  public static final DataLayerClassic RIVER2 = newIntDataLayer(255, "River2", 3);
  public final int layerID;
  public final Block block;
  public final String name;
  public final int valueInt;
  public final float valueFloat;

  private DataLayerClassic(int i, Block block, String name, int valueInt, float valueFloat) {
    this.layerID = i;
    this.block = block;
    this.name = name;
    this.valueInt = valueInt;
    this.valueFloat = valueFloat;
  }

  public static DataLayerClassic get(int i) {
    if (LAYERS[i] == null) {
      throw new IllegalArgumentException("Layer " + i + " not used.");
    }
    return LAYERS[i];
  }

  private static DataLayerClassic newIntDataLayer(int i, String name, int value) {
    if (LAYERS[i] != null) {
      throw new IllegalArgumentException("Layer " + i + " already in use.");
    }
    return LAYERS[i] = new DataLayerClassic(i, null, name, value, Float.NaN);
  }
}
