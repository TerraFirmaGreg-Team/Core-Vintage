package su.terrafirmagreg.data.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumChopperPart implements IStringSerializable {
  BASE,
  BLADE;

  @Override
  public String getName() {
    return name().toLowerCase();
  }
}
