package su.terrafirmagreg.api.data.enums;

public enum TemperatureMode {
  CYCLIC("Cyclic"),
  ENDLESS("Endless");

  private final String name;

  TemperatureMode(String name) {
    this.name = name;
  }

  /**
   * Shows this text in config instead of the enum name
   */
  @Override
  public String toString() {
    return name;
  }
}
