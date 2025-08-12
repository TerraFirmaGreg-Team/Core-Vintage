package su.terrafirmagreg.modules.animal.feature.egg;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.feature.egg.capability.CapabilityEgg;
import su.terrafirmagreg.modules.animal.feature.egg.capability.CapabilityHandlerEgg;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityHandlerFood;
import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityHandlerMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.modules.core.feature.calendar.spi.Calendar;
import su.terrafirmagreg.modules.core.object.gui.GuiOverlayAmbiental;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureEgg extends BaseFeature {

  public FeatureEgg() {
    super(Settings.of()
      .name("egg")
      .enabled(ConfigAnimal.FEATURE.EGG.enable)
    );
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

  @Override
  public void onPreInit() {
    CapabilityEgg.register();

    // TODO: move
    CapabilityFood.register();
    CapabilityMetal.register();
    CapabilityForgeable.register();

    if (ModUtils.isClient()) {
      MinecraftForge.EVENT_BUS.register(GuiOverlayAmbiental.getInstance());
    }
  }

  @Override
  public void onInit() {

    CapabilityHandlerEgg.init();

    // TODO: move
    CapabilityHandlerFood.init();
    CapabilityHandlerMetal.init();
  }
}
