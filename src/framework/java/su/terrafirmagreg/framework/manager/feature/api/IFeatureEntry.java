package su.terrafirmagreg.framework.manager.feature.api;


import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry.FeatureSettings;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;

import lombok.Getter;

// Это пока реализации чего-то, что может быть изменено с помощью системы событий,
// альтернатива есть в модулях, но от туда она будет удалена, в пользу этой реализации
public interface IFeatureEntry extends IBaseEntry<FeatureSettings, BaseFeature> {


  @Getter
  class FeatureSettings extends BaseSettings<FeatureSettings> {

    protected String[] incompatibleMods;
    protected boolean hasSubscriptions = true;

    protected FeatureSettings() {}

    public static FeatureSettings of() {
      return new FeatureSettings();
    }


    public FeatureSettings disableSubscriptions() {
      this.hasSubscriptions = false;
      return this.self();
    }

    public FeatureSettings incompatibleMods(String... mods) {
      this.incompatibleMods = mods;
      return this.self();
    }

  }

}
