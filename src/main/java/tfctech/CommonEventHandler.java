package tfctech;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.objects.items.rock.ItemRock;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCTECH;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID_TFCTECH)
public final class CommonEventHandler {

    @SubscribeEvent
    public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (!stack.isEmpty()) {
            // Attach missing heat capability to rocks
            if (item instanceof ItemRock) {
                ICapabilityProvider heatCap = new ItemHeatHandler(stack.getTagCompound(), 0.2f, 2000f);
                event.addCapability(CapabilityItemHeat.KEY, heatCap);
            }
        }
    }
}
