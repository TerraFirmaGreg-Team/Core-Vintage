package net.dries007.tfc.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.te.TEAlloyCalculator;
import net.dries007.tfc.util.Alloy;

import java.util.Map;

import static net.dries007.tfc.TerraFirmaCraft.MODID_TFC;

@SideOnly(Side.CLIENT)
public class GuiAlloyCalculator extends GuiContainerTE<TEAlloyCalculator> {

  public static final ResourceLocation BG_TEX = new ResourceLocation(MODID_TFC, "textures/gui/alloy_calc_gui.png");


  public GuiAlloyCalculator(Container container, InventoryPlayer playerInv, TEAlloyCalculator tile) {
    super(container, playerInv, tile, BG_TEX);
    this.ySize = 186;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    this.drawDefaultBackground();
    this.mc.getTextureManager().bindTexture(BG_TEX);
    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    //drawRect(this.guiLeft, this.guiTop, this.guiLeft + this.xSize, this.guiTop + this.ySize, 0xFFC6C6C6);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    this.fontRenderer.drawString("Alloy Calculator", 8, 6, 0x404040);
    Alloy alloy = tile.getAlloy();
    if (alloy != null) {
      Metal result = alloy.getResult();
      float scale = 0.85f;
      GlStateManager.pushMatrix();
      GlStateManager.scale(scale, scale, scale);
      this.fontRenderer.drawString(
        I18n.format(result.getTranslationKey()) + " - " + alloy.getAmount() + " units",
        8 / scale, 18 / scale, 0x404040, false
      );
      GlStateManager.popMatrix();
      scale = 0.7f;
      GlStateManager.pushMatrix();
      GlStateManager.scale(scale, scale, scale);
      // TODO Cache this
      int i = 0;
      for (Map.Entry<Metal, Double> entry : alloy.getMetals().entrySet()) {
        Metal metal = entry.getKey();
        double amount = entry.getValue();
        this.fontRenderer.drawString(
          String.format("%5.2f%%", (amount / alloy.getAmount()) * 100) + " - " + I18n.format(metal.getTranslationKey()) + " " + (int) amount,
          70 / scale,
          (20 + this.fontRenderer.FONT_HEIGHT + i * (this.fontRenderer.FONT_HEIGHT - 1)) / scale, 0x404040, false
        );
        i++;
      }
      GlStateManager.popMatrix();
    }
    this.renderHoveredToolTip(mouseX - this.guiLeft, mouseY - this.guiTop);
  }
}
