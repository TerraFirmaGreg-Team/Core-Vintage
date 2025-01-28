package su.terrafirmagreg.modules.core.feature.climate;

import lombok.Getter;

@Getter
public class ClimateData {

  public static final ClimateData DEFAULT = new ClimateData(0, 250);

  private final float regionalTemp;
  private final float rainfall;

  ClimateData(float regionalTemp, float rainfall) {
    this.regionalTemp = regionalTemp;
    this.rainfall = rainfall;
  }

}
