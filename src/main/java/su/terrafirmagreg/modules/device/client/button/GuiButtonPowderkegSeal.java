package su.terrafirmagreg.modules.device.client.button;

import su.terrafirmagreg.api.spi.button.GuiButtonBase;
import su.terrafirmagreg.api.spi.button.IButtonTooltip;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.objects.tiles.TEPowderKeg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.device.client.gui.GuiPowderkeg.BACKGROUND;

public class GuiButtonPowderkegSeal extends GuiButtonBase implements IButtonTooltip {

    private final TEPowderKeg tile;

    public GuiButtonPowderkegSeal(TEPowderKeg tile, int buttonId, int guiTop, int guiLeft) {
        super(buttonId, guiLeft + 123, guiTop + 35, 20, 20, "");
        this.tile = tile;
    }

    @Override
    public String getTooltip() {
        return ModUtils.idLocalized("tooltip.") + (tile.isSealed() ? "powderkeg_unseal" : "powderkeg_seal");
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }

    @Override
    public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(BACKGROUND);
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
