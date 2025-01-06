package tfctech;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.objects.items.rock.ItemRock;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFCTECH)
public final class CommonEventHandler {

  @SubscribeEvent
  public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    Item item = stack.getItem();
    if (!stack.isEmpty()) {
      // Attach missing heat capability to rocks
      if (item instanceof ItemRock) {
        ICapabilityProvider heatCap = new CapabilityProviderHeat(stack.getTagCompound(), 0.2f, 2000f);
        event.addCapability(CapabilityHeat.KEY, heatCap);
      }
    }
  }
}
