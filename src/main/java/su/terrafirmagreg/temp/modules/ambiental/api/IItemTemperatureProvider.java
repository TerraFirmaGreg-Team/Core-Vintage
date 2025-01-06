package su.terrafirmagreg.temp.modules.ambiental.api;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;
import su.terrafirmagreg.temp.modules.ambiental.modifier.TempModifier;
import su.terrafirmagreg.temp.modules.ambiental.modifier.TempModifierStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

@FunctionalInterface
public interface IItemTemperatureProvider {

  static void evaluateAll(EntityPlayer player, TempModifierStorage modifiers) {
    for (ItemStack stack : player.inventoryContainer.inventoryItemStacks) {
      for (IItemTemperatureProvider provider : AmbientalRegistry.ITEMS) {
        modifiers.add(provider.getModifier(player, stack));
      }
    }
  }

  static Optional<TempModifier> handleTemperatureCapability(EntityPlayer player, ItemStack stack) {
    if (stack.hasCapability(CapabilityHeat.CAPABILITY, null)) {
      ICapabilityHeat heat = stack.getCapability(CapabilityHeat.CAPABILITY, null);
      float temp = heat.getTemperature() / 800;
      float change = temp;
      float potency = 0f;
      return TempModifier.defined("heat_item", change, potency * stack.getCount());
    } else {
      return TempModifier.none();
    }
  }

  Optional<TempModifier> getModifier(EntityPlayer player, ItemStack stack);
}
