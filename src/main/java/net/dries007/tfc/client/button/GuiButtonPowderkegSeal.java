package net.dries007.tfc.client.button;

import net.dries007.tfc.objects.te.TEPowderKeg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import org.jetbrains.annotations.NotNull;

import static net.dries007.tfc.client.gui.GuiPowderkeg.POWDERKEG_BACKGROUND;
import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;


public class GuiButtonPowderkegSeal extends GuiButtonTFC implements IButtonTooltip {
	private final TEPowderKeg tile;

	public GuiButtonPowderkegSeal(TEPowderKeg tile, int buttonId, int guiTop, int guiLeft) {
		super(buttonId, guiLeft + 123, guiTop + 35, 20, 20, "");
		this.tile = tile;
	}

	@Override
	public String getTooltip() {
		return MODID_TFC + ".tooltip." + (tile.isSealed() ? "powderkeg_unseal" : "powderkeg_seal");
	}

	@Override
	public boolean hasTooltip() {
		return true;
	}

	@Override
	public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			GlStateManager.color(1, 1, 1, 1);
			mc.getTextureManager().bindTexture(POWDERKEG_BACKGROUND);
			hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			if (tile.isSealed()) {
				drawModalRectWithCustomSizedTexture(x, y, 236, 0, 20, 20, 256, 256);
			} else {
				drawModalRectWithCustomSizedTexture(x, y, 236, 20, 20, 20, 256, 256);
			}
			mouseDragged(mc, mouseX, mouseY);
		}
	}
}
