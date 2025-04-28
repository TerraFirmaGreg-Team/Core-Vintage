package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierItem;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public final class ModifierHandlerItem {


  public static final AmbientalRegistry<IAmbientalProviderItem> ITEM = new AmbientalRegistry<>();

  static {
    ITEM.register(ModifierHandlerItem::handleHeatItem);
  }

  private static Optional<ModifierItem> handleHeatItem(EntityPlayer player, ItemStack stack) {
    if (CapabilityHeat.has(stack)) {
      var cap = CapabilityHeat.get(stack);
      float change = cap.getTemperature() / 500;
      float potency = 0f;
      return ModifierItem.defined("heat_item", change, potency * stack.getCount());
    }
    return ModifierItem.none();
  }

}
