package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEquipment;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderEquipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import gregtech.common.items.MetaItems;

import java.util.Optional;

public final class ModifierHandlerEquipment {

  public static final AmbientalRegistry<IAmbientalProviderEquipment> EQUIPMENT = new AmbientalRegistry<>();

  static {

    EQUIPMENT.register((player, stack) ->
      ModifierEquipment.defined("nanoHelmet", 0f, 0f)
        .filter(mod -> stack.getItem() == MetaItems.NANO_HELMET.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierEquipment.defined("nanoChestplate", 0f, 0f)
        .filter(mod -> stack.getItem() == MetaItems.NANO_CHESTPLATE.getStackForm().getItem()
                       || stack.getItem() == MetaItems.NANO_CHESTPLATE_ADVANCED.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierEquipment.defined("nanoLeggings", 0f, 0f)
        .filter(mod -> stack.getItem() == MetaItems.NANO_LEGGINGS.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierEquipment.defined("nanoBoots", 0f, 0f)
        .filter(mod -> stack.getItem() == MetaItems.NANO_BOOTS.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierEquipment.defined("quantumHelmet", 0f, 0f)
        .filter(mod -> stack.getItem() == MetaItems.QUANTUM_HELMET.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierEquipment.defined("quantumChestplate", 0f, 0f)
        .filter(mod -> stack.getItem() == MetaItems.QUANTUM_CHESTPLATE.getStackForm().getItem()
                       || stack.getItem() == MetaItems.QUANTUM_CHESTPLATE_ADVANCED.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierEquipment.defined("quantumLeggings", 0f, 0f)
        .filter(mod -> stack.getItem() == MetaItems.QUANTUM_LEGGINGS.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierEquipment.defined("quantumBoots", 0f, 0f)
        .filter(mod -> stack.getItem() == MetaItems.QUANTUM_BOOTS.getStackForm().getItem())
    );

    EQUIPMENT.register(ModifierHandlerEquipment::handleArmor);
  }

  private static Optional<ModifierEquipment> handleArmor(EntityPlayer player, ItemStack itemStack) {
    if (itemStack.getItem() instanceof ItemArmor thing) {
      if (thing.armorType == EntityEquipmentSlot.HEAD) {
        if (player.world.getLight(player.getPosition()) > 14) {
          float envTemp = ModifierEnvironmental.getEnvironmentTemperature(player);
          if (envTemp > CapabilityProviderAmbiental.AVERAGE + 3) {
            float diff = envTemp - CapabilityProviderAmbiental.AVERAGE;
            return ModifierEquipment.defined("helmet", -diff / 3f, -0.5f);
          } else {
            return ModifierEquipment.defined("armor", 3f, -0.25f);
          }
        }
      } else {
        float envTemp = ModifierEnvironmental.getEnvironmentTemperature(player);
        if (envTemp > CapabilityProviderAmbiental.AVERAGE + 3) {
          return ModifierEquipment.defined("armor", 3f, -0.25f);
        }
      }
    }
    return ModifierEquipment.none();
  }

}
