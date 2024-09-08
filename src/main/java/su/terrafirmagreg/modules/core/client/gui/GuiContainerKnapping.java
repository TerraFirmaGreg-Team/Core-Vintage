package su.terrafirmagreg.modules.core.client.gui;

import su.terrafirmagreg.api.base.gui.BaseGuiContainer;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.client.button.GuiButtonKnapping;
import su.terrafirmagreg.modules.core.object.container.ContainerBaseKnapping;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;


import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.client.TFCGuiHandler;

import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;

public abstract class GuiContainerKnapping extends BaseGuiContainer {

  private static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/container/knapping.png");

  protected final ResourceLocation buttonTexture;
  protected final KnappingType type;

  public GuiContainerKnapping(Container container, InventoryPlayer inventoryPlayer, KnappingType type, ResourceLocation buttonTexture) {
    super(container, inventoryPlayer, BACKGROUND);
    this.buttonTexture = buttonTexture;
    this.type = type;
    ySize = 184; // Bigger than normal gui
  }

  @Override
  public void initGui() {
    super.initGui();
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        int bx = (width - xSize) / 2 + 12 + 16 * x;
        int by = (height - ySize) / 2 + 12 + 16 * y;
        addButton(new GuiButtonKnapping(x + 5 * y, bx, by, 16, 16, buttonTexture));
      }
    }
    // JEI reloads this after it's recipe gui is closed
    if (inventorySlots instanceof ContainerBaseKnapping containerBaseKnapping) {
      containerBaseKnapping.requiresReset = true;
    }
  }

  @Override
  protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton,
          long timeSinceLastClick) {
    if (clickedMouseButton == 0) {
      for (GuiButton button : this.buttonList) {
        if (button instanceof GuiButtonKnapping && button.mousePressed(mc, mouseX, mouseY)) {
          ActionPerformedEvent.Pre event = new ActionPerformedEvent.Pre(this, button, buttonList);
          if (MinecraftForge.EVENT_BUS.post(event)) {
            break;
          } else if (selectedButton == event.getButton()) {
            continue;
          }

          selectedButton = event.getButton();
          event.getButton().mousePressed(mc, mouseX, mouseY);
          actionPerformed(event.getButton());

          MinecraftForge.EVENT_BUS.post(
                  new ActionPerformedEvent.Post(this, event.getButton(), buttonList));
        }
      }
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    // Check if the container has been updated
    if (inventorySlots instanceof ContainerBaseKnapping containerBaseKnapping && containerBaseKnapping.requiresReset) {
      for (GuiButton button : buttonList) {
        if (button instanceof GuiButtonKnapping) {
          button.visible = containerBaseKnapping.getSlotState(button.id);
        }
      }
      containerBaseKnapping.requiresReset = false;
    }
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    if (type == KnappingTypes.CLAY || type == KnappingTypes.FIRE_CLAY) {
      GlStateManager.color(1, 1, 1, 1);
      mc.getTextureManager()
              .bindTexture(type == KnappingTypes.CLAY
                      ? TFCGuiHandler.CLAY_DISABLED_TEXTURE
                      : TFCGuiHandler.FIRE_CLAY_DISABLED_TEXTURE);
      for (GuiButton button : buttonList) {
        if (!button.visible) {
          Gui.drawModalRectWithCustomSizedTexture(button.x, button.y, 0, 0, 16, 16, 16, 16);
        }
      }
    }
  }

  @Override
  protected void actionPerformed(@NotNull GuiButton button) {
    if (button instanceof GuiButtonKnapping guiButtonKnapping) {
      guiButtonKnapping.onClick();
      button.playPressSound(mc.getSoundHandler());
      // Set the client-side matrix
      if (inventorySlots instanceof ContainerBaseKnapping containerBaseKnapping) {
        containerBaseKnapping.setSlotState(button.id, false);
      }
    }
  }
}
