package su.terrafirmagreg.modules.core.feature.ambiental.modifier;

import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalModifierStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ModifierEquipment extends ModifierBase {


  public ModifierEquipment(String name) {
    super(name);

  }

  public ModifierEquipment(String name, float change, float potency) {
    super(name, change, potency);

  }

  public static void computeModifiers(EntityPlayer player, AmbientalModifierStorage modifiers) {
    Iterable<ItemStack> armor = player.getArmorInventoryList();
    for (ItemStack stack : armor) {
      if (stack.getItem() instanceof ItemArmor thing) {
        if (thing.armorType == EntityEquipmentSlot.HEAD) {
          if (player.world.getLight(player.getPosition()) > 14) {
            float envTemp = ModifierEnvironmental.getEnvironmentTemperature(player);
            if (envTemp > CapabilityProviderAmbiental.AVERAGE + 3) {
              float diff = envTemp - CapabilityProviderAmbiental.AVERAGE;
              modifiers.add(new ModifierEquipment("helmet", -diff / 3f, -0.5f));
            } else {
              modifiers.add(new ModifierEquipment("armor", 3f, -0.25f));
            }
          }
        } else {
          float envTemp = ModifierEnvironmental.getEnvironmentTemperature(player);
          if (envTemp > CapabilityProviderAmbiental.AVERAGE + 3) {
            modifiers.add(new ModifierEquipment("armor", 3f, -0.25f));
          }
        }
      }
    }
  }

}






