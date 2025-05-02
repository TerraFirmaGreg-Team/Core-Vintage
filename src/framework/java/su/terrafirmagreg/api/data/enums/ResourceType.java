package su.terrafirmagreg.api.data.enums;

public enum ResourceType {

  DATA("data"),
  ASSET("assets");

  private final String directory;

  ResourceType(String directory) {
    this.directory = directory;
  }

  public String getDirectoryName() {
    return this.directory;
  }
}
