package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.base.gui.BaseGuiContainerTile;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.Unicode;
import su.terrafirmagreg.data.lib.MCColor;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.device.network.CSPacketFreezeDryer;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.lwjgl.opengl.GL11;
import pieman.caffeineaddon.ModConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiFreezeDryer extends BaseGuiContainerTile<TileFreezeDryer> {

  public static final ResourceLocation BACKGROUND = ModUtils.resource(
    "textures/gui/container/freeze_dryer.png");
  private final String translationKey;
  private final InventoryPlayer playerInventory;

  public GuiFreezeDryer(Container container, InventoryPlayer playerInv, TileFreezeDryer tile,
                        IBlockState state) {
    super(container, playerInv, tile, BACKGROUND);

    this.playerInventory = playerInv;
    this.translationKey = state.getBlock().getTranslationKey();
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    String name = I18n.format(translationKey + ".name");
    fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6,
                            MCColor.BLACK.getRGB());
    this.fontRenderer.drawString(this.playerInventory.getDisplayName()
                                                     .getUnformattedText(), 8, this.ySize - 92, MCColor.BLACK.getRGB());

    if (tile.getSeal()) {
      IItemHandler handler = (this.tile).getCapability(
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      if (handler != null) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        for (int slotId = 0; slotId < handler.getSlots() - 1; ++slotId) {
          this.drawSlotOverlay(this.inventorySlots.getSlot(slotId));
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
      }
    }

    List<String> infoText = new ArrayList<String>();
    if (mouseX >= guiLeft + 5 && mouseX <= guiLeft + 15 && mouseY >= guiTop + 5
        && mouseY <= guiTop + 15) {

      if (ModConfig.isDebugging) {
        infoText.add("---Debug---");
        infoText.add(
          "Pump Temperature: " + String.format("%.2f", tile.getTemperature()) + Unicode.CELSIUS);
        infoText.add("Internal Pressure: " + String.format("%.2f", tile.getPressure()));
        infoText.add("External Temperature: " + String.format("%.2f", tile.getLocalTemperature())
                     + Unicode.CELSIUS);
        infoText.add("External Pressure: " + String.format("%.2f", tile.getLocalPressure()));
        infoText.add("Temperature Delta: " + String.format("%.2f",
                                                           (tile.getTemperature() - tile.getLocalTemperature())) + Unicode.CELSIUS);
        infoText.add("Pressure Delta: " + String.format("%.2f",
                                                        (tile.getPressure() - tile.getLocalPressure())));
        infoText.add("Coolant: " + String.format("%.2f", tile.getCoolant()));
        infoText.add("Progress: " + (tile.getSealedFor() / ModConfig.sealedDuration) * 100 + "%");
        infoText.add("Sealed: " + ((tile.getSeal()) ? "Yes" : "No"));
        infoText.add("Pumping: " + ((tile.getPump()) ? "Yes" : "No"));
        infoText.add("Overheating: " + ((tile.overheating) ? "Yes" : "No"));
        infoText.add("Overheating Ticks: " + tile.overheatTick);
        infoText.add("Power Level: " + tile.getPower());
        infoText.add(
          "Max Pressure: " + (ModConfig.seaLevelPressure + ModConfig.pressureChange * (256
                                                                                       - ModConfig.seaLevel)));
        infoText.add("------------");
        infoText.add("-- Config --");
        infoText.add("------------");
        infoText.add("Sea Level: " + ModConfig.seaLevel);
        infoText.add("Sea Pressure: " + ModConfig.seaLevelPressure);
        infoText.add("Work Per Power: " + ModConfig.workPerPower);
        infoText.add("Heat Per Power: " + ModConfig.heatPerPower);
        infoText.add("Change in Pressure: " + ModConfig.pressureChange);
        infoText.add("Dissipation: " + ModConfig.temperatureDissipation);
        infoText.add("Temperature Max: " + ModConfig.maxTemp + Unicode.CELSIUS);
        infoText.add("Coolant Max: " + ModConfig.coolantMax);
        infoText.add("Initialized: " + tile.initialized);
      } else {
        infoText.add("---Info---");
        infoText.add(
          "Temperature: " + String.format("%.2f", tile.getTemperature()) + Unicode.CELSIUS);
        infoText.add("Pressure: " + String.format("%.2f", tile.getPressure()));
        infoText.add("Coolant: " + String.format("%.2f", tile.getCoolant()));
        infoText.add("Progress: " + (tile.getSealedFor() / ModConfig.sealedDuration) * 100 + "%");
        infoText.add("----------");
        infoText.add("Sealed: " + ((tile.getSeal()) ? "Yes" : "No"));
        infoText.add("Pumping: " + ((tile.getPump()) ? "Yes" : "No"));
        infoText.add("Power Level: " + tile.getPower());
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Freeze Drier Seal
    if (mouseX >= guiLeft + 62 && mouseX <= guiLeft + 78 && mouseY >= guiTop + 17
        && mouseY <= guiTop + 33) {

      if (tile.getSeal()) {
        infoText.add("Unseal Chamber");
      } else {
        infoText.add("Seal Chamber");
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Freeze Drier Snow Flake
    if ((mouseX >= guiLeft + 73 && mouseX <= guiLeft + 103 && mouseY >= guiTop + 36
         && mouseY <= guiTop + 50)
        || (mouseX >= guiLeft + 80 && mouseX <= guiLeft + 96 && mouseY >= guiTop + 28
            && mouseY <= guiTop + 58)) {

      if (ModConfig.isDebugging) {
        infoText.add("Progress: " + String.format("%d", tile.getSealedFor()) + "%");
        infoText.add(
          "Ticks Vacuum Sealed: " + String.format("%.2f", tile.getSealedTicks()) + " ticks");
      } else {
        infoText.add("Progress: " + String.format("%d", tile.getSealedFor()) + "%");
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Seal
    if (mouseX >= guiLeft + 125 && mouseX <= guiLeft + 129 && mouseY >= guiTop + 18
        && mouseY <= guiTop + 69) {

      if (ModConfig.isDebugging) {
        infoText.add("Vacuum: " + String.format("%.5f", tile.getPressure()));
        infoText.add("External Pressure: " + String.format("%.5f", tile.getLocalPressure()));
      } else {
        infoText.add("Vacuum: " + String.format("%.2f", tile.getPressure()));
        infoText.add("External Pressure: " + String.format("%.2f", tile.getLocalPressure()));
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Heat
    if (mouseX >= guiLeft + 133 && mouseX <= guiLeft + 137 && mouseY >= guiTop + 18
        && mouseY <= guiTop + 69) {

      if (ModConfig.isDebugging) {
        infoText.add("Heat: " + String.format("%.5f", tile.getTemperature()) + Unicode.CELSIUS);
        infoText.add("External Temperature: " + String.format("%.5f", tile.getLocalTemperature())
                     + Unicode.CELSIUS);
      } else {
        infoText.add("Heat: " + String.format("%.2f", tile.getTemperature()) + Unicode.CELSIUS);
        infoText.add("External Temperature: " + String.format("%.2f", tile.getLocalTemperature())
                     + Unicode.CELSIUS);
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Pump Power
    if (mouseX >= guiLeft + 141 && mouseX <= guiLeft + 158 && mouseY >= guiTop + 53
        && mouseY <= guiTop + 70) {

      if (tile.getPump()) {
        infoText.add("Stop Pump");
      } else {
        infoText.add("Start Pump");
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
      return;
    }

    // Coolant
    if (mouseX >= guiLeft + 163 && mouseX <= guiLeft + 167 && mouseY >= guiTop + 18
        && mouseY <= guiTop + 69) {

      if (ModConfig.isDebugging) {
        infoText.add("Coolant: " + String.format("%.5f", tile.getCoolant()));
      } else {
        infoText.add(
          "Coolant: " + String.format("%.1f", (tile.getCoolant() / ModConfig.coolantMax)) + "%");
      }

      this.drawHoveringText(infoText, mouseX - guiLeft, mouseY - guiTop);
    }

  }

  @Override
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    super.mouseClicked(mouseX, mouseY, mouseButton);

    if (mouseX >= guiLeft + 61 && mouseX <= guiLeft + 79 && mouseY >= guiTop + 16
        && mouseY <= guiTop + 34) {
      if (!tile.getSeal()) {
        ModuleDevice.getPacketService().sendToServer(
          new CSPacketFreezeDryer(tile.getPos().getX(), tile.getPos().getY(), tile.getPos()
                                                                                  .getZ(), 0, true));
        //TE.seal();
      } else {
        ModuleDevice.getPacketService().sendToServer(
          new CSPacketFreezeDryer(tile.getPos().getX(), tile.getPos().getY(), tile.getPos()
                                                                                  .getZ(), 0, false));
        //TE.unseal();
      }
    } else if (mouseX >= guiLeft + 141 && mouseX <= guiLeft + 159 && mouseY >= guiTop + 52
               && mouseY <= guiTop + 70) {
      if ((tile.getSeal() && tile.getPower() > 0) || tile.getPump()) {
        if (!tile.getPump()) {
          ModuleDevice.getPacketService()
                      .sendToServer(new CSPacketFreezeDryer(tile.getPos().getX(), tile.getPos()
                                                                                      .getY(), tile.getPos()
                                                                                                   .getZ(), 1, true));
          //TE.startPump();
        } else {
          ModuleDevice.getPacketService()
                      .sendToServer(new CSPacketFreezeDryer(tile.getPos().getX(), tile.getPos()
                                                                                      .getY(), tile.getPos()
                                                                                                   .getZ(), 1, false));
          //TE.stopPump();
        }
      }
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    this.mc.getTextureManager().bindTexture(BACKGROUND);
    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

    {
      int k = (int) this.getPressureLeftScaled(51);
      this.drawTexturedModalRect(this.guiLeft + 125, this.guiTop + 17 + 52 - k, 180, 52 - k - 1, 5,
                                 k + 1);
    }

    {
      int k = (int) this.getHeatLeftScaled(51);
      this.drawTexturedModalRect(this.guiLeft + 133, this.guiTop + 17 + 52 - k, 188, 52 - k - 1, 5,
                                 k + 1);
    }

    {
      int k = (int) this.getCoolentLeftScaled(51);
      this.drawTexturedModalRect(this.guiLeft + 163, this.guiTop + 17 + 52 - k, 196, 52 - k - 1, 5,
                                 k + 1);
    }

    {
      int k = (int) this.getLocalPressureScaled(51);
      this.drawTexturedModalRect(this.guiLeft + 126, this.guiTop + 17 + 52 - k, 204, 52 - k - 1, 3,
                                 1);
    }

    {
      int k = (int) this.getLocalTempatureScaled(51);
      this.drawTexturedModalRect(this.guiLeft + 134, this.guiTop + 17 + 52 - k, 204, 52 - k - 1, 3,
                                 1);
    }

    {
      int k = (int) this.getProgressScaled(27);
      this.drawTexturedModalRect(this.guiLeft + 74, this.guiTop + 28 + 28 - k, 180, 84 - k - 1, 28,
                                 k + 1);
    }

    if (tile.getSeal()) {
      this.drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 16, 211, 0, 18, 18);
    } else {
      this.drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 16, 211, 22, 18, 18);
    }

    if (tile.getPump()) {
      this.drawTexturedModalRect(this.guiLeft + 141, this.guiTop + 52, 211, 0, 18, 18);
    } else {
      this.drawTexturedModalRect(this.guiLeft + 141, this.guiTop + 52, 211, 22, 18, 18);
    }

  }

  private float getPressureLeftScaled(int pixels) {
    return (float) tile.getPressure() * pixels / (ModConfig.seaLevelPressure
                                                  + ModConfig.pressureChange * (256 - ModConfig.seaLevel));
  }

  private float getHeatLeftScaled(int pixels) {
    return Math.round(tile.getTemperature()) * pixels / ModConfig.maxTemp;
  }

  private float getCoolentLeftScaled(int pixels) {
    return (float) tile.getCoolant() * pixels / ModConfig.coolantMax;
  }

  private float getLocalPressureScaled(int pixels) {
    return (float) tile.getLocalPressure() * pixels / (ModConfig.seaLevelPressure
                                                       + ModConfig.pressureChange * (256 - ModConfig.seaLevel));
  }

  private float getLocalTempatureScaled(int pixels) {
    return Math.round(tile.getLocalTemperature()) * pixels / ModConfig.maxTemp;
  }

  private float getProgressScaled(int pixels) {
    return tile.getSealedTicks() * pixels / ModConfig.sealedDuration;
  }

}
