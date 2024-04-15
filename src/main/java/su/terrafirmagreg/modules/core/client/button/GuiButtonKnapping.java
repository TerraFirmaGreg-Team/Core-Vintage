package su.terrafirmagreg.modules.core.client.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.network.PacketGuiButton;

import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class GuiButtonKnapping extends GuiButton {

    private final ResourceLocation texture;

    public GuiButtonKnapping(int id, int x, int y, int width, int height, ResourceLocation texture) {
        super(id, x, y, width, height, "");
        this.texture = texture;
    }

    public void onClick() {
        if (this.enabled) {
            this.visible = false;
            TerraFirmaCraft.getNetwork().sendToServer(new PacketGuiButton(this.id));
        }
    }

    @Override
    public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(texture);

            hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
            mouseDragged(mc, mouseX, mouseY);
        }
    }
}
