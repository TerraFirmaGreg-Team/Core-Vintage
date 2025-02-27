package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.base.client.gui.inventory.spi.BaseGuiContainerTile;
import su.terrafirmagreg.api.data.Unicode;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.world.classic.WorldTypeTFC;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiFreezeDryer extends BaseGuiContainerTile<TileFreezeDryer> {

  public static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/freeze_dryer.png");

  public GuiFreezeDryer(Container container, InventoryPlayer playerInv, TileFreezeDryer tile) {
    super(container, playerInv, tile, BACKGROUND);

  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    if (tile.isSealed()) {
      IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      if (handler != null) {
        GL11.glDisable(2929);

        for (int slotId = 0; slotId < handler.getSlots() - 1; ++slotId) {
          this.drawSlotOverlay(this.inventorySlots.getSlot(slotId));
        }

        GL11.glEnable(2929);
      }
    }

    List<String> infoText = new ArrayList<>();
    if (mouseX >= guiLeft + 5 && mouseX <= guiLeft + 15 && mouseY >= guiTop + 5 && mouseY <= guiTop + 15) {

      infoText.add("---Info---");
      infoText.add("Temperature: " + String.format("%.2f", tile.getTemperature()) + Unicode.CELSIUS);
      infoText.add("Pressure: " + String.format("%.2f", tile.getPressure()));
      infoText.add("Coolant: " + String.format("%.2f", tile.getCoolant()));
      infoText.add("Progress: " + (tile.getSealedFor() / ConfigDevice.BLOCK.FREEZE_DRYER.sealedDuration) * 100 + "%");
      infoText.add("----------");
      infoText.add("Sealed: " + ((tile.isSealed()) ? "Yes" : "No"));
      infoText.add("Pumping: " + ((tile.isPump()) ? "Yes" : "No"));
      infoText.add("Power Level: " + tile.getPower());

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Freeze Drier Seal
    if (mouseX >= guiLeft + 62 && mouseX <= guiLeft + 78 && mouseY >= guiTop + 17 && mouseY <= guiTop + 33) {

      if (tile.isSealed()) {
        infoText.add("Unseal Chamber");
      } else {
        infoText.add("Seal Chamber");
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Freeze Drier Snow Flake
    if ((mouseX >= guiLeft + 73 && mouseX <= guiLeft + 103 && mouseY >= guiTop + 36 && mouseY <= guiTop + 50) ||
        (mouseX >= guiLeft + 80 && mouseX <= guiLeft + 96 && mouseY >= guiTop + 28 && mouseY <= guiTop + 58)) {

      infoText.add("Progress: " + String.format("%d", tile.getSealedFor()) + "%");

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Seal
    if (mouseX >= guiLeft + 125 && mouseX <= guiLeft + 129 && mouseY >= guiTop + 18 && mouseY <= guiTop + 69) {

      infoText.add("Vacuum: " + String.format("%.2f", tile.getPressure()));
      infoText.add("External Pressure: " + String.format("%.2f", tile.getLocalPressure()));

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Heat
    if (mouseX >= guiLeft + 133 && mouseX <= guiLeft + 137 && mouseY >= guiTop + 18 && mouseY <= guiTop + 69) {

      infoText.add("Heat: " + String.format("%.2f", tile.getTemperature()) + Unicode.CELSIUS);
      infoText.add("External Temperature: " + String.format("%.2f", tile.getLocalTemperature()) + Unicode.CELSIUS);

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Pump Power
    if (mouseX >= guiLeft + 141 && mouseX <= guiLeft + 158 && mouseY >= guiTop + 53 && mouseY <= guiTop + 70) {

      if (tile.isPump()) {
        infoText.add("Stop Pump");
      } else {
        infoText.add("Start Pump");
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Coolant
    if (mouseX >= guiLeft + 163 && mouseX <= guiLeft + 167 && mouseY >= guiTop + 18 && mouseY <= guiTop + 69) {

      infoText.add("Coolant: " + String.format("%.1f", (tile.getCoolant() / ConfigDevice.BLOCK.FREEZE_DRYER.coolantMax)) + "%");

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
    }

  }

  @Override
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    super.mouseClicked(mouseX, mouseY, mouseButton);

    if (mouseX >= guiLeft + 61 && mouseX <= guiLeft + 79 && mouseY >= guiTop + 16 && mouseY <= guiTop + 34) {
      if (!tile.isSealed()) {
        ModuleDevice.NETWORK.sendToServer(new CSPacketFreezeDryer(tile.getPos(), 0, true));
        // tile.seal();
      } else {
        ModuleDevice.NETWORK.sendToServer(new CSPacketFreezeDryer(tile.getPos(), 0, false));
        //tile.unseal();
      }
    } else if (mouseX >= guiLeft + 141 && mouseX <= guiLeft + 159 && mouseY >= guiTop + 52 && mouseY <= guiTop + 70) {
      if ((tile.isSealed() && tile.getPower() > 0) || tile.isPump()) {
        if (!tile.isPump()) {
          ModuleDevice.NETWORK.sendToServer(new CSPacketFreezeDryer(tile.getPos(), 1, true));
          //tile.startPump();
        } else {
          ModuleDevice.NETWORK.sendToServer(new CSPacketFreezeDryer(tile.getPos(), 1, false));
          //tile.stopPump();
        }
      }
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

    {
      int k = (int) (tile.getPressure() * 51 / (ConfigDevice.BLOCK.FREEZE_DRYER.seaLevelPressure + ConfigDevice.BLOCK.FREEZE_DRYER.pressureChange * (256 - WorldTypeTFC.SEALEVEL)));
      this.drawTexturedModalRect(this.guiLeft + 125, this.guiTop + 17 + 52 - k, 180, 52 - k - 1, 5, k + 1);
    }

    {
      int k = Math.round(tile.getTemperature()) * 51 / ConfigDevice.BLOCK.FREEZE_DRYER.maxTemp;
      this.drawTexturedModalRect(this.guiLeft + 133, this.guiTop + 17 + 52 - k, 188, 52 - k - 1, 5, k + 1);
    }

    {
      int k = (int) (tile.getCoolant() * 51 / ConfigDevice.BLOCK.FREEZE_DRYER.coolantMax);
      this.drawTexturedModalRect(this.guiLeft + 163, this.guiTop + 17 + 52 - k, 196, 52 - k - 1, 5, k + 1);
    }

    {
      int k = (int) (tile.getLocalPressure() * 51 / (ConfigDevice.BLOCK.FREEZE_DRYER.seaLevelPressure + ConfigDevice.BLOCK.FREEZE_DRYER.pressureChange * (256 - WorldTypeTFC.SEALEVEL)));
      this.drawTexturedModalRect(this.guiLeft + 126, this.guiTop + 17 + 52 - k, 204, 52 - k - 1, 3, 1);
    }

    {
      int k = Math.round(tile.getLocalTemperature()) * 51 / ConfigDevice.BLOCK.FREEZE_DRYER.maxTemp;
      this.drawTexturedModalRect(this.guiLeft + 134, this.guiTop + 17 + 52 - k, 204, 52 - k - 1, 3, 1);
    }

    {
      int k = (int) (tile.getSealedTicks() * 27 / ConfigDevice.BLOCK.FREEZE_DRYER.sealedDuration);
      this.drawTexturedModalRect(this.guiLeft + 74, this.guiTop + 28 + 28 - k, 180, 84 - k - 1, 28, k + 1);
    }

    if (tile.isSealed()) {
      this.drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 16, 211, 0, 18, 18);
    } else {
      this.drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 16, 211, 22, 18, 18);
    }

    if (tile.isPump()) {
      this.drawTexturedModalRect(this.guiLeft + 141, this.guiTop + 52, 211, 0, 18, 18);
    } else {
      this.drawTexturedModalRect(this.guiLeft + 141, this.guiTop + 52, 211, 22, 18, 18);
    }

  }


}
