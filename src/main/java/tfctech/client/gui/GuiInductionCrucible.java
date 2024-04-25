package tfctech.client.gui;

import su.terrafirmagreg.modules.device.client.gui.GuiCrucible;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;


import tfctech.client.TechGuiHandler;
import tfctech.objects.tileentities.TEInductionCrucible;

public class GuiInductionCrucible extends GuiCrucible {

    public GuiInductionCrucible(Container container, InventoryPlayer playerInv, TEInductionCrucible tile) {
        super(container, playerInv, tile);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        if (mouseX >= guiLeft + 8 && mouseX <= guiLeft + 8 + 16 && mouseY >= guiTop + 79 && mouseY <= guiTop + 79 + 57) {
            int energy = tile.getField(1);
            drawHoveringText(I18n.format("tooltip.tfctech.gui.energy_format", energy, ((TEInductionCrucible) tile).getEnergyCapacity()), mouseX,
                    mouseY);
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        // Draw the energy bar
        mc.getTextureManager().bindTexture(TechGuiHandler.GUI_ELEMENTS);
        int energyPixels = Math.round(60 * tile.getField(1) / (float) ((TEInductionCrucible) tile).getEnergyCapacity());
        int emptyPixels = 60 - energyPixels;
        drawTexturedModalRect(guiLeft + 7, guiTop + 78, 0, 0, 18, emptyPixels);
        drawTexturedModalRect(guiLeft + 7, guiTop + 78 + emptyPixels, 18, emptyPixels, 18, energyPixels);
    }
}
