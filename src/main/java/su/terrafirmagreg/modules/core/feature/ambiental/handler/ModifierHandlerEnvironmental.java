package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderEnvironmental;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.entity.player.EntityPlayer;

import net.dries007.tfc.api.capability.food.Nutrient;

import java.util.Optional;

public final class ModifierHandlerEnvironmental {


  public static final AmbientalRegistry<IAmbientalProviderEnvironmental> ENVIRONMENT = new AmbientalRegistry<>();

  static {
    ENVIRONMENT.register((player) ->
      ModifierEnvironmental.defined("on_fire", 4f, 4f)
        .filter(mod -> player.isBurning())
    );

    ENVIRONMENT.register((player) ->
      ModifierEnvironmental.defined("environment",
        ModifierEnvironmental.getEnvironmentTemperature(player),
        ModifierEnvironmental.getEnvironmentHumidity(player))
    );

    ENVIRONMENT.register((player) ->
      ModifierEnvironmental.defined("weather", -2f, 0.1f)
        .filter(mod -> player.world.isRaining())
        .filter(mod -> ModifierEnvironmental.getSkylight(player) < 15)
    );

    ENVIRONMENT.register((player) ->
      ModifierEnvironmental.defined("weather", -4f, 0.3f)
        .filter(mod -> player.world.isRaining())
        .filter(mod -> ModifierEnvironmental.getSkylight(player) > 15)
    );

    ENVIRONMENT.register((player) ->
      ModifierEnvironmental.defined("sprint", 2f, 0.3f)
        .filter(mod -> player.isSprinting())
    );

    ENVIRONMENT.register((player) ->
      ModifierEnvironmental.defined("underground", -6f, 0.2f)
        .filter(mod -> ModifierEnvironmental.getSkylight(player) < 2)
    );

    ENVIRONMENT.register((player) ->
      ModifierEnvironmental.defined("hypothermia_effect", -10F, 0)
        .filter(mod -> player.isPotionActive(EffectsCore.HYPOTHERMIA.get()))
    );

    ENVIRONMENT.register((player) ->
      ModifierEnvironmental.defined("hyperthermia_effect", 10F, 0)
        .filter(mod -> player.isPotionActive(EffectsCore.HYPERTHERMIA.get()))
    );
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleTimeOfDay);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleShade);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleCozy);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleThirst);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleFood);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleDiet);
  }

  public static Optional<ModifierEnvironmental> handleTimeOfDay(EntityPlayer player) {
    int dayTicks = (int) (player.world.getWorldTime() % 24000);
    if (dayTicks < 6000) {
      return ModifierEnvironmental.defined("morning", 2f, 0);
    } else if (dayTicks < 12000) {
      return ModifierEnvironmental.defined("afternoon", 4f, 0);
    } else if (dayTicks < 18000) {
      return ModifierEnvironmental.defined("evening", 2f, 0);
    } else {
      return ModifierEnvironmental.defined("night", 1f, 0);
    }
  }

  private static Optional<ModifierEnvironmental> handleShade(EntityPlayer player) {
    int light = Math.max(12, ModifierEnvironmental.getSkylight(player));
    float temp = ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player);
    float avg = CapabilityProviderAmbiental.AVERAGE;
    return ModifierEnvironmental.defined("shade", -Math.abs(avg - temp) * 0.6f, 0f)
      .filter(mod -> light < 15 && temp > avg);
  }


  private static Optional<ModifierEnvironmental> handleCozy(EntityPlayer player) {
    int skyLight = Math.max(11, ModifierEnvironmental.getSkylight(player));
    int blockLight = ModifierEnvironmental.getBlockLight(player);
    float temp = ModifierEnvironmental.getEnvironmentTemperature(player);
    float avg = CapabilityProviderAmbiental.AVERAGE;
    float coverage = (1f - (float) skyLight / 15f) + 0.4f;
    return ModifierEnvironmental.defined("cozy", Math.abs(avg - 2 - temp) * coverage, 0f)
      .filter(mod -> skyLight < 14 && blockLight > 4 && temp < avg - 2 && player.getPosition().getY() > 130);
  }

  private static Optional<ModifierEnvironmental> handleThirst(EntityPlayer player) {
    if (player.getFoodStats() instanceof IFoodStatsTFC stats) {
      if (ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player) > CapabilityProviderAmbiental.AVERAGE + 3 && stats.getThirst() > 80f) {
        return ModifierEnvironmental.defined("well_hidrated", -2.5f, 0f);
      }
    }
    return ModifierEnvironmental.none();
  }

  private static Optional<ModifierEnvironmental> handleFood(EntityPlayer player) {
    if (ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player) < CapabilityProviderAmbiental.AVERAGE - 3
        && player.getFoodStats().getFoodLevel() > 14) {
      return ModifierEnvironmental.defined("well_fed", 2.5f, 0f);
    }
    return ModifierEnvironmental.none();
  }

  private static Optional<ModifierEnvironmental> handleDiet(EntityPlayer player) {
    if (player.getFoodStats() instanceof IFoodStatsTFC stats) {
      if (ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player) < CapabilityProviderAmbiental.COOL_THRESHOLD) {
        float grainLevel = stats.getNutrition().getNutrient(Nutrient.GRAIN);
        float meatLevel = stats.getNutrition().getNutrient(Nutrient.PROTEIN);
        return ModifierEnvironmental.defined("nutrients", 4f * grainLevel * meatLevel, 0f);
      }
      if (ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player) > CapabilityProviderAmbiental.HOT_THRESHOLD) {
        float fruitLevel = stats.getNutrition().getNutrient(Nutrient.FRUIT);
        float veggieLevel = stats.getNutrition().getNutrient(Nutrient.VEGETABLES);
        return ModifierEnvironmental.defined("nutrients", -4f * fruitLevel * veggieLevel, 0f);
      }
    }
    return ModifierEnvironmental.none();
  }

}
