package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.base.client.gui.inventory.spi.BaseGuiContainerTile;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.object.tile.TileAlloyCalculator;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.util.Alloy;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class GuiAlloyCalculator extends BaseGuiContainerTile<TileAlloyCalculator> {

  public static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/alloy_calculator.png");

  public GuiAlloyCalculator(Container container, InventoryPlayer playerInv, TileAlloyCalculator tile) {
    super(container, playerInv, tile, BACKGROUND);
    this.ySize = 186;
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    Alloy alloy = this.tile.getAlloy();
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
          (20 + this.fontRenderer.FONT_HEIGHT + i * (this.fontRenderer.FONT_HEIGHT - 1)) / scale,
          0x404040, false
        );
        i++;
      }
      GlStateManager.popMatrix();
    }
    this.renderHoveredToolTip(mouseX - this.guiLeft, mouseY - this.guiTop);
  }

}
