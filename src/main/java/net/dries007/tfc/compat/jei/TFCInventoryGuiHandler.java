package net.dries007.tfc.compat.jei;

import net.minecraft.client.gui.inventory.GuiContainer;


import mezz.jei.api.gui.IAdvancedGuiHandler;

import org.jetbrains.annotations.NotNull;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class TFCInventoryGuiHandler<T extends GuiContainer> implements IAdvancedGuiHandler<T> {

  private final Class<T> clazz;

  public TFCInventoryGuiHandler(Class<T> clazz) {
    this.clazz = clazz;
  }

  @NotNull
  @Override
  public Class<T> getGuiContainerClass() {
    return clazz;
  }

  @Override
  public java.util.List<Rectangle> getGuiExtraAreas(T guiContainer) {
    List<Rectangle> areas = new ArrayList<>();

    int xPosition = guiContainer.getGuiLeft() + 176;
    int yPosition = guiContainer.getGuiTop() + 4; // +23 each button
    int w = 20;
    int h = 22;
    for (int i = 0; i < 4; i++) {
      Rectangle rectangle = new Rectangle(xPosition, yPosition + i * 23, w, h);
      areas.add(rectangle);
    }

    return areas;
  }
}
