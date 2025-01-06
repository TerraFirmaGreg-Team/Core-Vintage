package su.terrafirmagreg.modules.core.feature.ambiental.modifier;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalModifierStorage;
import su.terrafirmagreg.modules.core.feature.ambiental.handler.ModifierHandlerItem;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ModifierItem extends ModifierBase {

  public ModifierItem(String name) {
    super(name);
  }

  public ModifierItem(String name, float change, float potency) {
    super(name, change, potency);
  }

  public static void computeModifiers(EntityPlayer player, AmbientalModifierStorage modifiers) {
    for (ItemStack stack : player.inventoryContainer.inventoryItemStacks) {
      if (CapabilityHeat.has(stack)) {
        var cap = CapabilityHeat.get(stack);
        float temp = cap.getTemperature() / 500;
        float change = temp;
        float potency = 0f;
        modifiers.add(new ModifierItem("heat_item", change, potency * stack.getCount()));
      }
      if (stack.getItem() instanceof IAmbientalProviderItem provider) {
        modifiers.add(provider.getModifier(player, stack));
      }
      for (IAmbientalProviderItem provider : ModifierHandlerItem.ITEM) {
        modifiers.add(provider.getModifier(player, stack));
      }
    }
  }

}
