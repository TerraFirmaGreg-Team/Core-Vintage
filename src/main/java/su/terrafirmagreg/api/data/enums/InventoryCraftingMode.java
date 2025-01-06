/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package su.terrafirmagreg.api.data.enums;

public enum InventoryCraftingMode {
  DISABLED("Disabled"),
  ENABLED("Enabled - Needs Workbench"),
  ALWAYS("Always");

  private final String name;

  InventoryCraftingMode(String name) {
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
