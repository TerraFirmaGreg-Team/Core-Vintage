package su.terrafirmagreg.modules.core.event.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.feed_the_beast.ftbquests.client.ClientQuestFile;

@SideOnly(Side.CLIENT)
public class EventHandlerGuiScreen {

  private static final int ADVANCEMENTS_BUTTON_ID = 5;

  @SubscribeEvent
  public static void on(GuiScreenEvent.ActionPerformedEvent.Post event) {
    if (event.getGui() instanceof GuiIngameMenu) {
      if (event.getButton().id == ADVANCEMENTS_BUTTON_ID) {
        ClientQuestFile.INSTANCE.openQuestGui(Minecraft.getMinecraft().player);
      }
    }
  }
}
