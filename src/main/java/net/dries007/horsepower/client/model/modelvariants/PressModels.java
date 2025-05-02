package net.dries007.horsepower.client.model.modelvariants;

import net.minecraft.util.IStringSerializable;

public enum PressModels implements IStringSerializable {
  BASE,
  TOP;

  @Override
  public String getName() {
    return name().toLowerCase();
  }
}
