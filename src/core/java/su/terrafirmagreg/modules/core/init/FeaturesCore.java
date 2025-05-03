package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.modules.core.feature.advanceddata.FeatureAdvancedData;
import su.terrafirmagreg.modules.core.feature.ambiental.FeatureAmbiental;
import su.terrafirmagreg.modules.core.feature.calendar.FeatureCalendar;
import su.terrafirmagreg.modules.core.feature.climate.FeatureClimate;
import su.terrafirmagreg.modules.core.feature.damageresistance.FeatureDamageResistance;
import su.terrafirmagreg.modules.core.feature.falling.FeatureFalling;
import su.terrafirmagreg.modules.core.feature.hotornot.FeatureHotOrNot;
import su.terrafirmagreg.modules.core.feature.mapping.FeatureRemappingCore;
import su.terrafirmagreg.modules.core.feature.playerdata.FeaturePlayerData;
import su.terrafirmagreg.modules.core.feature.sinkorswim.FeatureSinkOrSwim;
import su.terrafirmagreg.modules.core.feature.size.FeatureSize;

public final class FeaturesCore {

  public static void onRegister(IFeatureRegistrar registrar) {

    registrar.addFeature(new FeatureRemappingCore());
    registrar.addFeature(new FeatureSize());
    registrar.addFeature(new FeaturePlayerData());
    registrar.addFeature(new FeatureCalendar());
    registrar.addFeature(new FeatureClimate());
    registrar.addFeature(new FeatureSinkOrSwim());
    registrar.addFeature(new FeatureHotOrNot());
    registrar.addFeature(new FeatureDamageResistance());
    registrar.addFeature(new FeatureAmbiental());
    registrar.addFeature(new FeatureFalling());
    registrar.addFeature(new FeatureAdvancedData());
  }

}
