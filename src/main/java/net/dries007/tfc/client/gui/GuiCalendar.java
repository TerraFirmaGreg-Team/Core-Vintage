package net.dries007.tfc.client.gui;

import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.module.core.ModuleCore;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.network.SCPacketSwitchPlayerInventoryTab;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCalendar extends GuiContainerTFC {
    private static final ResourceLocation BACKGROUND = Helpers.getID("textures/gui/player_calendar.png");

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

        String tooltip = TextFormatting.WHITE + "" + TextFormatting.UNDERLINE + I18n.format("tfc.tooltip.calendar") + ":";
        fontRenderer.drawString(tooltip, xSize / 2 - fontRenderer.getStringWidth(tooltip) / 2, 7, 0x404040);

        String season, day, date, temperature, rainfall, error;

        season = I18n.format("tfc.tooltip.season", CalendarTFC.CALENDAR_TIME.getSeasonDisplayName());
        day = I18n.format("tfc.tooltip.day", CalendarTFC.CALENDAR_TIME.getDisplayDayName());
        date = I18n.format("tfc.tooltip.date", CalendarTFC.CALENDAR_TIME.getTimeAndDate());

        fontRenderer.drawString(season, xSize / 2 - fontRenderer.getStringWidth(season) / 2, 25, 0x404040);
        fontRenderer.drawString(day, xSize / 2 - fontRenderer.getStringWidth(day) / 2, 34, 0x404040);
        fontRenderer.drawString(date, xSize / 2 - fontRenderer.getStringWidth(date) / 2, 43, 0x404040);

        var blockpos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
        var chunk = mc.world.getChunk(blockpos);

        if (mc.world.isBlockLoaded(blockpos) && !chunk.isEmpty()) {
            var data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);

            if (data != null) {
                temperature = I18n.format("tfc.tooltip.temperature", String.format("%.1f", ClimateTFC.getActualTemp(blockpos)));
                rainfall = I18n.format("tfc.tooltip.rainfall", String.format("%.1f", data.getRainfall()));

                fontRenderer.drawString(temperature, xSize / 2 - fontRenderer.getStringWidth(temperature) / 2, 52, 0x404040);
                fontRenderer.drawString(rainfall, xSize / 2 - fontRenderer.getStringWidth(rainfall) / 2, 61, 0x404040);
            } else {
                error = "Fail to get chunk data (?)";

                fontRenderer.drawString("Fail to get chunk data (?)", xSize / 2 - fontRenderer.getStringWidth(error) / 2, 61, 0x404040);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof GuiButtonPlayerInventoryTab tabButton && ((GuiButtonPlayerInventoryTab) button).isActive()) {
            if (tabButton.isActive()) {
                if (tabButton.getGuiType() == TFCGuiHandler.Type.INVENTORY) {
                    this.mc.displayGuiScreen(new GuiInventory(playerInv.player));
                }
                ModuleCore.PACKET_SERVICE.sendToServer(new SCPacketSwitchPlayerInventoryTab(tabButton.getGuiType()));
            }
        }
    }
}
