package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierTile;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderTile;
import su.terrafirmagreg.modules.device.object.tile.TileCrucible;
import su.terrafirmagreg.modules.device.object.tile.TileElectricForge;
import su.terrafirmagreg.modules.device.object.tile.TileFridge;
import su.terrafirmagreg.modules.device.object.tile.TileInductionCrucible;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import net.dries007.tfc.objects.te.TELamp;
import net.dries007.tfc.objects.te.TEOven;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Optional;

public final class ModifierHandlerTile {


  public static final AmbientalRegistry<IAmbientalProviderTile> TILE = new AmbientalRegistry<>();

  static {
    // TFC-Tech
    TILE.register(ModifierHandlerTile::handleElectricForge); // Тигель
    TILE.register(ModifierHandlerTile::handleInductionCrucible); // Кузня
    TILE.register(ModifierHandlerTile::handleFridge); // Холодос

    // Firmalife
    TILE.register(ModifierHandlerTile::handleClayOven); // Oven

    // TFC
    TILE.register(ModifierHandlerTile::handleLamps); // Лампа
  }


  public static Optional<ModifierTile> handleClayOven(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEOven oven) {

      boolean isBurning = false;

      try {
        isBurning = (boolean) FieldUtils.readField(oven, "isBurning", true);
      } catch (Exception e) {
        e.printStackTrace();
      }

      float change = 0.0f;
      float potency = 1.0f;

      if (isBurning) {
        change = 8f;
        potency = 4f;

        if (ModifierTile.hasProtection(player)) {
          change = 1.0F;
        }
      }

      return ModifierTile.defined("firmalife_oven", change, potency);
    } else {
      return ModifierTile.none();
    }
  }

  public static Optional<ModifierTile> handleLamps(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TELamp lamp) {

      if (ModifierEnvironmental.getEnvironmentTemperature(player) < CapabilityProviderAmbiental.AVERAGE) {
        float change = (lamp.isPowered() && lamp.getFuel() > 0) ? 1f : 0f;
        float potency = 0f;
        return ModifierTile.defined("lamp", change, potency);
      }
    }
    return ModifierTile.none();
  }

  public static Optional<ModifierTile> handleElectricForge(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TileElectricForge electricForge) {
      float temp = electricForge.getField(TileCrucible.FIELD_TEMPERATURE);
      float change = temp / 100f;
      float potency = temp / 350f;
      if (ModifierTile.hasProtection(player)) {
        change = 1.0F;
      }
      return ModifierTile.defined("electric_forge", change, potency);
    } else {
      return ModifierTile.none();
    }
  }

  public static Optional<ModifierTile> handleInductionCrucible(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TileInductionCrucible inductionCrucible) {
      float temp = inductionCrucible.getField(TileCrucible.FIELD_TEMPERATURE);
      float change = temp / 100f;
      float potency = temp / 350f;
      if (ModifierTile.hasProtection(player)) {
        change = 1.0F;
      }
      return ModifierTile.defined("induction_crucible", change, potency);
    } else {
      return ModifierTile.none();
    }
  }

  public static Optional<ModifierTile> handleFridge(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TileFridge fridge) {

      float change = 0f;
      float potency = 0f;

      if (fridge.isOpen()) {
        change = -10f;
        potency = -0.7f;
      }

      return ModifierTile.defined("fridge", change, potency);
    } else {
      return ModifierTile.none();
    }
  }

}
