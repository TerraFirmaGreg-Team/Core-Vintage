package su.terrafirmagreg.modules.wood.client.button;

import su.terrafirmagreg.api.spi.button.GuiButtonBase;
import su.terrafirmagreg.api.spi.button.IButtonTooltip;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.client.gui.GuiWoodBarrel;
import su.terrafirmagreg.modules.wood.objects.tiles.TEWoodBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;


import org.jetbrains.annotations.NotNull;

public class GuiButtonBarrelSeal extends GuiButtonBase implements IButtonTooltip {

    private final TEWoodBarrel tile;

    public GuiButtonBarrelSeal(TEWoodBarrel tile, int buttonId, int guiTop, int guiLeft) {
        super(buttonId, guiLeft + 123, guiTop + 35, 20, 20, "");
        this.tile = tile;
    }

    @Override
    public String getTooltip() {
        return ModUtils.name("tooltip.") + (tile.isSealed() ? "barrel_unseal" : "barrel_seal");
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }

    @Override
    public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(GuiWoodBarrel.BARREL_BACKGROUND);
            hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            if (tile.isSealed()) {
                Gui.drawModalRectWithCustomSizedTexture(x, y, 236, 0, 20, 20, 256, 256);
            } else {
                Gui.drawModalRectWithCustomSizedTexture(x, y, 236, 20, 20, 20, 256, 256);
            }
            mouseDragged(mc, mouseX, mouseY);
        }
    }
}
