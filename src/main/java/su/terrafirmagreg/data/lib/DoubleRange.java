package su.terrafirmagreg.data.lib;

import net.dries007.tfc.util.Alloy;

import lombok.Getter;

@Getter
public class DoubleRange {

  private final double min;
  private final double max;

  public DoubleRange(double min, double max) {
    this.min = min;
    this.max = max;
  }

  public boolean test(double value) {
    return value >= min - Alloy.EPSILON && value <= max + Alloy.EPSILON;
  }
}
