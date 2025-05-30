package net.dries007.tfc.client.gui;

import su.terrafirmagreg.api.data.ToolTipKeys;
import su.terrafirmagreg.api.library.TextComponents;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.modules.core.feature.calendar.spi.Calendar;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.network.PacketSwitchPlayerInventoryTab;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

@SideOnly(Side.CLIENT)
public class GuiCalendar extends GuiContainerTFC {

  private static final ResourceLocation BACKGROUND = new ResourceLocation(TFC, "textures/gui/player_calendar.png");

  public GuiCalendar(Container container, InventoryPlayer playerInv) {
    super(container, playerInv, BACKGROUND);
  }

  @Override
  public void initGui() {
    super.initGui();

    int buttonId = 0;
    addButton(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.INVENTORY, guiLeft, guiTop, ++buttonId, true));
    addButton(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.SKILLS, guiLeft, guiTop, ++buttonId, true));
    addButton(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.CALENDAR, guiLeft, guiTop, ++buttonId, false));
    addButton(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.NUTRITION, guiLeft, guiTop, ++buttonId, true));
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    String tooltip = TextComponents.empty().color(TextFormatting.DARK_BLUE).underline()
      .translation(ModUtils.localize(ToolTipKeys.TOOLTIP, "core.calendar")).string(":").format();

    fontRenderer.drawString(tooltip, xSize / 2 - fontRenderer.getStringWidth(tooltip) / 2, 7, 0x404040);

    String season = TranslatorUtils.translate(ModUtils.localize(ToolTipKeys.TOOLTIP, "core.calendar.season"), Calendar.CALENDAR_TIME.getSeasonDisplayName());
    String day = TranslatorUtils.translate(ModUtils.localize(ToolTipKeys.TOOLTIP, "core.calendar.day"), Calendar.CALENDAR_TIME.getDisplayDayName());
    String date = TranslatorUtils.translate(ModUtils.localize(ToolTipKeys.TOOLTIP, "core.calendar.date"), Calendar.CALENDAR_TIME.getTimeAndDate());

    fontRenderer.drawString(season, xSize / 2 - fontRenderer.getStringWidth(season) / 2, 25, 0x404040);
    fontRenderer.drawString(day, xSize / 2 - fontRenderer.getStringWidth(day) / 2, 34, 0x404040);
    fontRenderer.drawString(date, xSize / 2 - fontRenderer.getStringWidth(date) / 2, 43, 0x404040);
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    if (button instanceof GuiButtonPlayerInventoryTab tabButton && tabButton.isActive()) {
      if (tabButton.isActive()) {
        if (tabButton.getGuiType() == TFCGuiHandler.Type.INVENTORY) {
          this.mc.displayGuiScreen(new GuiInventory(playerInv.player));
        }
        TerraFirmaCraft.getNetwork().sendToServer(new PacketSwitchPlayerInventoryTab(tabButton.getGuiType()));
      }
    }
  }
}
