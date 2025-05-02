package su.terrafirmagreg.modules.core.feature.ambiental.spi.modifier;

import su.terrafirmagreg.modules.core.feature.ambiental.capability.CapabilityHandlerAmbiental;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.AmbientalModifierStorage;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.provider.IAmbientalProviderEquipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class ModifierEquipment extends ModifierBase {


  protected ModifierEquipment(String name) {
    super(name);

  }

  protected ModifierEquipment(String name, float change, float potency) {
    super(name, change, potency);

  }

  public static Optional<ModifierEquipment> defined(String name, float change, float potency) {
    return Optional.of(new ModifierEquipment(name, change, potency));
  }

  public static Optional<ModifierEquipment> none() {
    return Optional.empty();
  }

  public static void computeModifiers(EntityPlayer player, AmbientalModifierStorage storage) {
    Iterable<ItemStack> armor = player.getArmorInventoryList();
    for (ItemStack stack : armor) {
      var item = stack.getItem();
      if (item instanceof IAmbientalProviderEquipment provider) {
        storage.add(provider.getModifier(player, stack));
      }

      for (IAmbientalProviderEquipment provider : CapabilityHandlerAmbiental.EQUIPMENT) {
        storage.add(provider.getModifier(player, stack));
      }
    }
  }

}






