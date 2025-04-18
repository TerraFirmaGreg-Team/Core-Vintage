package su.terrafirmagreg.api.base.client.gui.inventory.spi;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public abstract class BaseGuiContainer extends GuiContainer {

  protected final ResourceLocation background;
  protected final InventoryPlayer playerInv;

  public BaseGuiContainer(Container container, InventoryPlayer playerInv, ResourceLocation background) {
    super(container);
    this.playerInv = playerInv;
    this.background = background;
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.drawDefaultBackground();
    super.drawScreen(mouseX, mouseY, partialTicks);
    this.renderHoveredToolTip(mouseX, mouseY);
  }

  @Override
  protected void drawItemStack(ItemStack stack, int x, int y, @NotNull String altText) {
    this.zLevel = 200.0F;
    this.itemRender.zLevel = 200.0F;
    FontRenderer font = stack.getItem().getFontRenderer(stack);
    if (font == null) {
      font = fontRenderer;
    }
    this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
    this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
    this.zLevel = 0.0F;
    this.itemRender.zLevel = 0.0F;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    drawSimpleBackground();
  }

  protected final void drawSimpleBackground() {
    GlStateManager.color(1, 1, 1, 1);
//    this.drawDefaultBackground();
    mc.getTextureManager().bindTexture(background);
    this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
  }

  protected void drawSlotOverlay(Slot slot) {
    int xPos = slot.xPos - 1;
    int yPos = slot.yPos - 1;

    this.drawGradientRect(xPos, yPos, xPos + 18, yPos + 18, 0x75FFFFFF, 0x75FFFFFF);
  }
}
