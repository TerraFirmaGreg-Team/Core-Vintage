package su.terrafirmagreg.data.enums;

import net.minecraft.util.IStringSerializable;

import net.dries007.tfc.ConfigTFC;

import org.jetbrains.annotations.NotNull;

public enum EnumGradeOre implements IStringSerializable {
  NORMAL,
  POOR,
  RICH;

  private static final EnumGradeOre[] VALUES = values();

  @NotNull
  public static EnumGradeOre valueOf(int value) {
    return value < 0 || value >= VALUES.length ? NORMAL : VALUES[value];
  }

  public int getSmeltAmount() {
    return switch (this) {
      case POOR -> ConfigTFC.General.MISC.poorOreMetalAmount;
      case RICH -> ConfigTFC.General.MISC.richOreMetalAmount;
      default -> ConfigTFC.General.MISC.normalOreMetalAmount;
    };
  }

  @Override
  public String getName() {
    return this.name().toLowerCase();
  }

  public int getMeta() {
    return this.ordinal();
  }
}
