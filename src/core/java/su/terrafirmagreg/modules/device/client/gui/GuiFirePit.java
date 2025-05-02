package su.terrafirmagreg.modules.device.client.gui;

import su.terrafirmagreg.api.base.client.gui.inventory.spi.BaseGuiContainerTile;
import su.terrafirmagreg.api.data.enums.EnumFirePitAttachment;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;
import su.terrafirmagreg.modules.device.object.tile.TileFirePit;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import static su.terrafirmagreg.api.data.Properties.EnumProp.FIRE_PIT_ATTACHMENT;

public class GuiFirePit extends BaseGuiContainerTile<TileFirePit> {

  private static final ResourceLocation FIRE_PIT_BACKGROUND = ModUtils.resource("textures/gui/container/fire_pit.png");
  private static final ResourceLocation FIRE_PIT_COOKING_POT_BACKGROUND = ModUtils.resource("textures/gui/container/fire_pit_cooking_pot.png");
  private static final ResourceLocation FIRE_PIT_GRILL_BACKGROUND = ModUtils.resource("textures/gui/container/fire_pit_grill.png");

  private final EnumFirePitAttachment attachment;

  public GuiFirePit(Container container, InventoryPlayer playerInv, TileFirePit tile) {
    super(container, playerInv, tile, FIRE_PIT_BACKGROUND);

    attachment = tile.getWorld().getBlockState(tile.getPos()).getValue(FIRE_PIT_ATTACHMENT);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    drawBackground();

    // Draw the fire / burn time indicator
    int temperature = (int) (51 * tile.getField(TileFirePit.FIELD_TEMPERATURE)
                             / Heat.maxVisibleTemperature());
    if (temperature > 0) {
      if (temperature > 51) {
        temperature = 51;
      }
      drawTexturedModalRect(guiLeft + 30, guiTop + 66 - temperature, 176, 0, 15, 5);
    }

    if (attachment == EnumFirePitAttachment.COOKING_POT) {
      // Draw soup overlays + text
      TileFirePit.CookingPotStage stage = tile.getCookingPotStage();
      String caption;
      if (stage == TileFirePit.CookingPotStage.WAITING || stage == TileFirePit.CookingPotStage.BOILING) {
        drawTexturedModalRect(guiLeft + 58, guiTop + 52, 191, 0, 24, 4);
        if (stage == TileFirePit.CookingPotStage.WAITING) {
          caption = I18n.format("tfc.tooltip.firepit_cooking_pot_waiting");
        } else // boiling
        {
          caption = I18n.format("tfc.tooltip.firepit_cooking_pot_boiling");
        }
      } else if (stage == TileFirePit.CookingPotStage.FINISHED) {
        drawTexturedModalRect(guiLeft + 58, guiTop + 52, 191, 4, 24, 4);
        caption = I18n.format("tfc.tooltip.firepit_cooking_pot_servings", tile.getSoupServings());
      } else {
        caption = I18n.format("tfc.tooltip.firepit_cooking_pot_empty");
      }

      fontRenderer.drawString(caption, guiLeft + 130 - fontRenderer.getStringWidth(caption) / 2,
        guiTop + 52, 0x404040);
    }
  }

  protected void drawBackground() {
    GlStateManager.color(1, 1, 1, 1);
    switch (attachment) {
      case NONE:
        mc.getTextureManager().bindTexture(FIRE_PIT_BACKGROUND);
        break;
      case COOKING_POT:
        mc.getTextureManager().bindTexture(FIRE_PIT_COOKING_POT_BACKGROUND);
        break;
      case GRILL:
        mc.getTextureManager().bindTexture(FIRE_PIT_GRILL_BACKGROUND);
        break;
    }
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    if (attachment == EnumFirePitAttachment.COOKING_POT) {
      TileFirePit.CookingPotStage stage = tile.getCookingPotStage();
      if (stage == TileFirePit.CookingPotStage.BOILING || stage == TileFirePit.CookingPotStage.FINISHED) {
        // slots are disabled while boiling
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        for (int i = TileFirePit.SLOT_EXTRA_INPUT_START; i <= TileFirePit.SLOT_EXTRA_INPUT_END;
             i++) {
          drawSlotOverlay(inventorySlots.getSlot(i - 3)); // index of extra inputs
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
      }
    }
  }
}
