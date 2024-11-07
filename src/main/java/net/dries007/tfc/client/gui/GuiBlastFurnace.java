/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.objects.te.TEBlastFurnace;

import java.util.ArrayList;
import java.util.List;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@SideOnly(Side.CLIENT)
public class GuiBlastFurnace extends GuiContainerTE<TEBlastFurnace> {

  private static final ResourceLocation BLAST_FURNACE_BACKGROUND = new ResourceLocation(MOD_ID, "textures/gui/blast_furnace.png");

  List<Float> tempList = new ArrayList<>();
  long lastBurningTicks = 0;
  int scaledWidth;
  int scaledHeight;

  public GuiBlastFurnace(Container container, InventoryPlayer playerInv, TEBlastFurnace tile) {
    super(container, playerInv, tile, BLAST_FURNACE_BACKGROUND);

  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

    readTemperatures();
    lastBurningTicks = tile.getBurnTicksLeft();
    ScaledResolution sr = new ScaledResolution(mc);
    scaledWidth = sr.getScaledWidth();
    scaledHeight = sr.getScaledHeight();

    // Отображение текущей температуры
    int temperature = (int) (51 * tile.getField(TEBlastFurnace.FIELD_TEMPERATURE)
                             / Heat.maxVisibleTemperature());
    if (temperature > 0) {
      if (temperature > 51) {
        temperature = 51;
      }
      drawTexturedModalRect(guiLeft + 8, guiTop + 66 - temperature, 185, 32, 15, 5);
    }

    // Отображение количества руды
    int oreCount = tile.getField(TEBlastFurnace.FIELD_ORE) * 4;
    if (oreCount > 0) {
      drawTexturedModalRect(guiLeft + 40, guiTop + 25, 176, 0, oreCount + 1, 8);
    }

    // Отображение количества топлива
    int fuelCount = tile.getField(TEBlastFurnace.FIELD_FUEL) * 4;
    if (fuelCount > 0) {
      drawTexturedModalRect(guiLeft + 40, guiTop + 43, 176, 0, fuelCount + 1, 8);
    }

    // Отображение количества сплава
    int meltAmount = tile.getField(TEBlastFurnace.FIELD_MELT);
    if (meltAmount > 700) {
      meltAmount = 700;
    }
    meltAmount = (int) (meltAmount / 8.75f);
    if (meltAmount > 0) {
      drawTexturedModalRect(guiLeft + 40, guiTop + 61, 176, 0, meltAmount + 1, 8);
    }

    // Отображение текста с количеством руды, топлива и сплава
    fontRenderer.drawString(I18n.format(MOD_ID + ".tooltip.blast_furnace_ore_amount"),
                            guiLeft + 40, guiTop + 17, 0x000000);
    fontRenderer.drawString(I18n.format(MOD_ID + ".tooltip.blast_furnace_fuel_amount"),
                            guiLeft + 40, guiTop + 35, 0x000000);
    fontRenderer.drawString(I18n.format(MOD_ID + ".tooltip.blast_furnace_melt_amount"),
                            guiLeft + 40, guiTop + 53, 0x000000);

    // Отображение предупреждения, если нет дымохода
    if (tile.getField(TEBlastFurnace.CHIMNEY_LEVELS) < 1) {
      fontRenderer.drawString(I18n.format(MOD_ID + ".tooltip.blast_furnace_invalid_structure"),
                              guiLeft + 40, guiTop + 71, 0xDC2400);
    }

    if (!tempList.isEmpty()) {

      Gui.drawRect(
        scaledWidth / 2 - 206,
        scaledHeight / 2 - 82,
        scaledWidth / 2 - 91,
        scaledHeight / 2 - 81 + 11 * ((tempList.size()) / 4),
        0x77000000);

      Gui.drawRect(
        scaledWidth / 2 - 206,
        scaledHeight / 2 - 81,
        scaledWidth / 2 - 91,
        scaledHeight / 2 - 82,
        0xff000000);
      Gui.drawRect(
        scaledWidth / 2 - 206,
        scaledHeight / 2 - 81 + 11 * ((tempList.size()) / 4),
        scaledWidth / 2 - 91,
        scaledHeight / 2 - 82 + 11 * ((tempList.size()) / 4),
        0xff000000);

      Gui.drawRect(
        scaledWidth / 2 - 206,
        scaledHeight / 2 - 81,
        scaledWidth / 2 - 207,
        scaledHeight / 2 - 82 + 11 * ((tempList.size()) / 4),
        0xff000000);
      Gui.drawRect(
        scaledWidth / 2 - 91,
        scaledHeight / 2 - 81,
        scaledWidth / 2 - 90,
        scaledHeight / 2 - 82 + 11 * ((tempList.size()) / 4),
        0xff000000);

      float sum = 0;

      for (int i = 0; i < tempList.size(); i++) {
        sum += tempList.get(i);
        if ((i + 1) % 4 == 0) {
          sum /= 4;
          fontRenderer.drawString(
            "Layer " + ((i + 1) / 4) + ": " + getLayerTemp(sum),
            scaledWidth / 2 - 205,
            scaledHeight / 2 - 80 + 11 * ((i + 1) / 4 - 1),
            0xffffff);
          sum = 0;
        } else if (i + 1 == tempList.size()) {
          sum /= (i + 1) % 4;
          fontRenderer.drawString(
            "Layer " + ((i + 1) / 4 + 1) + ": " + getLayerTemp(sum),
            scaledWidth / 2 - 205,
            scaledHeight / 2 - 80 + 11 * ((i + 1) / 4),
            0xffffff);
          sum = 0;
        }
      }

    }
  }

  @SideOnly(Side.CLIENT)
  public void readTemperatures() {

    tempList.clear();
    for (int i = 0; i < tile.getOreStacks().size(); i++) {
      IItemHeat cap = tile.getOreStacks().get(i).getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
      if (cap != null) {
        tempList.add(cap.getTemperature());
      }
    }

  }

  /**
   * Получение температуры слоя.
   *
   * @param avgTemp Средняя температура слоя.
   * @return Строка с описанием температуры слоя.
   */
  @SideOnly(Side.CLIENT)
  public String getLayerTemp(float avgTemp) {

    String ttString = Heat.getTooltip(avgTemp);
    if (ttString == null) {
      return "Cold";
    } else {
      return ttString.replaceAll("\u2605", "");
    }

  }

  @Override
  protected void renderHoveredToolTip(int mouseX, int mouseY) {
    if (mouseX > guiLeft + 40 && mouseX < guiLeft + 120 && mouseY > guiTop + 25
        && mouseY < guiTop + 33) {
      int amount = tile.getField(TEBlastFurnace.FIELD_ORE_UNITS);
      drawHoveringText(I18n.format(MOD_ID + ".tooltip.units", amount), mouseX, mouseY);
    }
    if (mouseX > guiLeft + 40 && mouseX < guiLeft + 120 && mouseY > guiTop + 43
        && mouseY < guiTop + 51) {
      int amount = tile.getField(TEBlastFurnace.FIELD_FUEL);
      drawHoveringText(I18n.format(MOD_ID + ".tooltip.units", amount), mouseX, mouseY);
    }
    if (mouseX > guiLeft + 40 && mouseX < guiLeft + 120 && mouseY > guiTop + 61
        && mouseY < guiTop + 69) {
      int amount = tile.getField(TEBlastFurnace.FIELD_MELT);
      drawHoveringText(I18n.format(MOD_ID + ".tooltip.units", amount), mouseX, mouseY);
    }
    super.renderHoveredToolTip(mouseX, mouseY);
  }
}

