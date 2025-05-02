package net.dries007.horsepower.client.model.modelvariants;

import net.minecraft.util.IStringSerializable;

public enum HandGrindstoneModels implements IStringSerializable {
  BASE,
  CENTER;

  @Override
  public String getName() {
    return name().toLowerCase();
  }
}
