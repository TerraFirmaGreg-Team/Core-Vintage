package su.terrafirmagreg.modules.animal.feature.egg;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.feature.egg.capability.CapabilityEgg;
import su.terrafirmagreg.modules.animal.feature.egg.capability.CapabilityHandlerEgg;
import su.terrafirmagreg.modules.core.feature.calendar.spi.Calendar;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureEgg extends FeatureBase {

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {
    CapabilityEgg.register();
  }

  @Override
  public void onInit(FMLInitializationEvent event) {
    CapabilityHandlerEgg.init();
  }

  @Override
  public boolean isEnabled() {
    return ConfigAnimal.FEATURE.EGG.enable;
  }

  @SubscribeEvent
  public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    if (!StackUtils.isValid(stack)) {return;}

    ICapabilityProvider provider = CapabilityHandlerEgg.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilityEgg.KEY, provider);
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  @SideOnly(Side.CLIENT)
  public static void onItemTooltipEvent(ItemTooltipEvent event) {

    var stack = event.getItemStack();
    var tooltip = event.getToolTip();

    if (!StackUtils.isValid(stack)) {return;}

    CapabilityUtils.getOptional(stack, CapabilityEgg.CAPABILITY).ifPresent(cap -> {
      if (cap.isFertilized()) {
        long remainingDays = cap.getHatchDay() - Calendar.PLAYER_TIME.getTotalDays();
        tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.fertilized"));
        if (remainingDays > 0) {
          tooltip.add(I18n.format("tfc.tooltip.egg_hatch", remainingDays));
        } else {
          tooltip.add(I18n.format("tfc.tooltip.egg_hatch_today"));
        }
      }
    });
  }
}
