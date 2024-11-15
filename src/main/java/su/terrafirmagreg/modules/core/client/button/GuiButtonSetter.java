package su.terrafirmagreg.modules.core.client.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import org.jetbrains.annotations.NotNull;

import tfctech.client.TechGuiHandler;

public class GuiButtonSetter extends GuiButton {

  private final boolean isPlus;

  public GuiButtonSetter(int buttonId, int x, int y, boolean plus) {
    super(buttonId, x, y, 14, 14, "");
    this.isPlus = plus;
  }

  @Override
  public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
    if (this.visible) {
      mc.getTextureManager().bindTexture(TechGuiHandler.GUI_ELEMENTS);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
                     && mouseY < this.y + this.height;
      int i = this.getHoverState(this.hovered);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                                          GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                                          GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
                               GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      this.drawTexturedModalRect(this.x, this.y, isPlus ? 14 : 0, 60 + i * 14, this.width,
                                 this.height);
      this.mouseDragged(mc, mouseX, mouseY);
    }
  }
}
