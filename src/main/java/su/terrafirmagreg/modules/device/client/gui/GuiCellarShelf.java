package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.Unicode;
import su.terrafirmagreg.modules.device.objects.tiles.TileCellarShelf;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.client.gui.GuiContainerTE;

import java.util.ArrayList;
import java.util.List;

public class GuiCellarShelf extends GuiContainerTE<TileCellarShelf> {

    public static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/cellar_shelf.png");
    private final String translationKey;
    private final InventoryPlayer playerInventory;

    public GuiCellarShelf(Container container, InventoryPlayer playerInv, TileCellarShelf tile, IBlockState state) {
        super(container, playerInv, tile, BACKGROUND);
        this.playerInventory = playerInv;
        this.translationKey = state.getBlock().getTranslationKey();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(translationKey + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 00000000);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName()
                .getUnformattedText(), 8, this.ySize - 92, 00000000);

        if (mouseX >= guiLeft + 5 && mouseX <= guiLeft + 15 && mouseY >= guiTop + 5 && mouseY <= guiTop + 15) {
            List<String> infoText = new ArrayList<String>();
            float temperature = tile.getTemperature();

            if (temperature <= -1000) {
                infoText.add("[!] The shelf is not inside a cellar");
            } else {
                if (temperature < 0) {
                    infoText.add("Temperature: below zero");
                } else {
                    infoText.add("Temperature: " + String.format("%.2f", temperature) + Unicode.CELSIUS);
                }
            }

            this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

}
