package su.terrafirmagreg.temp.modules.ambiental.compat;

import su.terrafirmagreg.temp.modules.ambiental.capability.TemperatureCapability;
import su.terrafirmagreg.temp.modules.ambiental.modifier.EnvironmentalModifier;
import su.terrafirmagreg.temp.modules.ambiental.modifier.TempModifier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import net.dries007.tfc.objects.te.TEBloomery;
import net.dries007.tfc.objects.te.TECharcoalForge;
import net.dries007.tfc.objects.te.TECrucible;
import net.dries007.tfc.objects.te.TEFirePit;
import net.dries007.tfc.objects.te.TELamp;

import java.util.Optional;

import static su.terrafirmagreg.temp.modules.ambiental.api.ITileEntityTemperatureProvider.hasProtection;
import static su.terrafirmagreg.temp.modules.ambiental.api.ITileEntityTemperatureProvider.mod;

public class TFC {

  public static Optional<TempModifier> handleCharcoalForge(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TECharcoalForge forge) {

      float temp = forge.getField(TECharcoalForge.FIELD_TEMPERATURE);
      float change = temp / 140f;
      if (hasProtection(player)) {
        change = change * mod;
      }
      return TempModifier.defined("charcoal_forge", change, 0);
    } else {
      return TempModifier.none();
    }
  }

  public static Optional<TempModifier> handleFirePit(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEFirePit pit) {

      float temp = pit.getField(TEFirePit.FIELD_TEMPERATURE);
      float change = temp / 100f;
      if (hasProtection(player)) {
        change = change * mod;
      }
      return TempModifier.defined("fire_pit", Math.min(6f, change), 0);
    } else {
      return TempModifier.none();
    }
  }

  public static Optional<TempModifier> handleBloomery(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEBloomery bloomery) {

      float change = bloomery.getRemainingTicks() > 0 ? 4f : 0f;
      if (hasProtection(player)) {
        change = change * mod;
      }
      return TempModifier.defined("bloomery", change, 0);
    } else {
      return TempModifier.none();
    }
  }

  public static Optional<TempModifier> handleCrucible(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TECrucible crucible) {

      float temp = crucible.getField(TECrucible.FIELD_TEMPERATURE);
      float change = temp / 100f;
      if (hasProtection(player)) {
        change = change * mod;
      }
      return TempModifier.defined("crucible", change, 0);
    } else {
      return TempModifier.none();
    }
  }

  public static Optional<TempModifier> handleLamps(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TELamp lamp) {

      if (EnvironmentalModifier.getEnvironmentTemperature(player) < TemperatureCapability.AVERAGE) {
        float change = (lamp.isPowered() && lamp.getFuel() > 0) ? 1f : 0f;
        float potency = 0f;
        return TempModifier.defined("lamp", change, potency);
      }
    }
    return TempModifier.none();
  }
}
