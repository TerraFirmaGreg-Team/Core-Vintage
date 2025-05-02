package su.terrafirmagreg.modules.core.event.capabilities;

import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityHandlerDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilityHandlerSharpness;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.jetbrains.annotations.NotNull;

public class EventHandlerCapabilitiesItemStack {


  @SubscribeEvent
  public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    if (stack.isEmpty()) {
      return;
    }

    sharpness(event, stack);
    damageResistance(event, stack);
  }


  private static void sharpness(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

    ICapabilityProvider provider = CapabilityHandlerSharpness.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilitySharpness.KEY, provider);
  }

  private static void damageResistance(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

    ICapabilityProvider provider = CapabilityHandlerDamageResistance.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilityDamageResistance.KEY, provider);
  }
}
