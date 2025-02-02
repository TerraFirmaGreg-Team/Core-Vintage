package net.dries007.tfctech.client.gui;

import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.gui.GuiContainerTE;
import net.dries007.tfc.network.PacketGuiButton;
import net.dries007.tfctech.client.TechGuiHandler;
import net.dries007.tfctech.client.gui.button.GuiButtonSetter;
import net.dries007.tfctech.objects.tileentities.TEElectricForge;

import java.io.IOException;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

public class GuiElectricForge extends GuiContainerTE<TEElectricForge> {

  private static final ResourceLocation ELECTRIC_FORGE_BACKGROUND = new ResourceLocation(TFCTECH, "textures/gui/electric_forge.png");

  public GuiElectricForge(Container container, InventoryPlayer playerInv, TEElectricForge tile) {
    super(container, playerInv, tile, ELECTRIC_FORGE_BACKGROUND);
  }

  @Override
  public void initGui() {
    super.initGui();
    addButton(new GuiButtonSetter(0, guiLeft + 34, guiTop + 27, true));
    addButton(new GuiButtonSetter(1, guiLeft + 34, guiTop + 46, false));
  }

  @Override
  protected void renderHoveredToolTip(int mouseX, int mouseY) {
    if (mouseX >= guiLeft + 153 && mouseX <= guiLeft + 153 + 18 && mouseY >= guiTop + 6 && mouseY <= guiTop + 6 + 59) {
      int energy = tile.getField(1);
      drawHoveringText(I18n.format("tooltip.tfctech.gui.energy_format", energy, tile.getEnergyCapacity()), mouseX, mouseY);
    }
    super.renderHoveredToolTip(mouseX, mouseY);
  }

  @Override
  protected void actionPerformed(GuiButton button) throws IOException {
    int shift = isShiftKeyDown() ? 2 : 0;
    int ctrl = isCtrlKeyDown() ? 4 : 0;
    int value = button.id + (ctrl > 0 ? ctrl : shift);
    TerraFirmaCraft.getNetwork().sendToServer(new PacketGuiButton(value));
    super.actionPerformed(button);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

    //Draw the temperature bar
    mc.getTextureManager().bindTexture(TechGuiHandler.GUI_ELEMENTS);
    drawTexturedModalRect(this.guiLeft + 11, this.guiTop + 17, 39, 1, 9, 52);

    // Draw the temperature indicator
    int targetTemperature = tile.getField(0);
    int temperaturePixels = (int) (51 * Math.min(Heat.maxVisibleTemperature(), targetTemperature)
                                   / Heat.maxVisibleTemperature()); //Max temperature is brilliant white in tfc
    drawTexturedModalRect(guiLeft + 8, guiTop + 66 - temperaturePixels, 36, 54, 15, 5);

    // Draw the energy bar
    int energyPixels = Math.round(60 * tile.getField(1) / (float) tile.getEnergyCapacity());
    int emptyPixels = 60 - energyPixels;
    drawTexturedModalRect(guiLeft + 153, guiTop + 6, 0, 0, 18, emptyPixels);
    drawTexturedModalRect(guiLeft + 153, guiTop + 6 + emptyPixels, 18, emptyPixels, 18, energyPixels);

    //Draw the temperature value (for higher than brilliant white)
    String temp = I18n.format("tooltip.tfctech.gui.electric_forge.temperature_format", targetTemperature);
    int x = guiLeft + 41 - fontRenderer.getStringWidth(temp) / 2;
    int y = guiTop + 15;
    fontRenderer.drawString(temp, x, y, 0x000000);
  }
}
