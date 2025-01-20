package su.terrafirmagreg.modules.core.feature.ambiental.modifier;

import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalModifierStorage;
import su.terrafirmagreg.modules.core.feature.ambiental.handler.ModifierHandlerEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderEnvironmental;
import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

import java.util.Optional;

import static su.terrafirmagreg.modules.core.feature.ambiental.handler.ModifierHandlerEnvironmental.ENVIRONMENT;

public class ModifierEnvironmental extends ModifierBase {


  protected ModifierEnvironmental(String name, float change, float potency) {
    super(name, change, potency);

  }

  public static Optional<ModifierEnvironmental> defined(String name, float change, float potency) {
    return Optional.of(new ModifierEnvironmental(name, change, potency));
  }

  public static Optional<ModifierEnvironmental> none() {
    return Optional.empty();
  }

  public static void computeModifiers(EntityPlayer player, AmbientalModifierStorage storage) {
    if (player instanceof IAmbientalProviderEnvironmental provider) {
      storage.add(provider.getModifier(player));
    }

    for (IAmbientalProviderEnvironmental provider : ENVIRONMENT) {
      storage.add(provider.getModifier(player));
    }
  }

  public static float getEnvironmentTemperature(EntityPlayer player) {
    float avg = Climate.getAvgTemp(player.world, player.getPosition());
    float actual = Climate.getActualTemp(player.world, player.getPosition());
    if (ConfigCore.MISC.AMBIENTAL.harsherTemperateAreas) {
      float diff = actual - CapabilityProviderAmbiental.AVERAGE;
      float sign = Math.signum(diff);
      float generalDiff = Math.abs(avg - CapabilityProviderAmbiental.AVERAGE);
      float mult0 = Math.max(0f, ConfigCore.MISC.AMBIENTAL.harsherMultiplier - 1f);
      float multiplier = 1 + Math.max(0, 1 - generalDiff / 55) * mult0;
      actual = CapabilityProviderAmbiental.AVERAGE + (diff + 1.5f * sign) * multiplier;
    }
    return actual;
  }

  public static float getEnvironmentHumidity(EntityPlayer player) {
    return Climate.getRainfall(player.world, player.getPosition()) / 3000;
  }

  public static int getSkylight(EntityPlayer player) {
    BlockPos pos = new BlockPos(player.getPosition());
    BlockPos pos2 = pos.add(0, 1.8, 0);
    return player.world.getLightFor(EnumSkyBlock.SKY, pos2);
  }

  public static float getEnvironmentTemperatureWithTimeOfDay(EntityPlayer player) {
    return getEnvironmentTemperature(player) + ModifierHandlerEnvironmental.handleTimeOfDay(player).get().getChange();
  }

  public static int getBlockLight(EntityPlayer player) {
    BlockPos pos = new BlockPos(player.getPosition());
    BlockPos pos2 = pos.add(0, 1, 0);
    return player.world.getLightFor(EnumSkyBlock.BLOCK, pos2);
  }

}
