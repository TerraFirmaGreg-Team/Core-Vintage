package su.terrafirmagreg.framework.manager.feature.api;


import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry.Settings;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;

import lombok.Getter;

// Это пока реализации чего-то, что может быть изменено с помощью системы событий,
// альтернатива есть в модулях, но от туда она будет удалена, в пользу этой реализации
public interface IFeatureEntry extends IBaseEntry<Settings, BaseFeature> {

  // ===== FML Lifecycle

  default void onPreInit() {}

  default void onInit() {}

  default void onPostInit() {}

  default void onLoadComplete() {}

  // ===== FML Lifecycle: Server

  default void onServerAboutToStart() {}

  default void onServerStarting() {}

  default void onServerStarted() {}

  default void onServerStopping() {}

  default void onServerStopped() {}

  @Getter
  class Settings extends BaseSettings<Settings> {

    String name;
    boolean enabled = true;
    boolean hasSubscriptions = true;

    protected Settings() {}

    public static Settings of() {
      return new Settings();
    }

    public Settings name(String name) {
      this.name = name;
      return this;
    }

    public Settings disable() {
      this.enabled = false;
      return this.self();
    }

    public Settings enabled(boolean enabled) {
      this.enabled = enabled;
      return this.self();
    }

    public Settings disableSubscriptions() {
      this.hasSubscriptions = false;
      return this.self();
    }

  }

}
