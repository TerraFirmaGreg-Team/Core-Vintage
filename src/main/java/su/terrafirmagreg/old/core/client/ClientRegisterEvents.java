package su.terrafirmagreg.old.core.client;

import su.terrafirmagreg.Tags;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.feed_the_beast.ftbquests.client.ClientQuestFile;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MOD_ID)
public final class ClientRegisterEvents {

  private static final int advancementsButtonID = 5;

  @SubscribeEvent
  public static void DisableOpeningAdvancementsGui(GuiOpenEvent event) {
    if (event.getGui() instanceof GuiScreenAdvancements) {
      event.setCanceled(true);
    }
  }

  @SubscribeEvent
  public static void InsteadOfOpenAdvancementGuiOpenQuestGui(GuiScreenEvent.ActionPerformedEvent.Post event) {
    if (event.getGui() instanceof GuiIngameMenu) {
      if (event.getButton().id == advancementsButtonID) {
        ClientQuestFile.INSTANCE.openQuestGui(Minecraft.getMinecraft().player);
      }
    }
  }
}
