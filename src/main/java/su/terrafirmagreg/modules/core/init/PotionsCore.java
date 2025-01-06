package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.potion.PotionColdResist;
import su.terrafirmagreg.modules.core.object.potion.PotionHeatResist;
import su.terrafirmagreg.modules.core.object.potion.PotionLongColdResist;
import su.terrafirmagreg.modules.core.object.potion.PotionLongHeatResist;

import java.util.function.Supplier;

public final class PotionsCore {

  public static Supplier<PotionColdResist> COLD_RESIST_TYPE;
  public static Supplier<PotionLongColdResist> LONG_COLD_RESIST_TYPE;
  public static Supplier<PotionHeatResist> HEAT_RESIST_TYPE;
  public static Supplier<PotionLongHeatResist> LONG_HEAT_RESIST_TYPE;

  public static void onRegister(IRegistryManager registry) {

    COLD_RESIST_TYPE = registry.potion(new PotionColdResist());
    LONG_COLD_RESIST_TYPE = registry.potion(new PotionLongColdResist());
    HEAT_RESIST_TYPE = registry.potion(new PotionHeatResist());
    LONG_HEAT_RESIST_TYPE = registry.potion(new PotionLongHeatResist());
  }
}
