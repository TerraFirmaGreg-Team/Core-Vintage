package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderEnvironmental;

public final class ModifierHandlerEnvironmental {


  public static final AmbientalRegistry<IAmbientalProviderEnvironmental> ENVIRONMENT = new AmbientalRegistry<>();

  static {
    ENVIRONMENT.register(ModifierEnvironmental::handleGeneralTemperature);
    ENVIRONMENT.register(ModifierEnvironmental::handleTimeOfDay);
    ENVIRONMENT.register(ModifierEnvironmental::handleShade);
    ENVIRONMENT.register(ModifierEnvironmental::handleCozy);
    ENVIRONMENT.register(ModifierEnvironmental::handleThirst);
    ENVIRONMENT.register(ModifierEnvironmental::handleFood);
    ENVIRONMENT.register(ModifierEnvironmental::handleDiet);
    ENVIRONMENT.register(ModifierEnvironmental::handleFire);
    ENVIRONMENT.register(ModifierEnvironmental::handleWater);
    ENVIRONMENT.register(ModifierEnvironmental::handleRain);
    ENVIRONMENT.register(ModifierEnvironmental::handleSprinting);
    ENVIRONMENT.register(ModifierEnvironmental::handleUnderground);
    ENVIRONMENT.register(ModifierEnvironmental::handlePotionEffects);
  }

}
