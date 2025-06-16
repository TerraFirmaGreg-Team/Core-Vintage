package su.terrafirmagreg.modules.soil.feature.soiltype;

import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.types.type.SoilTypeHandler;


// TODO скорее всего будет переделано в enum + interface
public class FeatureSoilType extends BaseFeature {

  public FeatureSoilType() {
    super(Settings.of()
      .registryKey("soil_type")
    );

    SoilTypeHandler.init();
  }
}
