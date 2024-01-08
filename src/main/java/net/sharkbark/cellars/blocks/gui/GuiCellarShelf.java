package net.sharkbark.cellars.blocks.gui;

import net.dries007.tfc.client.gui.GuiContainerTE;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.sharkbark.cellars.blocks.tileentity.TECellarShelf;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.Constants.MODID_CELLARS;

public class GuiCellarShelf extends GuiContainerTE<TECellarShelf> {

	public static final ResourceLocation CELLAR_SHELF_BACKGROUND = new ResourceLocation(MODID_CELLARS, "textures/gui/cellar_shelf.png");
	private static TECellarShelf TE;
	private final String translationKey;
	private final InventoryPlayer playerInventory;

	public GuiCellarShelf(Container container, InventoryPlayer playerInv, TECellarShelf tile, String translationKey) {
		super(container, playerInv, tile, CELLAR_SHELF_BACKGROUND);
		this.playerInventory = playerInv;
		this.TE = tile;
		this.translationKey = translationKey;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(translationKey + ".name");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 00000000);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName()
		                                                 .getUnformattedText(), 8, this.ySize - 92, 00000000);

		if (mouseX >= guiLeft + 5 && mouseX <= guiLeft + 15 && mouseY >= guiTop + 5 && mouseY <= guiTop + 15) {
			List<String> infoText = new ArrayList<String>();
			float temperature = TE.getTemperature();

			if (temperature <= -1000) {
				infoText.add("[!] The shelf is not inside a cellar");
			} else {
				if (temperature < 0) {
					infoText.add("Temperature: below zero");
				} else {
					infoText.add("Temperature: " + String.format("%.2f", temperature) + "\u2103");
				}
			}

			this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(CELLAR_SHELF_BACKGROUND);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

}
