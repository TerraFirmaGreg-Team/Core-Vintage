package su.terrafirmagreg.modules.core.event.client;

import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EventHandlerGuiOpen {

  @SubscribeEvent
  public static void on(GuiOpenEvent event) {
    var gui = event.getGui();
    if (gui instanceof GuiScreenAdvancements) {
      event.setCanceled(true);
    }
  }
}
