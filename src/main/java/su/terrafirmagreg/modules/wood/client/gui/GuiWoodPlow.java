package su.terrafirmagreg.modules.wood.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import su.terrafirmagreg.api.gui.GuiContainerBase;
import su.terrafirmagreg.api.util.ModUtils;

public class GuiWoodPlow extends GuiContainerBase {

	private static final ResourceLocation PLOW_GUI_TEXTURES = ModUtils.getID("textures/gui/container/plow.png");
	private final IInventory plowInventory;

	public GuiWoodPlow(Container container, InventoryPlayer playerInv, IInventory plowInventory) {
		super(container, playerInv, PLOW_GUI_TEXTURES);
		this.plowInventory = plowInventory;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = this.plowInventory.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(PLOW_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

}
