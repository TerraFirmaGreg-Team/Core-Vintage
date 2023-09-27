package net.dries007.tfc.module.wood.client.button;

import net.dries007.tfc.Tags;
import net.dries007.tfc.client.button.GuiButtonTFC;
import net.dries007.tfc.client.button.IButtonTooltip;
import net.dries007.tfc.module.wood.common.tiles.TEWoodBarrel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nonnull;

import static net.dries007.tfc.module.wood.client.gui.GuiWoodBarrel.BARREL_BACKGROUND;


public class GuiButtonBarrelSeal extends GuiButtonTFC implements IButtonTooltip {
    private final TEWoodBarrel tile;

    public GuiButtonBarrelSeal(TEWoodBarrel tile, int buttonId, int guiTop, int guiLeft) {
        super(buttonId, guiLeft + 123, guiTop + 35, 20, 20, "");
        this.tile = tile;
    }

    @Override
    public String getTooltip() {
        return Tags.MOD_ID + ".tooltip." + (tile.isSealed() ? "barrel_unseal" : "barrel_seal");
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(BARREL_BACKGROUND);
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
