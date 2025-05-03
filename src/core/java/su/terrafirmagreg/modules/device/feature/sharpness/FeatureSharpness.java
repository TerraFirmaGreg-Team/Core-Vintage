package su.terrafirmagreg.modules.device.feature.sharpness;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.device.feature.sharpness.capability.CapabilitySharpness;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureSharpness extends FeatureBase {

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    CapabilitySharpness.register();
  }

  @Override
  public void onPostInit(FMLPostInitializationEvent event) {

    CapabilitySharpness.Handler.init();
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  @SideOnly(Side.CLIENT)
  public static void onItemTooltipEvent(ItemTooltipEvent event) {
    var stack = event.getItemStack();
    var tooltip = event.getToolTip();
    if (!StackUtils.isValid(stack)) {return;}

    CapabilityUtils.getOptional(stack, CapabilitySharpness.CAPABILITY).ifPresent(cap -> {
      if (cap.getCharges() > 0) {
        TextFormatting color = cap.getCharges() > 64 ? cap.getCharges() > 256 ? TextFormatting.DARK_PURPLE : TextFormatting.BLUE : TextFormatting.DARK_GREEN;
        tooltip.add(I18n.format(ModUtils.localize("tooltip", "device.sharpness"), color, "" + cap.getCharges()));
      }
    });
  }

  @SubscribeEvent
  public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    if (!StackUtils.isValid(stack)) {return;}

    ICapabilityProvider provider = CapabilitySharpness.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilitySharpness.KEY, provider);
  }
}
