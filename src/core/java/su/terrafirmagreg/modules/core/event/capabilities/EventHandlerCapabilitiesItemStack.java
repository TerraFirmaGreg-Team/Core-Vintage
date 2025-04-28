package su.terrafirmagreg.modules.core.event.capabilities;

import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityHandlerDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityHandlerEgg;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilityHandlerSharpness;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityHandlerSize;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;

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

    size(event, stack);
    food(event, stack);
    metal(event, stack);
    heat(event, stack);
    sharpness(event, stack);
    egg(event, stack);
    damageResistance(event, stack);
  }


  private static void size(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

//    if (CapabilitySize.getIItemSize(stack) != null) {
//      return;
//    }

    ICapabilityProvider provider = CapabilityHandlerSize.getCustom(stack);

    event.addCapability(CapabilitySize.KEY, provider);

    if (provider instanceof ICapabilitySize itemSize) {
      // Only modify the stack size if the item was stackable in the first place
      // Note: this is called in many cases BEFORE all custom capabilities are added.
      int prevStackSize = stack.getMaxStackSize();
      var item = stack.getItem();
      if (prevStackSize != 1) {
        item.setMaxStackSize(itemSize.getStackSize(stack));
      }
    }

  }

  private static void food(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

  }

  private static void metal(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

  }

  private static void heat(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

  }

  private static void sharpness(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

    ICapabilityProvider provider = CapabilityHandlerSharpness.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilitySharpness.KEY, provider);
  }

  private static void egg(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

    ICapabilityProvider provider = CapabilityHandlerEgg.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilityEgg.KEY, provider);
  }

  private static void damageResistance(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

    ICapabilityProvider provider = CapabilityHandlerDamageResistance.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilityDamageResistance.KEY, provider);
  }
}
