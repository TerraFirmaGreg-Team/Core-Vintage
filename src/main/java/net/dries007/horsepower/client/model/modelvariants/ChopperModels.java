package net.dries007.horsepower.client.model.modelvariants;


import net.minecraft.util.IStringSerializable;

public enum ChopperModels implements IStringSerializable {
  BASE,
  BLADE;

  @Override
  public String getName() {
    return name().toLowerCase();
  }
}
