package su.terrafirmagreg.temp.modules.ambiental.compat;


import su.terrafirmagreg.temp.modules.ambiental.modifier.TempModifier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import net.dries007.tfc.objects.te.TECrucible;
import tfctech.objects.tileentities.TEElectricForge;
import tfctech.objects.tileentities.TEFridge;
import tfctech.objects.tileentities.TEInductionCrucible;
import tfctech.objects.tileentities.TESmelteryCauldron;
import tfctech.objects.tileentities.TESmelteryFirebox;

import java.util.Optional;

import static su.terrafirmagreg.temp.modules.ambiental.api.ITileEntityTemperatureProvider.hasProtection;
import static su.terrafirmagreg.temp.modules.ambiental.api.ITileEntityTemperatureProvider.mod;


public class TFCTech {

  public static Optional<TempModifier> handleElectricForge(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEElectricForge electricForge) {
      float temp = electricForge.getField(TECrucible.FIELD_TEMPERATURE);
      float change = temp / 100f;
      float potency = temp / 350f;
      if (hasProtection(player)) {
        change = change * mod;
      }
      return TempModifier.defined("electric_forge", change, potency);
    } else {
      return TempModifier.none();
    }
  }

  public static Optional<TempModifier> handleInductionCrucible(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEInductionCrucible inductionCrucible) {
      float temp = inductionCrucible.getField(TECrucible.FIELD_TEMPERATURE);
      float change = temp / 100f;
      float potency = temp / 350f;
      if (hasProtection(player)) {
        change = change * mod;
      }
      return TempModifier.defined("induction_crucible", change, potency);
    } else {
      return TempModifier.none();
    }
  }

  public static Optional<TempModifier> handleSmelteryCauldron(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TESmelteryCauldron smelteryCauldron) {
      float temp = smelteryCauldron.getField(TECrucible.FIELD_TEMPERATURE);
      float change = temp / 120f;
      float potency = temp / 370f;
      if (hasProtection(player)) {
        change = change * mod;
      }
      return TempModifier.defined("smeltery_cauldron", change, potency);
    } else {
      return TempModifier.none();
    }
  }

  public static Optional<TempModifier> handleSmelteryFirebox(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TESmelteryFirebox smelteryFirebox) {
      float temp = smelteryFirebox.getField(TECrucible.FIELD_TEMPERATURE);
      float change = temp / 120f;
      float potency = temp / 370f;
      if (hasProtection(player)) {
        change = change * mod;
      }
      return TempModifier.defined("smeltery_firebox", change, potency);
    } else {
      return TempModifier.none();
    }
  }

  public static Optional<TempModifier> handleFridge(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEFridge fridge) {

      float change = 0f;
      float potency = 0f;

      if (fridge.isOpen()) {
        change = -10f;
        potency = -0.7f;
      }

      return TempModifier.defined("fridge", change, potency);
    } else {
      return TempModifier.none();
    }
  }
}
