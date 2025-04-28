package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.capabilities.food.spi.Nutrient;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderEnvironmental;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public final class ModifierHandlerEnvironmental {


  public static final AmbientalRegistry<IAmbientalProviderEnvironmental> ENVIRONMENT = new AmbientalRegistry<>();

  static {

    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleGeneralTemperature);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleTimeOfDay);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleShade);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleCozy);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleThirst);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleFood);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleDiet);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleFire);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleWater);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleRain);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleSprinting);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handleUnderground);
    ENVIRONMENT.register(ModifierHandlerEnvironmental::handlePotionEffects);
  }

  public static Optional<ModifierEnvironmental> handleGeneralTemperature(EntityPlayer player) {
    return ModifierEnvironmental.defined("environment",
      ModifierEnvironmental.getEnvironmentTemperature(player),
      ModifierEnvironmental.getEnvironmentHumidity(player));
  }

  public static Optional<ModifierEnvironmental> handleFire(EntityPlayer player) {
    return ModifierEnvironmental.defined("on_fire", 4f, 4f)
      .filter(mod -> player.isBurning());
  }

  public static Optional<ModifierEnvironmental> handleRain(EntityPlayer player) {
    if (player.world.isRaining()) {
      if (ModifierEnvironmental.getSkylight(player) < 15) {
        return ModifierEnvironmental.defined("weather", -2f, 0.1f);
      } else {
        return ModifierEnvironmental.defined("weather", -4f, 0.3f);
      }
    } else {
      return ModifierEnvironmental.none();
    }
  }

  public static Optional<ModifierEnvironmental> handleUnderground(EntityPlayer player) {
    if (ModifierEnvironmental.getSkylight(player) < 2) {
      return ModifierEnvironmental.defined("underground", -6f, 0.2f);
    } else {
      return ModifierEnvironmental.none();
    }
  }

  public static Optional<ModifierEnvironmental> handleSprinting(EntityPlayer player) {
    if (player.isSprinting()) {
      return ModifierEnvironmental.defined("sprint", 2f, 0.3f);
    } else {
      return ModifierEnvironmental.none();
    }
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

  public static Optional<ModifierEnvironmental> handlePotionEffects(EntityPlayer player) {
    if (player.isPotionActive(EffectsCore.HYPOTHERMIA.get())) {
      return ModifierEnvironmental.defined("hypothermia_effect", -10F, 0);
    }
    if (player.isPotionActive(EffectsCore.HYPERTHERMIA.get())) {
      return ModifierEnvironmental.defined("hyperthermia_effect", 10F, 0);
    }
    return ModifierEnvironmental.none();
  }

  public static Optional<ModifierEnvironmental> handleWater(EntityPlayer player) {
    if (player.isInWater()) {
      BlockPos pos = player.getPosition();
      IBlockState state = player.world.getBlockState(pos);
      var block = state.getBlock();
      if (block == FluidsCore.HOT_WATER.get().getBlock()) {
        return ModifierEnvironmental.defined("in_hot_water", 5f, 6f);
      } else if (block == Blocks.LAVA) {
        return ModifierEnvironmental.defined("in_lava", 10f, 5f);
      } else if (block == FluidsCore.SALT_WATER.get().getBlock() && player.world.getBiome(pos).getTempCategory() == Biome.TempCategory.OCEAN) {
        return ModifierEnvironmental.defined("in_ocean_water", -8f, 6f);
      } else {
        return ModifierEnvironmental.defined("in_water", -5f, 6f);
      }
    } else {
      return ModifierEnvironmental.none();
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
