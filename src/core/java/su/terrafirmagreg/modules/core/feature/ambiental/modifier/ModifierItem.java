package su.terrafirmagreg.modules.core.feature.ambiental.modifier;

import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalModifierStorage;
import su.terrafirmagreg.modules.core.feature.ambiental.handler.ModifierHandlerItem;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class ModifierItem extends ModifierBase {

  protected ModifierItem(String name) {
    super(name);
  }

  protected ModifierItem(String name, float change, float potency) {
    super(name, change, potency);
  }

  public static Optional<ModifierItem> defined(String name, float change, float potency) {
    return Optional.of(new ModifierItem(name, change, potency));
  }

  public static Optional<ModifierItem> none() {
    return Optional.empty();
  }

  public static void computeModifiers(EntityPlayer player, AmbientalModifierStorage storage) {
    for (ItemStack stack : player.inventory.mainInventory) {
      if (stack.getItem() instanceof IAmbientalProviderItem provider) {
        storage.add(provider.getModifier(player, stack));
      }
      for (IAmbientalProviderItem provider : ModifierHandlerItem.ITEM) {
        storage.add(provider.getModifier(player, stack));
      }
    }
  }

}
