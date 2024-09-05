package su.terrafirmagreg.modules.metal.client.gui;

import su.terrafirmagreg.api.base.gui.component.button.IButtonTooltip;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.network.CSPacketGuiButton;
import su.terrafirmagreg.modules.metal.ModuleMetal;
import su.terrafirmagreg.modules.metal.objects.recipe.anvil.IAnvilRecipe;
import su.terrafirmagreg.modules.metal.objects.tile.TileMetalAnvil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;


import net.dries007.tfc.client.button.GuiButtonAnvilStep;
import net.dries007.tfc.client.gui.GuiContainerTE;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.forge.ForgeStep;
import net.dries007.tfc.util.forge.ForgeSteps;

import java.io.IOException;

public class GuiMetalAnvil extends GuiContainerTE<TileMetalAnvil> {

  public static final ResourceLocation ANVIL_BACKGROUND = ModUtils.resource(
      "textures/gui/anvil.png");
  public static final int BUTTON_ID_STEP_MIN = 0;
  public static final int BUTTON_ID_STEP_MAX = 7;
  public static final int BUTTON_ID_PLAN = 8;

  public GuiMetalAnvil(Container container, InventoryPlayer playerInv, TileMetalAnvil tile) {
    super(container, playerInv, tile, ANVIL_BACKGROUND);
    ySize = 192;
  }

  @Override
  public void initGui() {
    super.initGui();

    int buttonID = -1;
    for (ForgeStep step : ForgeStep.values()) {
      addButton(new GuiButtonAnvilStep(++buttonID, guiLeft, guiTop, step));
    }
    //		addButton(new GuiButtonAnvilPlan(tile, ++buttonID, guiLeft, guiTop));
  }

  @Override
  protected void renderHoveredToolTip(int mouseX, int mouseY) {
    // Button Tooltips
    for (GuiButton button : buttonList) {
      if (button instanceof IButtonTooltip tooltip && button.isMouseOver()) {
        if (tooltip.hasTooltip()) {
          drawHoveringText(I18n.format(tooltip.getTooltip()), mouseX, mouseY);
        }
      }
    }
    // Rule Tooltips
    IAnvilRecipe recipe = tile.getRecipe();
    if (recipe != null) {
      int x = guiLeft + 61, y = guiTop + 7;
      for (int i = 0; i < recipe.getRules().length; i++) {
        ForgeRule rule = recipe.getRules()[i];
        if (rule != null && mouseX >= x && mouseY >= y && mouseX < x + 18 && mouseY < y + 22) {
          // Hovering over rule area
          drawHoveringText(I18n.format(Helpers.getEnumName(rule)), mouseX, mouseY);
          break;
        }
        x += 19;
      }
    }
    super.renderHoveredToolTip(mouseX, mouseY);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

    if (Loader.isModLoaded("jei")) {
      drawTexturedModalRect(guiLeft + 26, guiTop + 24, 0, 192, 9, 14);
    }

    // Draw the progress indicators
    int progress = tile.getWorkingProgress();
    drawTexturedModalRect(guiLeft + 13 + progress, guiTop + 100, 176, 0, 5, 5);

    int target = tile.getWorkingTarget();
    drawTexturedModalRect(guiLeft + 13 + target, guiTop + 94, 181, 0, 5, 5);

    // Draw rule icons
    IAnvilRecipe recipe = tile.getRecipe();
    ForgeSteps steps = tile.getSteps();
    if (recipe != null) {
      for (int i = 0; i < recipe.getRules().length; i++) {
        ForgeRule rule = recipe.getRules()[i];
        if (rule != null) {
          int xOffset = i * 19;
          // The rule icon
          drawScaledCustomSizeModalRect(guiLeft + 64 + xOffset, guiTop + 10, rule.getU(),
              rule.getV(), 32, 32, 10, 10, 256, 256);

          // The overlay
          if (rule.matches(steps)) {
            // GREEN
            GlStateManager.color(0f, 0.6f, 0.2f);
          } else {
            // RED
            GlStateManager.color(1f, 0.4f, 0);
          }
          drawTexturedModalRect(guiLeft + 59 + xOffset, guiTop + 7, 198, rule.getW(), 20, 22);
          GlStateManager.color(1f, 1f, 1f);
        }
      }
    }

    // Draw step icons
    for (int i = 0; i < 3; i++) {
      ForgeStep step = steps.getStep(i);
      if (step != null) {
        // Reverses the placement of the steps to line up better with the rules
        int xOffset = (2 - i) * 19;
        drawScaledCustomSizeModalRect(guiLeft + 64 + xOffset, guiTop + 31, step.getU(), step.getV(),
            32, 32, 10, 10, 256, 256);
      }
    }

  }

  @Override
  protected void actionPerformed(GuiButton button) throws IOException {
    ModuleMetal.getPacketService().sendToServer(new CSPacketGuiButton(button.id));
    super.actionPerformed(button);
  }
}
