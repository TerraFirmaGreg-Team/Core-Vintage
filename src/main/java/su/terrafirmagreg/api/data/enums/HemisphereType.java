package su.terrafirmagreg.api.data.enums;

public enum HemisphereType {
  COLD_NORTH_HOT_SOUTH(1, "Cold North, Hot South"),
  HOT_NORTH_COLD_SOUTH(-1, "Hot North, Cold South");

  private final int value;
  private final String name;

  HemisphereType(int value, String name) {
    this.value = value;
    this.name = name;
  }

  /**
   * Shows this text in config instead of the enum name
   */
  @Override
  public String toString() {
    return name;
  }

  public int getValue() {
    return value;
  }
}
