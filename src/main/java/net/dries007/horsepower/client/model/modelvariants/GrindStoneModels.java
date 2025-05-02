package net.dries007.horsepower.client.model.modelvariants;

import net.minecraft.util.IStringSerializable;

public enum GrindStoneModels implements IStringSerializable {
  BASE,
  FILLED;

  @Override
  public String getName() {
    return name().toLowerCase();
  }
}
