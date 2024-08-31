package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.damage.HandlerDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.capabilities.egg.HandlerEgg;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.modules.core.capabilities.sharpness.HandlerSharpness;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.HandlerSize;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class EventHandlerCapabilitiesItem {

    @SubscribeEvent
    public void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {

        ItemStack stack = event.getObject();
        if (stack.isEmpty()) return;

        size(event, stack);
        food(event, stack);
        metal(event, stack);
        heat(event, stack);
        sharpness(event, stack);
        egg(event, stack);
        damageResistance(event, stack);
    }

    public void size(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

        if (CapabilitySize.getIItemSize(stack) != null) return;

        ICapabilityProvider provider = HandlerSize.getCustom(stack);

        event.addCapability(CapabilitySize.KEY, provider);

        if (provider instanceof ICapabilitySize itemSize) {
            // Only modify the stack size if the item was stackable in the first place
            // Note: this is called in many cases BEFORE all custom capabilities are added.
            int prevStackSize = stack.getMaxStackSize();
            if (prevStackSize != 1) {
                stack.getItem().setMaxStackSize(itemSize.getStackSize(stack));
            }
        }

    }

    public void food(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

    }

    public void metal(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

    }

    public void heat(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

    }

    public void sharpness(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

        ICapabilityProvider provider = HandlerSharpness.getCustom(stack);
        if (provider == null) return;

        event.addCapability(CapabilitySharpness.KEY, provider);
    }

    public void egg(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

        ICapabilityProvider provider = HandlerEgg.getCustom(stack);
        if (provider == null) return;

        event.addCapability(CapabilityEgg.KEY, provider);
    }

    public void damageResistance(AttachCapabilitiesEvent<ItemStack> event, @NotNull ItemStack stack) {

        ICapabilityProvider provider = HandlerDamageResistance.getCustom(stack);
        if (provider == null) return;

        event.addCapability(CapabilityDamageResistance.KEY, provider);
    }
}
