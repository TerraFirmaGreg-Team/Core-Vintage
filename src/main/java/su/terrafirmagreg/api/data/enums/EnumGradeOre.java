package su.terrafirmagreg.api.data.enums;

import su.terrafirmagreg.modules.rock.ConfigRock;

import net.minecraft.util.IStringSerializable;

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
      case POOR -> ConfigRock.MISC.ORE.poorOreMetalAmount;
      case RICH -> ConfigRock.MISC.ORE.richOreMetalAmount;
      default -> ConfigRock.MISC.ORE.normalOreMetalAmount;
    };
  }

  @Override
  public @NotNull String getName() {
    return this.name().toLowerCase();
  }

  public int getMeta() {
    return this.ordinal();
  }
}
