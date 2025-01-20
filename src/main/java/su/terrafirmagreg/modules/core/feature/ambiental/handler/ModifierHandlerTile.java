package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierTile;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.eerussianguy.firmalife.te.TEOven;
import net.dries007.tfc.objects.te.TEBloomery;
import net.dries007.tfc.objects.te.TECharcoalForge;
import net.dries007.tfc.objects.te.TECrucible;
import net.dries007.tfc.objects.te.TEFirePit;
import net.dries007.tfc.objects.te.TELamp;
import net.sharkbark.cellars.blocks.tileentity.TEIceBunker;
import org.apache.commons.lang3.reflect.FieldUtils;
import tfctech.objects.tileentities.TEElectricForge;
import tfctech.objects.tileentities.TEFridge;
import tfctech.objects.tileentities.TEInductionCrucible;
import tfctech.objects.tileentities.TESmelteryCauldron;
import tfctech.objects.tileentities.TESmelteryFirebox;

import java.util.Optional;

public final class ModifierHandlerTile {


  public static final AmbientalRegistry<IAmbientalProviderTile> TILE = new AmbientalRegistry<>();

  static {
    // TFC-Tech
    TILE.register(ModifierHandlerTile::handleSmelteryFirebox); // Топило для печи для стекла
    TILE.register(ModifierHandlerTile::handleSmelteryCauldron); // Печь для стекла
    TILE.register(ModifierHandlerTile::handleElectricForge); // Тигель
    TILE.register(ModifierHandlerTile::handleInductionCrucible); // Кузня
    TILE.register(ModifierHandlerTile::handleFridge); // Холодос

    // Cellar
    TILE.register(ModifierHandlerTile::handleCellar); // Подвал

    // Firmalife
    TILE.register(ModifierHandlerTile::handleClayOven); // Oven

    // TFC
    TILE.register(ModifierHandlerTile::handleCharcoalForge); // Угольная кузня
    TILE.register(ModifierHandlerTile::handleFirePit); // Костер
    TILE.register(ModifierHandlerTile::handleBloomery); // Доменка
    TILE.register(ModifierHandlerTile::handleLamps); // Лампа
    TILE.register(ModifierHandlerTile::handleCrucible); // Тигель
  }

  public static Optional<ModifierTile> handleCellar(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEIceBunker iceBunker) {

      boolean isComplete = false;
      float temperature = 0.0f;

      try {
        isComplete = (boolean) FieldUtils.readField(iceBunker, "isComplete", true);
        temperature = (float) FieldUtils.readField(iceBunker, "temperature", true);
      } catch (Exception e) {
        e.printStackTrace();
      }

      float change = 0.0f;
      float potency = 0.0f;

      if (isComplete) {

        if (temperature < 10) {
          change = -2f * (12 - temperature);
          potency = -0.5f;
        }

        if (ModifierTile.hasProtection(player)) {
          change = 1.0F;
        }
      }

      return ModifierTile.defined("cellar", change, potency);
    } else {
      return ModifierTile.none();
    }
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


  public static Optional<ModifierTile> handleCharcoalForge(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TECharcoalForge forge) {

      float temp = forge.getField(TECharcoalForge.FIELD_TEMPERATURE);
      float change = temp / 140f;
      if (ModifierTile.hasProtection(player)) {
        change = 1.0F;
      }
      return ModifierTile.defined("charcoal_forge", change, 0);
    } else {
      return ModifierTile.none();
    }
  }

  public static Optional<ModifierTile> handleFirePit(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEFirePit pit) {

      float temp = pit.getField(TEFirePit.FIELD_TEMPERATURE);
      float change = temp / 100f;
      if (ModifierTile.hasProtection(player)) {
        change = 1.0F;
      }
      return ModifierTile.defined("fire_pit", Math.min(6f, change), 0);
    } else {
      return ModifierTile.none();
    }
  }

  public static Optional<ModifierTile> handleBloomery(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEBloomery bloomery) {

      float change = bloomery.getRemainingTicks() > 0 ? 4f : 0f;
      if (ModifierTile.hasProtection(player)) {
        change = 1.0F;
      }
      return ModifierTile.defined("bloomery", change, 0);
    } else {
      return ModifierTile.none();
    }
  }

  public static Optional<ModifierTile> handleCrucible(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TECrucible crucible) {

      float temp = crucible.getField(TECrucible.FIELD_TEMPERATURE);
      float change = temp / 100f;
      if (ModifierTile.hasProtection(player)) {
        change = 1.0F;
      }
      return ModifierTile.defined("crucible", change, 0);
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
    if (tile instanceof TEElectricForge electricForge) {
      float temp = electricForge.getField(TECrucible.FIELD_TEMPERATURE);
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
    if (tile instanceof TEInductionCrucible inductionCrucible) {
      float temp = inductionCrucible.getField(TECrucible.FIELD_TEMPERATURE);
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

  public static Optional<ModifierTile> handleSmelteryCauldron(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TESmelteryCauldron smelteryCauldron) {
      float temp = smelteryCauldron.getField(TECrucible.FIELD_TEMPERATURE);
      float change = temp / 120f;
      float potency = temp / 370f;
      if (ModifierTile.hasProtection(player)) {
        change = 1.0F;
      }
      return ModifierTile.defined("smeltery_cauldron", change, potency);
    } else {
      return ModifierTile.none();
    }
  }

  public static Optional<ModifierTile> handleSmelteryFirebox(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TESmelteryFirebox smelteryFirebox) {
      float temp = smelteryFirebox.getField(TECrucible.FIELD_TEMPERATURE);
      float change = temp / 120f;
      float potency = temp / 370f;
      if (ModifierTile.hasProtection(player)) {
        change = 1.0F;
      }
      return ModifierTile.defined("smeltery_firebox", change, potency);
    } else {
      return ModifierTile.none();
    }
  }

  public static Optional<ModifierTile> handleFridge(EntityPlayer player, TileEntity tile) {
    if (tile instanceof TEFridge fridge) {

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
