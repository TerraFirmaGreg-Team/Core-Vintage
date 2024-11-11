package su.terrafirmagreg.api.data.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumPressPart implements IStringSerializable {
  BASE,
  TOP;

  @Override
  public String getName() {
    return name().toLowerCase();
  }
}
