package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.base.client.gui.button.api.IButtonTooltip;
import su.terrafirmagreg.api.base.client.gui.inventory.spi.BaseGuiContainerTile;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.network.CSPacketGuiButton;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.device.client.button.GuiButtonPowderkegSeal;
import su.terrafirmagreg.modules.device.object.tile.TilePowderKeg;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

public class GuiPowderkeg extends BaseGuiContainerTile<TilePowderKeg> {

  public static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/powderkeg.png");

  public GuiPowderkeg(Container container, InventoryPlayer playerInv, TilePowderKeg tile) {
    super(container, playerInv, tile, BACKGROUND);

  }

  @Override
  public void initGui() {
    super.initGui();
    addButton(new GuiButtonPowderkegSeal(tile, 0, guiTop, guiLeft));
  }

  @Override
  protected void renderHoveredToolTip(int mouseX, int mouseY) {
    super.renderHoveredToolTip(mouseX, mouseY);

    // Button Tooltips
    for (GuiButton button : buttonList) {
      if (button instanceof IButtonTooltip tooltip && button.isMouseOver()) {
        if (tooltip.hasTooltip()) {
          drawHoveringText(I18n.format(tooltip.getTooltip()), mouseX, mouseY);
        }
      }
    }
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    if (tile.isSealed()) {
      // Draw over the input items, making them look unavailable
      IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
        null);
      if (handler != null) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        for (int slotId = 0; slotId < handler.getSlots(); slotId++) {
          drawSlotOverlay(inventorySlots.getSlot(slotId));
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
      }
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
  }

  @Override
  protected void actionPerformed(@NotNull GuiButton button) throws IOException {
    if (button instanceof GuiButtonPowderkegSeal) {
      ModuleDevice.NETWORK.sendToServer(new CSPacketGuiButton(button.id));
    }
    super.actionPerformed(button);
  }
}
