package su.terrafirmagreg.modules.core.event.player;

import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EventHandlerItemTooltip {

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  @SideOnly(Side.CLIENT)
  public static void onHighest(ItemTooltipEvent event) {
    var stack = event.getItemStack();
    var tooltip = event.getToolTip();
    var isAdvanced = event.getFlags().isAdvanced();

    if (!StackUtils.isValid(stack)) {return;}

    capabilityHeat(tooltip, stack);
    capabilityMetal(tooltip, stack);
    capabilityForge(tooltip, stack);
    capabilityFood(tooltip, stack);
    capabilitySharpness(tooltip, stack);
  }

  private static void capabilityMetal(List<String> tooltips, ItemStack stack) {
    var forge = CapabilityMetal.get(stack);
    if (forge != null) {
      forge.addMetalInfo(stack, tooltips);
    }
  }

  private static void capabilityForge(List<String> tooltips, ItemStack stack) {
    var forge = CapabilityForgeable.get(stack);
    if (forge != null) {
      forge.addTooltipInfo(stack, tooltips);
    }
  }

  private static void capabilityFood(List<String> tooltips, ItemStack stack) {
    var food = CapabilityFood.get(stack);
    if (food != null) {
      food.addTooltipInfo(stack, tooltips);
    }
  }

  private static void capabilityHeat(List<String> tooltips, ItemStack stack) {
    var heat = CapabilityHeat.get(stack);
    if (heat != null) {
      heat.addHeatInfo(stack, tooltips);
    }
  }

  private static void capabilitySharpness(List<String> tooltips, ItemStack stack) {
    var sharpness = CapabilitySharpness.get(stack);
    if (sharpness != null) {
      sharpness.addTooltipInfo(stack, tooltips);
    }
  }


}
