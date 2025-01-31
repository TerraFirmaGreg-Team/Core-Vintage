package su.terrafirmagreg.modules.core.event.player;

import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EventHandlerItemTooltip {

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public static void onItemTooltip(ItemTooltipEvent event) {

    ItemStack stack = event.getItemStack();
    if (stack.isEmpty()) {return;}
    boolean isAdvanced = event.getFlags().isAdvanced();
    List<String> tooltip = event.getToolTip();

    var size = CapabilitySize.get(stack);
    if (size != null) {
      size.addTooltipInfo(stack, tooltip);
    }

    var sharpness = CapabilitySharpness.get(stack);
    if (sharpness != null) {
      sharpness.addTooltipInfo(stack, tooltip);
    }

    var egg = CapabilityEgg.get(stack);
    if (egg != null) {
      egg.addTooltipInfo(stack, tooltip);
    }
  }
}
