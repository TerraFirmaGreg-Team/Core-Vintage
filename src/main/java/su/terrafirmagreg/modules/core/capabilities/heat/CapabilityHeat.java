package su.terrafirmagreg.modules.core.capabilities.heat;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilityHeat {

  public static final ResourceLocation KEY = ModUtils.resource("heat_capability");

  @CapabilityInject(ICapabilityHeat.class)
  public static final Capability<ICapabilityHeat> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilityHeat.class, new StorageHeat(),
        ProviderHeat::new);

  }

  public static ICapabilityHeat get(ItemStack itemStack) {
    return itemStack.getCapability(CAPABILITY, null);
  }

  public static boolean has(ItemStack itemStack) {
    return itemStack.hasCapability(CAPABILITY, null);
  }

  /**
   * Helper method to adjust temperature towards a value, without overshooting or stuttering
   */
  public static float adjustTempTowards(float temp, float target, float delta) {
    return adjustTempTowards(temp, target, delta, delta);
  }

  public static float adjustTempTowards(float temp, float target, float deltaPositive,
      float deltaNegative) {
    if (temp < target) {
      return Math.min(temp + deltaPositive, target);
    } else if (temp > target) {
      return Math.max(temp - deltaNegative, target);
    } else {
      return target;
    }
  }

  /**
   * Call this from within {@link ICapabilityHeat#getTemperature()}
   */
  public static float adjustTemp(float temp, float heatCapacity, long ticksSinceUpdate) {
    if (ticksSinceUpdate <= 0) {
      return temp;
    }
    final float newTemp = temp
        - heatCapacity * (float) ticksSinceUpdate * (float) ConfigCore.MISC.HEAT.globalModifier;
    return newTemp < 0 ? 0 : newTemp;
  }

  public static void addTemp(ICapabilityHeat instance) {
    // Default modifier = 3 (2x normal cooling)
    addTemp(instance, 3);
  }

  /**
   * Use this to increase the heat on an IItemHeat instance.
   *
   * @param modifier the modifier for how much this will heat up: 0 - 1 slows down cooling, 1 = no heating or cooling, > 1 heats, 2 heats at the same
   *                 rate of normal cooling, 2+ heats faster
   */
  public static void addTemp(ICapabilityHeat instance, float modifier) {
    final float temp = instance.getTemperature()
        + modifier * instance.getHeatCapacity() * (float) ConfigCore.MISC.HEAT.globalModifier;
    instance.setTemperature(temp);
  }

  public static float adjustToTargetTemperature(float temp, float burnTemp, int airTicks,
      int maxTempBonus) {
    boolean hasAir = airTicks > 0;
    float targetTemperature = burnTemp + (hasAir ? MathHelper.clamp(burnTemp, 0, maxTempBonus) : 0);
    if (temp != targetTemperature) {
      float delta = (float) ConfigCore.MISC.HEAT.heatingModifier;
      return adjustTempTowards(temp, targetTemperature, delta * (hasAir ? 2 : 1),
          delta * (hasAir ? 0.5f : 1));
    }
    return temp;
  }

}
