package su.terrafirmagreg.modules.core.config;

import su.terrafirmagreg.modules.core.feature.advanceddata.FeatureAdvancedDataConfig;
import su.terrafirmagreg.modules.core.feature.ambiental.FeatureAmbientalConfig;
import su.terrafirmagreg.modules.core.feature.calendar.FeatureCalendarConfig;
import su.terrafirmagreg.modules.core.feature.damageresistance.FeatureDamageResistanceConfig;
import su.terrafirmagreg.modules.core.feature.hotornot.FeatureHotOrNotConfig;
import su.terrafirmagreg.modules.core.feature.sinkorswim.FeatureSinkOrSwimConfig;
import su.terrafirmagreg.modules.core.feature.size.FeatureSizeConfig;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;

public final class ConfigCoreFeature {

  @Comment("Advanced Data settings")
  public final FeatureAdvancedDataConfig ADVANCED_DATA = new FeatureAdvancedDataConfig();

  @Config.Comment("Calendar settings")
  public final FeatureCalendarConfig CALENDAR = new FeatureCalendarConfig();

  @Config.Comment("Ambiental settings")
  public final FeatureAmbientalConfig AMBIENTAL = new FeatureAmbientalConfig();

  @Comment("Sink or Swim settings")
  public final FeatureSinkOrSwimConfig SINK_OR_SWIM = new FeatureSinkOrSwimConfig();

  @Comment("Hot Or Not settings")
  public final FeatureHotOrNotConfig HOT_OR_NOT = new FeatureHotOrNotConfig();

  @Comment("Size And Weight settings")
  public final FeatureSizeConfig SIZE = new FeatureSizeConfig();

  @Config.Comment("Damage settings")
  public final FeatureDamageResistanceConfig DAMAGE_RESISTANCE = new FeatureDamageResistanceConfig();


}
