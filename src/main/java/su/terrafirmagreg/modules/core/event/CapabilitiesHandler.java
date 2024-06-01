package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.api.capabilities.size.CapabilitySize;
import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilitiesHandler {

    @SubscribeEvent
    public static void size(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (stack.isEmpty()) return;

        if (CapabilitySize.getIItemSize(stack) == null) {
            ICapabilityProvider sizeHandler = CapabilitySize.getCustomSize(stack);
            event.addCapability(CapabilitySize.KEY, sizeHandler);

            if (sizeHandler instanceof ICapabilitySize itemSize) {
                // Only modify the stack size if the item was stackable in the first place
                // Note: this is called in many cases BEFORE all custom capabilities are added.
                int prevStackSize = stack.getMaxStackSize();
                if (prevStackSize != 1) {
                    item.setMaxStackSize(itemSize.getStackSize(stack));
                }
            }
        }

    }

    @SubscribeEvent
    public static void food(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (stack.isEmpty()) return;

    }

    @SubscribeEvent
    public static void metal(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (stack.isEmpty()) return;

    }

    @SubscribeEvent
    public static void damageResistance(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (stack.isEmpty()) return;

    }

    @SubscribeEvent
    public static void egg(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (stack.isEmpty()) return;

    }
}
