package su.terrafirmagreg.modules.core.feature.ambiental.modifier;

import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalModifierStorage;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderEquipment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

import static su.terrafirmagreg.modules.core.feature.ambiental.handler.ModifierHandlerEquipment.EQUIPMENT;

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

      for (IAmbientalProviderEquipment provider : EQUIPMENT) {
        storage.add(provider.getModifier(player, stack));
      }
    }
  }

}






