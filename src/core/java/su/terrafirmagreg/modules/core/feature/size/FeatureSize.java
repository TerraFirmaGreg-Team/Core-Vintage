package su.terrafirmagreg.modules.core.feature.size;

import su.terrafirmagreg.api.data.Unicode;
import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilitySize;
import su.terrafirmagreg.modules.core.feature.size.capability.ICapabilitySize;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureSize extends BaseFeature {


  public FeatureSize() {
    super(FeatureSettings.of()
      .enabled(ConfigCore.FEATURE.SIZE.enabled)
    );
  }

  @SubscribeEvent
  public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    if (!StackUtils.isValid(stack)) {return;}

    //    if (CapabilitySize.getIItemSize(stack) != null) {
//      return;
//    }

    ICapabilityProvider provider = CapabilitySize.getCustom(stack);

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

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  @SideOnly(Side.CLIENT)
  public static void onItemTooltipEvent(ItemTooltipEvent event) {

    var stack = event.getItemStack();
    var tooltip = event.getToolTip();

    if (!StackUtils.isValid(stack)) {return;}

    CapabilityUtils.getOptional(stack, CapabilitySize.CAPABILITY).ifPresent(cap -> {
      tooltip.add(
        Unicode.WEIGHT + " " + I18n.format(TranslatorUtils.getEnumName(cap.getWeight(stack))) + " " +
        Unicode.SIZE + " " + I18n.format(TranslatorUtils.getEnumName(cap.getSize(stack))) + " " +
        Unicode.STACK_SIZE + " " + cap.getStackSize(stack)
      );
    });
  }

  @SubscribeEvent
  public static void onPreInit(StateEvent.PreInitialization event) {

    CapabilitySize.register();
  }

  @SubscribeEvent
  public static void onPostInit(StateEvent.PostInitialization event) {
    CapabilitySize.Handler.init();
  }
}
